package com.taim.taimcustomerservicelambda.config;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamoDBConfig {

    private static final Integer CONNECTION_TIME_OUT = 2000;

    @Bean
    public AmazonDynamoDB amazonDbClient() {
        final int connectionTimoutInMillis = CONNECTION_TIME_OUT;
        ClientConfiguration clientConfiguration = new ClientConfiguration()
                .withConnectionTimeout(connectionTimoutInMillis);

        return AmazonDynamoDBClientBuilder.standard().withClientConfiguration(clientConfiguration).build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(final AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }
}
