package com.github.paulosalonso.aws.dynamodb.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Movie {
    private final String plot;
    private final Double rating;
    private final Integer year;
    private final String title;
}
