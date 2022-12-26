package com.techgen.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techgen.dto.CustomerDto;
import com.techgen.service.CustomerService;

@WebMvcTest
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CustomerService customerService;

	CustomerDto customer;

	@BeforeEach
	void createCustomerDto() {
		customer = new CustomerDto();
		customer.setId(1l);
		customer.setCity("Indore");
		customer.setName("Taher");
		customer.setCustomerId("tah123");
	}

	@Test
	void test_getCustomerByCustomerId() throws Exception {
		Mockito.when(customerService.getCustomerByCustomerId(Mockito.anyString())).thenReturn(customer);
		mockMvc.perform(get("/api/customer/" + customer.getCustomerId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void test_getCustomers() throws Exception {
		List<CustomerDto> customers = new ArrayList<>();
		customers.add(customer);
		Mockito.when(customerService.getCustomers()).thenReturn(customers);
		mockMvc.perform(get("/api/customer/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void test_createCustomer() throws Exception {
		String customerJsonReq = objectMapper.writeValueAsString(customer);
		doNothing().when(customerService).createCustomer(customer);
		mockMvc.perform(post("/api/customer/").contentType(MediaType.APPLICATION_JSON).content(customerJsonReq))
				.andExpect(status().isCreated());
	}

	@Test
	void test_updateCustomerByCustomerId() throws Exception {
		String customerJsonReq = objectMapper.writeValueAsString(customer);
		Mockito.when(customerService.updateCustomerByCustomerId(Mockito.anyString(), Mockito.any(CustomerDto.class)))
				.thenReturn(customer);
		mockMvc.perform(put("/api/customer/" + customer.getCustomerId()).contentType(MediaType.APPLICATION_JSON)
				.content(customerJsonReq)).andExpect(status().isOk());
	}

	@Test
	void test_deleteCustomerByCustomerId() throws Exception {
		Mockito.when(customerService.deleteCustomerByCustomerId(Mockito.anyString())).thenReturn(1l);
		mockMvc.perform(delete("/api/customer/" + customer.getCustomerId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
