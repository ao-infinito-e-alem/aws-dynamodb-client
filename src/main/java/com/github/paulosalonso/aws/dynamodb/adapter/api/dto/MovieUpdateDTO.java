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
public class MovieUpdateDTO {

    @NotBlank
    private String plot;

    @PositiveOrZero
    @Max(10)
    private Double rating;
}
