package com.github.paulosalonso.aws.dynamodb.domain.usecase;

import com.github.paulosalonso.aws.dynamodb.domain.model.Movie;
import com.github.paulosalonso.aws.dynamodb.domain.usecase.port.MoviePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UpdateMovieUseCase {

    private final MoviePort moviePort;

    public void update(Movie movie) {
        moviePort.update(movie);
    }
}
