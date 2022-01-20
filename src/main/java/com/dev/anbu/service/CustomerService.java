package com.dev.anbu.service;

import java.util.List;

import com.dev.anbu.exceptions.CustomerException;
import com.dev.anbu.model.Customer;
import com.dev.anbu.model.Statistic;

public interface CustomerService {

    void createCustomer(Customer customer) throws CustomerException;

    Statistic getStatistic() throws CustomerException;

    List<Customer> getCustomers() throws CustomerException;

}
