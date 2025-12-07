package org.example.service;

import java.time.LocalDate;

public class FineCalculator {
    private static final double FINE_PER_DAY = 0.5;

    public double calculateFine(LocalDate dueDate, LocalDate returnDate) {
        if (returnDate.isBefore(dueDate)) {
            return 0.0;
        }
        long daysOverdue = returnDate.toEpochDay() - dueDate.toEpochDay();
        return daysOverdue * FINE_PER_DAY;
    }
}
