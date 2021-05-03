package com.github.paulosalonso.aws.dynamodb.domain.usecase.port;

import com.github.paulosalonso.aws.dynamodb.domain.model.Movie;

import java.util.Optional;

public interface MoviePort {

    void create(Movie movie);
    Optional<Movie> read(Integer year, String title);
    void update(Movie movie);
    void delete(Integer year, String title);
}
