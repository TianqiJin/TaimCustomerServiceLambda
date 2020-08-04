package com.taim.taimcustomerservicelambda.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.Builder;
import lombok.Data;

@DynamoDBDocument
@Data
@Builder
public class Address{

    @DynamoDBAttribute
    private String streetNum;

    @DynamoDBAttribute
    private String addressLine1;

    @DynamoDBAttribute
    private String addressLine2;

    @DynamoDBAttribute
    private String city;

    @DynamoDBAttribute
    private String province;

    @DynamoDBAttribute
    private String country;

    @DynamoDBAttribute
    private String postalCode;
}
