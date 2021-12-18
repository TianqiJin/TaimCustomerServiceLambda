package com.taim.taimcustomerservicelambda.converters;

import com.google.common.base.Converter;
import com.taim.taimcustomerservicelambda.model.CustomerFilterDTO;
import com.taim.taimcustomerservicelambda.models.CustomerFilter;
import org.springframework.stereotype.Component;

@Component
public class CustomerFilterConverter extends Converter<CustomerFilterDTO, CustomerFilter> {
    @Override
    protected CustomerFilter doForward(CustomerFilterDTO customerFilterDTO) {
        return CustomerFilter.builder()
                .companyCode(customerFilterDTO.getCompanyCode())
                .email(customerFilterDTO.getEmail())
                .phoneNumber(customerFilterDTO.getPhone())
                .build();
    }

    @Override
    protected CustomerFilterDTO doBackward(CustomerFilter customerFilter) {
        return CustomerFilterDTO.builder()
                .companyCode(customerFilter.getCompanyCode())
                .email(customerFilter.getEmail())
                .phone(customerFilter.getPhoneNumber())
                .build();
    }
}
