package com.techgen.controller;

import com.techgen.dto.CustomerDto;
import com.techgen.service.CustomerService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerDto> getCustomerByCustomerId(@PathVariable(name = "customer-id") String customerId) {
        CustomerDto customer = customerService.getCustomerByCustomerId(customerId);
        return ResponseEntity.ok(customer);
    }

    @GetMapping()
    public ResponseEntity<List<CustomerDto>> getCustomers() {
        List<CustomerDto> customers = customerService.getCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping()
    public ResponseEntity<String> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("customer-id " + customerDto.getCustomerId() + " created");
    }

    @PutMapping("/{customer-id}")
    public ResponseEntity<CustomerDto> updateCustomerByCustomerId(@PathVariable(name = "customer-id") String customerId, @RequestBody CustomerDto customerDto) {
        CustomerDto updateCustomer = customerService.updateCustomerByCustomerId(customerId, customerDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateCustomer);
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<String> deleteCustomerByCustomerId(@PathVariable(name = "customer-id") String customerId) {
        customerService.deleteCustomerByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.OK).body("customer-id " + customerId + " deleted");
    }
}
