package com.dev.anbu.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import com.dev.anbu.model.Customer;
import com.dev.anbu.utils.StatisticUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatisticUtilsTest {

    private final StatisticUtils statisticUtils = new StatisticUtils(1, 35000, 45000);
    private List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());

    @Test
    public void getAgeCorrectTest() {
        LocalDate dateTest = LocalDate.of(1989, 1, 9);
        int ageTest = statisticUtils.getAge(dateTest);
        Period age = Period.between(dateTest, LocalDate.now());
        assertEquals(age.getYears(), ageTest);
    }

    @Test
    public void getAgeCheckWrongTest() {
        int age = statisticUtils.getAge(LocalDate.of(2010, 11, 9));
        assertNotEquals(5, age);
    }

    @Test
    public void getAgeNullParameterTest() {
        assertThrows(Exception.class, () -> statisticUtils.getAge(null));
    }

    @Test
    public void getAverageCorrectTest() {
        customers.get(0).setAge(5);
        customers.get(1).setAge(10);
        customers.get(2).setAge(15);
        Double average = statisticUtils.getAverage(customers);
        assertEquals(10, average);
    }

    @Test
    public void getAverageCheckWrongTest() {
        customers.get(0).setAge(15);
        customers.get(1).setAge(10);
        customers.get(2).setAge(15);
        Double average = statisticUtils.getAverage(customers);
        assertNotEquals(10, average);
    }

    @Test
    public void getAverageNullParameterTest() {
        assertThrows(Exception.class, () -> statisticUtils.getAverage(null));
    }

    @Test
    public void getAverageEmptyListParameterTest() {
        customers = new ArrayList<>();
        Double average = statisticUtils.getAverage(customers);
        assertEquals(Double.NaN, average);
    }

    @Test
    public void getAverageListWithEmptyElementsParameterTest() {
        Double average = statisticUtils.getAverage(customers);
        assertEquals(0, average);
    }

    @Test
    public void getStandardDeviationCorrectTest() {
        customers = Arrays.asList(new Customer(), new Customer(), new Customer(), new Customer(), new Customer());
        customers.get(0).setAge(32);
        customers.get(1).setAge(21);
        customers.get(2).setAge(49);
        customers.get(3).setAge(12);
        customers.get(4).setAge(70);
        Double standardDeviation = statisticUtils.getStandardDeviation(customers, Double.valueOf(36.8));
        assertEquals(23.12358103754693, standardDeviation);
    }

    @Test
    public void getStandardDeviationCheckWrongTest() {
        customers.get(0).setAge(32);
        customers.get(1).setAge(21);
        customers.get(2).setAge(49);
        Double standardDeviation = statisticUtils.getStandardDeviation(customers, Double.valueOf(34));
        assertNotEquals(21.12358103754693, standardDeviation);
    }

    @Test
    public void getStandardDeviationNullParametersTest() {
        assertThrows(Exception.class, () -> statisticUtils.getStandardDeviation(null, null));
    }

    @Test
    public void getStandardDeviationNullFirstParameterTest() {
        assertThrows(Exception.class, () -> statisticUtils.getStandardDeviation(null, Double.valueOf(34)));
    }

    @Test
    public void getStandardDeviationNullSecondParameterTest() {
        assertThrows(Exception.class, () -> statisticUtils.getStandardDeviation(customers, null));
    }

    @Test
    public void getStandardDeviationEmptyParametersTest() {
        customers = new ArrayList<>();
        Double standardDeviation = statisticUtils.getStandardDeviation(customers, Double.valueOf(0));
        assertEquals(-0.0, standardDeviation);
    }

    @Test
    public void setDateOfDeathCorrectProbableTest() {
        customers.get(0).setDateOfBirth(LocalDate.of(1999, 2, 1));
        customers.get(1).setDateOfBirth(LocalDate.of(2009, 11, 19));
        customers.get(2).setDateOfBirth(LocalDate.of(1945, 5, 4));
        statisticUtils.setDateOfDeath(customers);

        customers.forEach(customer -> {
            Period age = Period.between(customer.getDateOfBirth(), customer.getDateOfDeath());
            assertTrue(age.getYears() > 40);
            assertTrue(age.getYears() < 130);
        });
    }

    @Test
    public void setDateOfDeathCheckWrongTest() {
        customers.get(0).setDateOfBirth(LocalDate.of(1990, 2, 1));
        customers.get(1).setDateOfBirth(LocalDate.of(2013, 11, 19));
        customers.get(2).setDateOfBirth(LocalDate.of(1976, 5, 4));
        statisticUtils.setDateOfDeath(customers);

        customers.forEach(customer -> {
            Period age = Period.between(customer.getDateOfBirth(), customer.getDateOfDeath());
            assertFalse(age.getYears() < 40);
            assertFalse(age.getYears() > 130);
        });
    }

    @Test
    public void setDateOfDeathNullParameterTest() {
        assertThrows(Exception.class, () -> statisticUtils.setDateOfDeath(null));
    }

    @Test
    public void setDateOfDeathEmptyParameterTest() {
        customers = new ArrayList<>();
        statisticUtils.setDateOfDeath(customers);
        assertEquals(0, customers.size());
    }

    @Test
    public void setDateOfDeathListWithEmptyElementsParameterTest() {
        assertThrows(Exception.class, () -> statisticUtils.setDateOfDeath(customers));
    }

}
