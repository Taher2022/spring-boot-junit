package com.techgen.service;

import com.techgen.dto.CustomerDto;
import java.util.List;

public interface CustomerService {

    CustomerDto getCustomerByCustomerId(String customerId);

    List<CustomerDto> getCustomers();

    void createCustomer(CustomerDto customerDto);

    CustomerDto updateCustomerByCustomerId(String customerId, CustomerDto customerDto);

    Long deleteCustomerByCustomerId(String customerId);

}
