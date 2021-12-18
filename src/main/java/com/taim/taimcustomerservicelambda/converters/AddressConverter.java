package com.taim.taimcustomerservicelambda.converters;

import com.google.common.base.Converter;
import com.taim.taimcustomerservicelambda.model.AddressDTO;
import com.taim.taimcustomerservicelambda.models.dynamodb.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter extends Converter<AddressDTO, Address> {

    @Override
    protected Address doForward (AddressDTO address) {
        return Address.builder()
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .stateOrProvince(address.getStateOrProvince())
                .postalCode(address.getPostalCode()).build();
    }

    @Override
    protected AddressDTO doBackward(Address address) {
        return AddressDTO.builder()
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .stateOrProvince(address.getStateOrProvince())
                .country(address.getCountry())
                .postalCode(address.getPostalCode())
                .build();
    }
}
