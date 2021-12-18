package com.taim.taimcustomerservicelambda.managers;


import com.taim.taimcustomerservicelambda.converters.CustomerConverter;
import com.taim.taimcustomerservicelambda.converters.CustomerFilterConverter;
import com.taim.taimcustomerservicelambda.daos.CustomerDao;
import com.taim.taimcustomerservicelambda.model.CreateCustomerSyncAttributeDTO;
import com.taim.taimcustomerservicelambda.model.CustomerDTO;
import com.taim.taimcustomerservicelambda.model.CustomerFilterDTO;
import com.taim.taimcustomerservicelambda.models.CustomerFilter;
import com.taim.taimcustomerservicelambda.models.dynamodb.Customer;
import com.taim.taimcustomerservicelambda.models.dynamodb.enums.Gender;
import com.taim.taimcustomerservicelambda.models.dynamodb.enums.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class CustomerManagerImpl implements CustomerManager {

    private final CustomerDao customerDao;
    private final CustomerConverter customerConverter;
    private final CustomerFilterConverter customerFilterConverter;

    @Override
    public void createCustomerSync(String companyCode, CreateCustomerSyncAttributeDTO attributeDTO) {
        Validate.notNull(attributeDTO.getUserType());
        Validate.notNull(attributeDTO.getGender());

        Customer customer = Customer.builder()
                .companyCode(companyCode)
                .firstName(attributeDTO.getFirstName())
                .middleName(attributeDTO.getMiddleName())
                .lastName(attributeDTO.getLastName())
                .email(attributeDTO.getEmail())
                .phone(attributeDTO.getPhone())
                .userType(UserType.valueOf(attributeDTO.getUserType().getValue()))
                .gender(Gender.valueOf(attributeDTO.getGender().getValue()))
                .pstNumber(attributeDTO.getPstNumber())
                .build();
        customerDao.saveCustomer(customer);
    }


    @Override
    public List<CustomerDTO> getCustomersByFilter(CustomerFilterDTO customerFilterDTO, int pageNumber, int pageSize) {
        CustomerFilter customerFilter = customerFilterConverter.convert(customerFilterDTO);
        return customerDao.getCustomerByFilter(customerFilter, pageNumber, pageSize).stream()
                .map(customer -> customerConverter.reverse().convert(customer))
                .collect(Collectors.toList());
    }
}
