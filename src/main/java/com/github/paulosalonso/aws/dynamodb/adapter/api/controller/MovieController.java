package com.github.paulosalonso.aws.dynamodb.adapter.api.controller;

import com.github.paulosalonso.aws.dynamodb.adapter.api.dto.MovieDTO;
import com.github.paulosalonso.aws.dynamodb.adapter.api.dto.MovieUpdateDTO;
import com.github.paulosalonso.aws.dynamodb.adapter.api.mapper.MovieDTOMapper;
import com.github.paulosalonso.aws.dynamodb.domain.usecase.CreateMovieUseCase;
import com.github.paulosalonso.aws.dynamodb.domain.usecase.DeleteMovieUseCase;
import com.github.paulosalonso.aws.dynamodb.domain.usecase.ReadMovieUseCase;
import com.github.paulosalonso.aws.dynamodb.domain.usecase.UpdateMovieUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final CreateMovieUseCase createMovieUseCase;
    private final ReadMovieUseCase readMovieUseCase;
    private final UpdateMovieUseCase updateMovieUseCase;
    private final DeleteMovieUseCase deleteMovieUseCase;
    private final MovieDTOMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody @Valid MovieDTO movieDTO) {
        createMovieUseCase.create(mapper.toDomain(movieDTO));
    }

    @GetMapping("/{year}/{title}")
    public MovieDTO read(@PathVariable Integer year, @PathVariable String title) {
        return mapper.toDTO(readMovieUseCase.read(year, title));
    }

    @PutMapping("/{year}/{title}")
    public void update(@PathVariable Integer year,
                       @PathVariable String title,
                       @RequestBody @Valid MovieUpdateDTO movieUpdateDTO) {

        updateMovieUseCase.update(mapper.toDomain(year, title, movieUpdateDTO));
    }

    @DeleteMapping("/{year}/{title}")
    public void delete(@PathVariable Integer year, @PathVariable String title) {
        deleteMovieUseCase.delete(year, title);
    }
}
