package com.taim.taimcustomerservicelambda.controllers;

import com.taim.taimcustomerservicelambda.api.CreateCustomerSyncApi;
import com.taim.taimcustomerservicelambda.managers.CustomerManager;
import com.taim.taimcustomerservicelambda.model.CreateCustomerSyncInput;
import com.taim.taimcustomerservicelambda.model.CreateCustomerSyncOutput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class CreateCustomerSyncController implements CreateCustomerSyncApi {

    private final CustomerManager customerManager;

    @Override
    public ResponseEntity<CreateCustomerSyncOutput> createCustomerSync(
            @Valid CreateCustomerSyncInput createCustomerSyncInput) {

        CreateCustomerSyncOutput createCustomerSyncOutput = CreateCustomerSyncOutput
                .builder()
                .companyCode(createCustomerSyncInput.getCompanyCode())
                .build();
        try{
            customerManager.createCustomerSync(createCustomerSyncInput.getCompanyCode(),
                    createCustomerSyncInput.getAttribute());
        } catch (Exception e) {
            log.error("Failed to create customer: {}", e.getMessage());
            createCustomerSyncOutput.setIsSuccess(false);
        }

        createCustomerSyncOutput.setIsSuccess(true);

        return ResponseEntity.ok(createCustomerSyncOutput);
    }
}
