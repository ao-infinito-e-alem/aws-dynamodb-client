package com.github.paulosalonso.aws.dynamodb.adapter.dynamodb.mapper;

import com.github.paulosalonso.aws.dynamodb.adapter.dynamodb.model.MovieEntity;
import com.github.paulosalonso.aws.dynamodb.domain.model.Movie;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MovieDynamoMapper {

    public MovieEntity toEntity(Movie movie) {
        return MovieEntity.builder()
                .primaryKey(MovieEntity.PrimaryKey.builder()
                        .year(movie.getYear())
                        .title(movie.getTitle())
                        .build())
                .property("plot", movie.getPlot())
                .property("rating", movie.getRating())
                .build();
    }

    public Movie toDomain(MovieEntity movieEntity) {
        String plot = null;
        Double rating = null;

        var info = movieEntity.getProperties();

        if (info != null) {
            plot = (String) info.get("plot");
            rating = ((BigDecimal) info.get("rating")).doubleValue();
        }

        return Movie.builder()
                .year(movieEntity.getPrimaryKey().getYear())
                .title(movieEntity.getPrimaryKey().getTitle())
                .plot(plot)
                .rating(rating)
                .build();
    }
}
