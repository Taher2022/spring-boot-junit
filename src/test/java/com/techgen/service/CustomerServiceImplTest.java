package com.techgen.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.modelmapper.ModelMapper;

import com.techgen.dto.CustomerDto;
import com.techgen.entity.Customer;
import com.techgen.exception.CustomerException;
import com.techgen.repository.CustomerRepository;
import com.techgen.service.impl.CustomerServiceImpl;

@MockitoSettings
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Test
	void test_getCustomerByCustomerId() {
		Customer customer = getCustomer();
		Mockito.when(customerRepository.findCustomerByCustomerId(Mockito.anyString()))
				.thenReturn(Optional.of(customer));
		CustomerDto customerDto = getCustomerDto(customer);
		Mockito.when(modelMapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(customerDto);
		CustomerDto customerDto1 = customerService.getCustomerByCustomerId(customer.getCustomerId());
		assertNotNull(customerDto1);
		assertEquals(customerDto.getCity(), customerDto1.getCity());
	}

	@Test
	void test_getCustomerByCustomerId_customer_id_not_found() {
		Mockito.when(customerRepository.findCustomerByCustomerId(Mockito.anyString())).thenReturn(Optional.empty());
		assertThrows(CustomerException.class, () -> customerService.getCustomerByCustomerId("test"));
	}

	@Test
	void test_getCustomers() {
		Customer customer = getCustomer();
		List<Customer> customers = new ArrayList<>();
		customers.add(customer);
		Mockito.when(customerRepository.findAll()).thenReturn(customers);
		CustomerDto customerDto = getCustomerDto(customer);
		Mockito.when(modelMapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(customerDto);
		List<CustomerDto> customerDtos = customerService.getCustomers();
		assertNotNull(customerDtos);
		assertEquals(customerDtos.size(), 1);
	}

	@Test
	void test_getCustomers_customers_not_found() {
		List<Customer> customers = new ArrayList<>();
		Mockito.when(customerRepository.findAll()).thenReturn(customers);
		assertThrows(CustomerException.class, () -> customerService.getCustomers());
	}

	@Test
	void test_createCustomer() {
		Customer customer = getCustomer();
		Mockito.when(modelMapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(customer);
		CustomerDto customerDto = getCustomerDto(customer);
		Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
		customerService.createCustomer(customerDto);
	}

	@Test
	void test_updateCustomerByCustomerId() {
		Customer customer = getCustomer();
		Mockito.when(customerRepository.findCustomerByCustomerId(Mockito.anyString()))
				.thenReturn(Optional.of(customer));
		Mockito.when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
		CustomerDto customerDto = getCustomerDto(customer);
		Mockito.when(modelMapper.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(customerDto);
		CustomerDto customerDto1 = customerService.updateCustomerByCustomerId(customerDto.getCustomerId(), customerDto);
		assertNotNull(customerDto1);
		assertEquals(customerDto.getCity(), customerDto1.getCity());
	}

	@Test
	void test_deleteCustomerByCustomerId() {
		Customer customer = getCustomer();
		Mockito.when(customerRepository.findCustomerByCustomerId(Mockito.anyString()))
				.thenReturn(Optional.of(customer));
		doNothing().when(customerRepository).delete(customer);
		Long customerId = customerService.deleteCustomerByCustomerId(customer.getCustomerId());
		assertNotNull(customerId);
		assertEquals(customerId, 1);
	}

	private CustomerDto getCustomerDto(Customer customer) {
		CustomerDto customerDto = new CustomerDto();
		customerDto.setId(customer.getId());
		customerDto.setName(customer.getName());
		customerDto.setCustomerId(customer.getCustomerId());
		customerDto.setCity(customer.getCity());
		return customerDto;
	}

	private Customer getCustomer() {
		Customer customer = new Customer();
		customer.setId(1l);
		customer.setName("Taher");
		customer.setCity("Indore");
		customer.setCustomerId("tah123");
		return customer;
	}
}
