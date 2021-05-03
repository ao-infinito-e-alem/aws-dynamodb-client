package com.github.paulosalonso.aws.dynamodb.adapter.api.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieDTO {

    @NotBlank
    private String plot;

    @PositiveOrZero
    @Max(10)
    private Double rating;

    @PositiveOrZero
    private Integer year;

    @NotBlank
    private String title;
}
