package com.techgen.service.impl;

import com.techgen.dto.CustomerDto;
import com.techgen.entity.Customer;
import com.techgen.exception.CustomerException;
import com.techgen.repository.CustomerRepository;
import com.techgen.service.CustomerService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;

	private ModelMapper modelMapper;

	public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
		this.customerRepository = customerRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public CustomerDto getCustomerByCustomerId(String customerId) {
		Optional<Customer> customerOptional = getCustomer(customerId);
		Customer customer = customerOptional.get();
		CustomerDto customerDto = mapEntityToDTO(customer);
		return customerDto;
	}

	@Override
	public List<CustomerDto> getCustomers() {
		List<Customer> customers = customerRepository.findAll();
		if (ObjectUtils.isEmpty(customers)) {
			throw new CustomerException(HttpStatus.BAD_REQUEST, "no customers are existed");
		}
		List<CustomerDto> customerDtos = customers.stream().map(customer -> mapEntityToDTO(customer))
				.collect(Collectors.toList());
		return customerDtos;
	}

	@Override
	public void createCustomer(CustomerDto customerDto) {
		Customer customer = mapDTOToEntity(customerDto);
		customerRepository.save(customer);
	}

	@Override
	public CustomerDto updateCustomerByCustomerId(String customerId, CustomerDto customerDto) {
		Optional<Customer> customerOptional = getCustomer(customerId);
		Customer customer = customerOptional.get();
		if (StringUtils.hasText(customerDto.getName())) {
			customer.setName(customerDto.getName());
		}
		if (StringUtils.hasText(customerDto.getCity())) {
			customer.setName(customerDto.getCity());
		}
		customer = customerRepository.save(customer);
		CustomerDto udpatedCustomerDto = mapEntityToDTO(customer);
		return udpatedCustomerDto;
	}

	@Override
	public Long deleteCustomerByCustomerId(String customerId) {
		Optional<Customer> customerOptional = getCustomer(customerId);
		Customer customer = customerOptional.get();
		customerRepository.delete(customer);
		return 1l;
	}

	private Optional<Customer> getCustomer(String customerId) {
		Optional<Customer> customerOptional = customerRepository.findCustomerByCustomerId(customerId);
		if (!customerOptional.isPresent()) {
			throw new CustomerException(HttpStatus.BAD_REQUEST, "customer not found with customer-id " + customerId);
		}
		return customerOptional;
	}

	private CustomerDto mapEntityToDTO(Customer customer) {
		CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
		return customerDto;
	}

	private Customer mapDTOToEntity(CustomerDto customerDto) {
		Customer customer = modelMapper.map(customerDto, Customer.class);
		return customer;
	}

}
