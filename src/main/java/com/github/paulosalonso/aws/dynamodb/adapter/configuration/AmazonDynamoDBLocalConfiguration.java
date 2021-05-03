package com.github.paulosalonso.aws.dynamodb.adapter.configuration;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("local-dynamodb")
@Configuration
public class AmazonDynamoDBLocalConfiguration {

    @Bean
    public AmazonDynamoDB amazonDynamoDB(@Value("${aws.dynamodb.endpoint.host}") String host,
                                         @Value("${aws.dynamodb.region}") String region) {

        log.info("Building DynamoDB client with local configurations.");

        return AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(host, region))
                .build();
    }
}
