package com.dev.anbu.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.dev.anbu.controller.CustomerController;
import com.dev.anbu.exceptions.CustomerException;
import com.dev.anbu.model.Customer;
import com.dev.anbu.model.Statistic;
import com.dev.anbu.service.CustomerService;
import com.dev.anbu.service.CustomerServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    private Customer customer;
    private CustomerController customerController;

    @BeforeEach
    public void init() {
        customerController = new CustomerController(customerService);
        customer = new Customer();
        customer.setDateOfBirth(LocalDate.of(1989, 1, 9));
        customer.setName("Jose");
        customer.setLastName("Aguero");
    }

    @Test
    public void createCustomerOKTest() {
        ResponseEntity<Void> response = customerController.createCustomer(customer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void createCustomerNullParameterTest() {
        customerController = new CustomerController(new CustomerServiceImpl(null, null));
        ResponseEntity<Void> response = customerController.createCustomer(null);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void getStatisticOKTest() {
        ResponseEntity<Statistic> response = customerController.getStatistic();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getStatisticCheckBodyTest() throws CustomerException {
        when(customerService.getStatistic()).thenReturn(new Statistic());
        ResponseEntity<Statistic> response = customerController.getStatistic();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getStatisticFailTest() {
        customerController = new CustomerController(new CustomerServiceImpl(null, null));
        ResponseEntity<Statistic> response = customerController.getStatistic();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getCustumersOKTest() {
        ResponseEntity<List<Customer>> response = customerController.getCustomers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getCustumersCheckBodyTest() throws CustomerException {
        when(customerService.getCustomers()).thenReturn(Arrays.asList(customer));
        ResponseEntity<List<Customer>> response = customerController.getCustomers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void getCustumersFailTest() {
        customerController = new CustomerController(new CustomerServiceImpl(null, null));
        ResponseEntity<List<Customer>> response = customerController.getCustomers();
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
