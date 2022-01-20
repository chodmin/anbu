package com.dev.anbu.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dev.anbu.exceptions.CustomerException;
import com.dev.anbu.model.Customer;
import com.dev.anbu.model.Statistic;
import com.dev.anbu.repository.CustomerRepository;
import com.dev.anbu.service.CustomerService;
import com.dev.anbu.service.CustomerServiceImpl;
import com.dev.anbu.utils.StatisticUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private StatisticUtils statisticUtils;

    private Customer customer;
    private CustomerService customerService;

    @BeforeEach
    public void init() {
        customerService = new CustomerServiceImpl(customerRepository, statisticUtils);
        customer = new Customer();
        customer.setDateOfBirth(LocalDate.of(1989, 1, 9));
        customer.setName("Gustavo");
        customer.setLastName("Martinez");
    }

    @Test
    public void createCustomerCorrectTest() throws CustomerException {
        when(statisticUtils.getAge(customer.getDateOfBirth())).thenReturn(32);
        customerService.createCustomer(customer);
    }

    @Test
    public void createCustomerNullParameterTest() {
        assertThrows(CustomerException.class, () -> customerService.createCustomer(null));
    }

    @Test
    public void getStatisticCorrectTest() throws CustomerException {
        Double average = Double.valueOf(12.9);
        Double standardDeviation = Double.valueOf(15.5);
        List<Customer> customers = Arrays.asList(customer);

        when(customerRepository.findAll()).thenReturn(customers);
        when(statisticUtils.getAverage(customers)).thenReturn(average);
        when(statisticUtils.getStandardDeviation(customers, average)).thenReturn(standardDeviation);

        Statistic statistic = customerService.getStatistic();
        assertEquals(average, statistic.getAverage());
        assertEquals(standardDeviation, statistic.getStandardDeviation());
    }

    @Test
    public void getStatisticEmptyDataTest() throws CustomerException {
        Statistic statistic = customerService.getStatistic();
        assertEquals(Double.valueOf(0.0), statistic.getAverage());
        assertEquals(Double.valueOf(0.0), statistic.getStandardDeviation());
    }

    @Test
    public void getCustomersCorrectTest() throws CustomerException {
        List<Customer> customers = Arrays.asList(customer);
        when(customerRepository.findAll()).thenReturn(customers);
        List<Customer> customersTest = customerService.getCustomers();
        assertEquals(customers, customersTest);
    }

    @Test
    public void getCustomersEmptyDataTest() throws CustomerException {
        List<Customer> customers = customerService.getCustomers();
        assertEquals(0, customers.size());
    }

}
