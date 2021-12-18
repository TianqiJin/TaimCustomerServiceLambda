package com.taim.taimcustomerservicelambda.controllers;

import com.taim.taimcustomerservicelambda.api.GetCustomersByFilterApi;
import com.taim.taimcustomerservicelambda.managers.CustomerManager;
import com.taim.taimcustomerservicelambda.model.CustomerDTO;
import com.taim.taimcustomerservicelambda.model.GetCustomersByFilterInput;
import com.taim.taimcustomerservicelambda.model.GetCustomersByFilterOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class GetCustomersByFilterController implements GetCustomersByFilterApi {

    private final CustomerManager customerManager;

    @Override
    public ResponseEntity<GetCustomersByFilterOutput> getCustomersByFilter(@Valid GetCustomersByFilterInput input,
                                                                           @Valid Integer pageNumber,
                                                                           @Valid Integer pageSize) {
        List<CustomerDTO> customerDTOS = customerManager.getCustomersByFilter(input.getCustomerFilter(), pageNumber,
                pageSize);
        return customerDTOS;
    }
}
