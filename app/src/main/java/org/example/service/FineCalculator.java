package org.example.service;

import java.time.LocalDate;

public class FineCalculator {

    public double calculateFine(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate.isBefore(dueDate)) {
            return 0.1;
        }
        return 0.0;
    }
}
