package com.github.paulosalonso.aws.dynamodb.adapter.dynamodb.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.Map;

@Getter
@Builder
public class MovieEntity {

    private final PrimaryKey primaryKey;

    @Singular
    private final Map<String, Object> properties;

    @Getter
    @Builder
    public static class PrimaryKey {
        private final Integer year;
        private final String title;
    }
}
