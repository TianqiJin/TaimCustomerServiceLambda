package com.taim.taimcustomerservicelambda.daos;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;

import java.util.Objects;
import java.util.Optional;

public abstract class DynamoDao<T> {

    private final DynamoDBMapper dynamoDBMapper;

    protected static final DynamoDBMapperConfig CONSISTENT_READ_CONFIG =
            DynamoDBMapperConfig.ConsistentReads.CONSISTENT.config();

    protected static final DynamoDBMapperConfig SKIP_NULL_SAVE_CONFIG =
            DynamoDBMapperConfig.SaveBehavior.UPDATE_SKIP_NULL_ATTRIBUTES.config();

    public DynamoDao(final DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Optional<T> get(final T entity) {
        return Optional.ofNullable(dynamoDBMapper.load(Objects.requireNonNull(entity), CONSISTENT_READ_CONFIG));
    }

    public void save(final T entity) {
        dynamoDBMapper.save(Objects.requireNonNull(entity));
    }

    public void save(final T entity, DynamoDBMapperConfig config) {
        dynamoDBMapper.save(Objects.requireNonNull(entity), config);
    }

    public void delete(final T entity) {
        dynamoDBMapper.delete(Objects.requireNonNull(entity));
    }

    public PaginatedQueryList<T> query(Class clazz, DynamoDBQueryExpression queryExpression) {
        return dynamoDBMapper.query(clazz, queryExpression);
    }
}
