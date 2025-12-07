package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class FineCalculatorTest {

    @Test
    public void testFineCalculatorCreation() {
        FineCalculator calculator = new FineCalculator();
        assertNotNull(calculator);
    }

    @Test
    public void testCalculateFineForOnTimeReturn() {
        // Given
        FineCalculator calculator = new FineCalculator();
        LocalDate dueDate = LocalDate.of(2024, 1, 15);
        LocalDate returnDate = LocalDate.of(2024, 1, 15); // Same day

        // When
        double fine = calculator.calculateFine(dueDate, returnDate);

        // Then
        assertEquals(0.0, fine);
    }

    @Test
    public void testCalculateFineForEarlyReturn() {

        FineCalculator calculator = new FineCalculator();
        LocalDate dueDate = LocalDate.of(2024, 1, 15);
        LocalDate returnDate = LocalDate.of(2024, 1, 10); // 5 days early

        double fine = calculator.calculateFine(dueDate, returnDate);

        assertEquals(0.0, fine);
    }

}
