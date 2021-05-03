package com.github.paulosalonso.aws.dynamodb.domain.usecase;

import com.github.paulosalonso.aws.dynamodb.domain.usecase.port.MoviePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteMovieUseCase {

    private final MoviePort moviePort;

    public void delete(Integer year, String title) {
        moviePort.delete(year, title);
    }
}
