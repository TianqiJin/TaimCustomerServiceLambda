package com.taim.taimcustomerservicelambda.daos;


import com.taim.taimcustomerservicelambda.models.CustomerFilter;
import com.taim.taimcustomerservicelambda.models.dynamodb.Customer;

import java.util.List;

public interface CustomerDao {
    void createCustomer(Customer customer);

    void saveCustomer(Customer customer);

    List<Customer> getCustomerByFilter(CustomerFilter customerFilter, int pageNumber, int pageSize);
}
