package com.dev.anbu.controller;

import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anbu.exceptions.CustomerException;
import com.dev.anbu.model.Customer;
import com.dev.anbu.model.Statistic;
import com.dev.anbu.service.CustomerService;

@RestController
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/creacliente")
    public ResponseEntity<Void> createCustomer(@RequestBody @Valid Customer customer) {
        try {
            customerService.createCustomer(customer);
        } catch (CustomerException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/kpideclientes")
    public ResponseEntity<Statistic> getStatistic() {
        Statistic statistic;
        try {
            statistic = customerService.getStatistic();
        } catch (CustomerException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound()
                    .build();
        }
        return ResponseEntity.ok(statistic);
    }

    @GetMapping("/listclientes")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> customers;
        try {
            customers = customerService.getCustomers();
        } catch (CustomerException e) {
            log.error(e.getMessage());
            return ResponseEntity.noContent()
                    .build();
        }
        return ResponseEntity.ok(customers);
    }

}
