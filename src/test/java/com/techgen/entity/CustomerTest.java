package com.techgen.entity;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public class CustomerTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Test
	void testCustomer() {
		Customer customer = new Customer();
		customer.setName("Taher");
		customer.setCity("Indore");
		customer.setCustomerId("tah123");
		Customer storedCustomer = testEntityManager.persistAndFlush(customer);
		Assertions.assertTrue(customer.getId() > 0);
		Assertions.assertEquals(customer.getName(), storedCustomer.getName());
		Assertions.assertEquals(customer.getCity(), storedCustomer.getCity());
		Assertions.assertEquals(customer.getCustomerId(), storedCustomer.getCustomerId());
	}

	@Test
	void testCustomer_duplicate_cust_id_exception() {
		Customer customer = new Customer();
		customer.setName("Taha");
		customer.setCity("Indore");
		customer.setCustomerId("tah123");
		testEntityManager.persistAndFlush(customer);

		Customer customer1 = new Customer();
		customer1.setName("Taha");
		customer1.setCity("Indore");
		customer1.setCustomerId("tah123");
		assertThrows(PersistenceException.class, () -> {
			testEntityManager.persistAndFlush(customer1);
		});
	}

}
