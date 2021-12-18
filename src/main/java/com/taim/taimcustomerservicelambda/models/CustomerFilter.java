package com.taim.taimcustomerservicelambda.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class CustomerFilter {
    @NonNull
    private String companyCode;
    private String email;
    private String phoneNumber;
}
