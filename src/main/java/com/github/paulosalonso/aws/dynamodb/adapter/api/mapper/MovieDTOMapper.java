package com.github.paulosalonso.aws.dynamodb.adapter.api.mapper;

import com.github.paulosalonso.aws.dynamodb.adapter.api.dto.MovieDTO;
import com.github.paulosalonso.aws.dynamodb.adapter.api.dto.MovieUpdateDTO;
import com.github.paulosalonso.aws.dynamodb.domain.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieDTOMapper {

    public Movie toDomain(MovieDTO movieDTO) {
        return Movie.builder()
                .plot(movieDTO.getPlot())
                .rating(movieDTO.getRating())
                .year(movieDTO.getYear())
                .title(movieDTO.getTitle())
                .build();
    }

    public Movie toDomain(Integer year, String title, MovieUpdateDTO movieUpdateDTO) {
        return Movie.builder()
                .plot(movieUpdateDTO.getPlot())
                .rating(movieUpdateDTO.getRating())
                .year(year)
                .title(title)
                .build();
    }

    public MovieDTO toDTO(Movie movie) {
        return MovieDTO.builder()
                .plot(movie.getPlot())
                .rating(movie.getRating())
                .year(movie.getYear())
                .title(movie.getTitle())
                .build();
    }
}
