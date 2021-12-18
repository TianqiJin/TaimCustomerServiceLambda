package com.taim.taimcustomerservicelambda.managers;

import com.taim.taimcustomerservicelambda.model.CreateCustomerSyncAttributeDTO;
import com.taim.taimcustomerservicelambda.model.CreateCustomerSyncInput;
import com.taim.taimcustomerservicelambda.model.CustomerDTO;
import com.taim.taimcustomerservicelambda.model.CustomerFilterDTO;

import java.util.List;

public interface CustomerManager {

    /**
     * Create customer synchronously
     * @param companyCode company code
     * @param syncAttributeDTO attributes for creating customer
     */
    void createCustomerSync(String companyCode, CreateCustomerSyncAttributeDTO syncAttributeDTO);

    /**
     * Get customers by filter
     * @param customerFilterDTO
     * @return
     */
    List<CustomerDTO> getCustomersByFilter(CustomerFilterDTO customerFilterDTO, int pageNumber, int pageSize);

}
