package com.github.paulosalonso.aws.dynamodb.adapter.gateway;

import com.github.paulosalonso.aws.dynamodb.adapter.dynamodb.MovieRepository;
import com.github.paulosalonso.aws.dynamodb.adapter.dynamodb.mapper.MovieDynamoMapper;
import com.github.paulosalonso.aws.dynamodb.domain.model.Movie;
import com.github.paulosalonso.aws.dynamodb.domain.usecase.port.MoviePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MovieGateway implements MoviePort {

    private final MovieRepository movieRepository;
    private final MovieDynamoMapper mapper;

    @Override
    public void create(Movie movie) {
        movieRepository.create(mapper.toEntity(movie));
    }

    @Override
    public Optional<Movie> read(Integer year, String title) {
        return movieRepository.read(year, title).map(mapper::toDomain);
    }

    @Override
    public void update(Movie movie) {
        movieRepository.update(mapper.toEntity(movie));
    }

    @Override
    public void delete(Integer year, String title) {
        movieRepository.delete(year, title);
    }
}
