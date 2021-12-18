package com.taim.taimcustomerservicelambda.models.dynamodb;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;
import com.taim.taimcustomerservicelambda.models.dynamodb.Address;
import com.taim.taimcustomerservicelambda.models.dynamodb.basemodels.BaseModel;
import com.taim.taimcustomerservicelambda.models.dynamodb.enums.Gender;
import com.taim.taimcustomerservicelambda.models.dynamodb.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by tjin on 2017-07-23.
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@DynamoDBTable(tableName = "Customer")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseModel {

    @DynamoDBRangeKey
    private String email;

    @DynamoDBAttribute
    private String firstName;

    @DynamoDBAttribute
    private String middleName;

    @DynamoDBAttribute
    private String lastName;

    @DynamoDBAttribute
    private String phone;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute
    private Gender gender;

    @DynamoDBAttribute
    private List<Address> address;

    @DynamoDBAttribute
    private BigDecimal storeCredit;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute
    private UserType userType;

    @DynamoDBAttribute
    private String pstNumber;

    @DynamoDBAttribute
    private String gsiSortKey;
}
