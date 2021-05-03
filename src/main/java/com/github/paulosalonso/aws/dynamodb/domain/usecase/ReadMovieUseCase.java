package com.github.paulosalonso.aws.dynamodb.domain.usecase;

import com.github.paulosalonso.aws.dynamodb.domain.model.Movie;
import com.github.paulosalonso.aws.dynamodb.domain.usecase.port.MoviePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReadMovieUseCase {

    private final MoviePort moviePort;

    public Movie read(Integer yer, String title) {
        return moviePort.read(yer, title).orElseThrow(() -> new RuntimeException("Not found"));
    }
}
