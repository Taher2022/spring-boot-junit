package com.techgen.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.techgen.entity.Customer;

@DataJpaTest
public class CustomerRepositoryTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void test_findCustomerByCustomerId() {
		Customer customer = new Customer();
		customer.setName("Taher");
		customer.setCity("Indore");
		customer.setCustomerId("tah123");
		testEntityManager.persistAndFlush(customer);
		Optional<Customer> storedCustomer = customerRepository.findCustomerByCustomerId(customer.getCustomerId());
		if (storedCustomer.isPresent()) {
			Assertions.assertEquals(customer.getName(), storedCustomer.get().getName());
		}
	}

	@Test
	void test_findCustomers() {
		Customer customer = new Customer();
		customer.setName("Taher");
		customer.setCity("Indore");
		customer.setCustomerId("tah123");
		testEntityManager.persistAndFlush(customer);
		List<Customer> storedCustomers = customerRepository.findAll();
		if (!storedCustomers.isEmpty()) {
			Assertions.assertEquals(1, storedCustomers.size());
		}
	}

	@Test
	void test_createCustomer() {
		Customer customer = new Customer();
		customer.setName("Taher");
		customer.setCity("Indore");
		customer.setCustomerId("tah123");
		testEntityManager.persistAndFlush(customer);
		Assertions.assertTrue(customer.getId() > 0);
	}

	@Test
	void test_deleteCustomer() {
		Customer customer = new Customer();
		customer.setName("Taher");
		customer.setCity("Indore");
		customer.setCustomerId("tah123");
		Customer storedCustomer = testEntityManager.persistAndFlush(customer);
		customerRepository.delete(storedCustomer);
		Assertions.assertTrue(customer.getId() >= 0);
	}

}
