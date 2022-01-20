package com.dev.anbu.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dev.anbu.model.Customer;

@Component
public class StatisticUtils {

    private int sample;
    private int deathDaysMin;
    private int deathDaysMax;

    public StatisticUtils(
            @Value("${statistics.sample}") int sample,
            @Value("${statistics.death-days.min}") int deathDaysMin,
            @Value("${statistics.death-days.max}") int deathDaysMax) {
        this.sample = sample;
        this.deathDaysMin = deathDaysMin;
        this.deathDaysMax = deathDaysMax;
    }

    public int getAge(LocalDate dateOfBirth) {
        Period age = Period.between(dateOfBirth, LocalDate.now());
        return age.getYears();
    }

    public Double getAverage(List<Customer> customers) {
        Double average = customers.stream()
                .mapToDouble(Customer::getAge)
                .average()
                .orElse(Double.NaN);
        return average;
    }

    public Double getStandardDeviation(List<Customer> customers, Double average) {
        int divider = customers.size() - sample;
        Double sumOfSquares = customers.stream()
                .mapToDouble(Customer::getAge)
                .map(age -> age - average)
                .map(res -> res * res)
                .sum();
        Double standardDeviation = Math.sqrt(sumOfSquares / divider);
        return standardDeviation;
    }

    public void setDateOfDeath(List<Customer> customers) {
        customers.forEach(cust -> {
            int daysOfLife = new Random().nextInt(deathDaysMax - deathDaysMin) - deathDaysMin;
            if (daysOfLife < 0) {
                daysOfLife = daysOfLife * -1;
            }
            LocalDate dateOfDeath = cust.getDateOfBirth().plusDays(daysOfLife);
            cust.setDateOfDeath(dateOfDeath);
        });
    }

}
