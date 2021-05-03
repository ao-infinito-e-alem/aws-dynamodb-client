package com.github.paulosalonso.aws.dynamodb.adapter.dynamodb;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.paulosalonso.aws.dynamodb.adapter.dynamodb.model.MovieEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.amazonaws.services.dynamodbv2.model.KeyType.HASH;
import static com.amazonaws.services.dynamodbv2.model.KeyType.RANGE;
import static com.amazonaws.services.dynamodbv2.model.ScalarAttributeType.N;
import static com.amazonaws.services.dynamodbv2.model.ScalarAttributeType.S;
import static java.lang.String.format;

@Slf4j
@Component
public class MovieRepository {

    private static final String TABLE = "movie";

    private final ApplicationContext context;
    private final DynamoDB dynamoDB;
    private Table table;

    public MovieRepository(ApplicationContext context, DynamoDB dynamoDB) {
        this.context = context;
        this.dynamoDB = dynamoDB;

        this.table = dynamoDB.getTable(TABLE);

        try {
            var describe = table.describe();
            log.info(describe.toString());
        } catch (Exception e) {
            this.table = createTable();
        }
    }

    public void create(MovieEntity movie) {
        Table table = dynamoDB.getTable(TABLE);

        try {
            log.info("Adding a new item...");

            table.putItem(new Item()
                    .withPrimaryKey("year", movie.getPrimaryKey().getYear(), "title", movie.getPrimaryKey().getTitle())
                    .withMap("info", movie.getProperties()));

            log.info("PutItem succeeded");
        } catch (Exception e) {
            log.error("Unable to add item", e);
        }
    }

    public Optional<MovieEntity> read(Integer year, String title) {
        var getItemSpec = new GetItemSpec()
                .withPrimaryKey("year", year, "title", title);

        try {
            log.info("Attempting to read the item...");

            var outcome = table.getItem(getItemSpec);

            log.info("GetItem succeeded...");

            if (outcome != null) {
                return Optional.of(MovieEntity.builder()
                        .primaryKey(MovieEntity.PrimaryKey.builder()
                                .year(outcome.getInt("year"))
                                .title(outcome.getString("title"))
                                .build())
                        .properties(outcome.getMap("info"))
                        .build());
            }

            return Optional.empty();
        } catch (Exception e) {
            var message = format("Unable to read item: %d %s", year, title);
            log.error(message, e);
            throw new RuntimeException(message, e);
        }
    }

    public void update(MovieEntity movieEntity) {
        var fieldUpdateExpression = "info.%1$s = :%1$s";

        var updateExpression = "set " + movieEntity.getProperties().keySet().stream()
                .map(property -> format(fieldUpdateExpression, property))
                .collect(Collectors.joining(","));

        var valueMap = new ValueMap();

        movieEntity.getProperties()
                .forEach((key, value) -> valueMap.with(":" + key, value));

        var updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("year", movieEntity.getPrimaryKey().getYear(), "title", movieEntity.getPrimaryKey().getTitle())
                .withUpdateExpression(updateExpression)
                .withValueMap(valueMap)
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            log.info("Updating the item...");
            table.updateItem(updateItemSpec);
            log.info("UpdateItem succeeded");
        } catch (Exception e) {
            log.error("Unable to update item", e);
            throw new RuntimeException(e);
        }
    }

    public void delete(Integer year, String title) {
        try {
            log.info("Attempting a conditional delete...");

            var deleteItemSpec = new DeleteItemSpec()
                    .withPrimaryKey("year", year, "title", title);

            table.deleteItem(deleteItemSpec);

            log.info("DeleteItem succeeded");
        } catch (Exception e) {
            log.error("Unable to delete item", e);
            throw new RuntimeException(e);
        }
    }

    private Table createTable() {
        Table movieTable = null;

        try {
            log.info("Creating table 'movie'");

            movieTable = dynamoDB.createTable(TABLE,
                    List.of(new KeySchemaElement("year", HASH),
                            new KeySchemaElement("title", RANGE)),
                    List.of(new AttributeDefinition("year", N),
                            new AttributeDefinition("title", S)),
                    new ProvisionedThroughput(10L, 10L));

            movieTable.waitForActive();

            log.info("Table 'movie' successfully created");
            log.info("Put data on table 'movie'");

            try (var jsonParser = new JsonFactory().createParser(context.getResource("classpath:data/movies.json").getInputStream())) {
                JsonNode rootNode = new ObjectMapper().readTree(jsonParser);
                Iterator<JsonNode> iterator = rootNode.iterator();

                ObjectNode currentNode;

                while (iterator.hasNext()) {
                    currentNode = (ObjectNode) iterator.next();

                    int year = currentNode.path("year").asInt();
                    String title = currentNode.path("title").asText();

                    movieTable.putItem(new Item()
                            .withPrimaryKey("year", year, "title", title)
                            .withJSON("info", currentNode.path("info").toString()));

                    log.info("PutItem succeeded: {} {}", year, title);
                }
            }
        } catch (Exception e) {
            if (movieTable != null) {
                movieTable.delete();
            }

            log.error("Error creating movie table", e);
            throw new RuntimeException(e);
        }

        return movieTable;
    }
}
