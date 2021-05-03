package com.github.paulosalonso.aws.dynamodb.adapter.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("dynamodb")
@Configuration
public class AmazonDynamoDBAWSConfiguration {

    @Bean
    public AmazonDynamoDB amazonDynamoDB(@Value("${aws.dynamodb.accessKey}") String accessKey,
                                         @Value("${aws.dynamodb.secretKey}") String secretKey,
                                         @Value("${aws.dynamodb.region}") String region) {

        log.info("Building DynamoDB client with AWS configurations.");

        return AmazonDynamoDBClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }
}
