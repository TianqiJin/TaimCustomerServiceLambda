package com.taim.taimcustomerservicelambda.converters;

import com.google.common.base.Converter;
import com.taim.taimcustomerservicelambda.model.CustomerDTO;
import com.taim.taimcustomerservicelambda.model.GenderDTO;
import com.taim.taimcustomerservicelambda.model.UserTypeDTO;
import com.taim.taimcustomerservicelambda.models.dynamodb.Customer;

import com.taim.taimcustomerservicelambda.models.dynamodb.enums.Gender;
import com.taim.taimcustomerservicelambda.models.dynamodb.enums.UserType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerConverter extends Converter<CustomerDTO, Customer> {

    private final AddressConverter addressConverter;

    @Autowired
    public CustomerConverter(AddressConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    @Override
    protected Customer doForward(CustomerDTO customer) {

        Validate.notNull(Gender.getGender(customer.getGender().getValue()));
        Validate.notNull(UserType.getUserType(customer.getUserType().getValue()));

        return Customer.builder()
                .companyCode(customer.getCompanyCode())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .middleName(customer.getMiddleName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .gender(Gender.getGender(customer.getGender().getValue()))
                .address(CollectionUtils.isNotEmpty(customer.getAddresses())?
                        customer.getAddresses().stream()
                                .map(addressConverter::convert)
                                .collect(Collectors.toList())
                        : null)
                .storeCredit(customer.getStoreCredit())
                .userType(UserType.getUserType(customer.getUserType().getValue()))
                .pstNumber(customer.getPstNumber())
                .build();
    }

    @Override
    protected CustomerDTO doBackward(Customer customer) {
        return CustomerDTO.builder()
                .companyCode(customer.getCompanyCode())
                .firstName(customer.getFirstName())
                .middleName(customer.getMiddleName())
                .lastName(customer.getLastName())
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .gender(GenderDTO.fromValue(customer.getGender().getValue()))
                .storeCredit(customer.getStoreCredit())
                .userType(UserTypeDTO.fromValue(customer.getUserType().getValue()))
                .pstNumber(customer.getPstNumber())
                .addresses(CollectionUtils.isNotEmpty(customer.getAddress())?
                        customer.getAddress().stream()
                                .map(address -> addressConverter.reverse().convert(address))
                                .collect(Collectors.toList())
                        : null)
                .build();
    }
}
