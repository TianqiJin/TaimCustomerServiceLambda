package com.taim.taimcustomerservicelambda.models.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Builder;
import lombok.Data;

@DynamoDBDocument
@Data
@Builder
public class Address{

    @DynamoDBAttribute
    private String addressLine1;

    @DynamoDBAttribute
    private String addressLine2;

    @DynamoDBAttribute
    private String city;

    @DynamoDBAttribute
    private String stateOrProvince;

    @DynamoDBAttribute
    private String country;

    @DynamoDBAttribute
    private String postalCode;
}
