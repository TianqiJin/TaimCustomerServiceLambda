package com.taim.taimcustomerservicelambda.daos;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.taim.taimcustomerservicelambda.models.CustomerFilter;
import com.taim.taimcustomerservicelambda.models.dynamodb.Customer;
import com.taim.taimcustomerservicelambda.utils.DynamoDbUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CustomerDaoImpl extends DynamoDao<Customer> implements CustomerDao {

    private static final String COMPANY_PHONE_GSI = "companyCode-phone-index";
    private static final String RANGE_KEY = "email";
    private static final String COMPANY_PHONE_GST_RANGE_KEY = "phone";

    @Autowired
    public CustomerDaoImpl(DynamoDBMapper dynamoDBMapper) {
        super(dynamoDBMapper);
    }

    @Override
    public void createCustomer(Customer customer) {
        save(customer);
    }

    @Override
    public void saveCustomer(Customer customer) {
        save(customer, SKIP_NULL_SAVE_CONFIG);
    }

    @Override
    public List<Customer> getCustomerByFilter(CustomerFilter customerFilter, int pageNumber, int pageSize) {
        Customer hashKeyValue = new Customer();
        hashKeyValue.setCompanyCode(customerFilter.getCompanyCode());

        DynamoDBQueryExpression<Object> dynamoDBQueryExpression;

        if (isHashKeyOnly(customerFilter)) {
            Condition rangeKeyCondition = new Condition().withComparisonOperator(ComparisonOperator.EQ)
                    .withAttributeValueList(new AttributeValue().withS(customerFilter.getEmail()));
            dynamoDBQueryExpression = new DynamoDBQueryExpression<>()
                    .withHashKeyValues(hashKeyValue)
                    .withRangeKeyCondition(RANGE_KEY, rangeKeyCondition);
        } else {
            Condition rangeKeyCondition = new Condition()
                    .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                    .withAttributeValueList(new AttributeValue().withS(customerFilter.getEmail()));
            dynamoDBQueryExpression = new DynamoDBQueryExpression<>()
                    .withHashKeyValues(hashKeyValue)
                    .withIndexName(COMPANY_PHONE_GSI)
                    .withRangeKeyCondition(COMPANY_PHONE_GST_RANGE_KEY, rangeKeyCondition);
        }
        dynamoDBQueryExpression.withConsistentRead(false);

        PaginatedQueryList<Customer> customerPaginatedQueryList = query(Customer.class, dynamoDBQueryExpression);

        return DynamoDbUtils.paginateResult(customerPaginatedQueryList, pageNumber, pageSize);
    }

    private boolean isHashKeyOnly(CustomerFilter customerFilter) {
        return StringUtils.isBlank(customerFilter.getPhoneNumber());
    }
}
