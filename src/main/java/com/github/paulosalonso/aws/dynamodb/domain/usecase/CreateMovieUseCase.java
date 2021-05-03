package com.github.paulosalonso.aws.dynamodb.domain.usecase;

import com.github.paulosalonso.aws.dynamodb.domain.model.Movie;
import com.github.paulosalonso.aws.dynamodb.domain.usecase.port.MoviePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateMovieUseCase {

    private final MoviePort moviePort;

    public void create(Movie movie) {
        moviePort.create(movie);
    }
}
