package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FineCalculatorTest {
    private FineCalculator fineCalculator;

    @BeforeEach
    public void setUp() {
        fineCalculator = new FineCalculator();
    }

    @Test
    public void testFineCalculatorCreation() {
        assertNotNull(fineCalculator);
    }

    @Test
    public void testCalculateFineForOnTimeReturn() {
        // Given
        LocalDate dueDate = LocalDate.of(2024, 1, 15);
        LocalDate returnDate = LocalDate.of(2024, 1, 15); // Same day

        // When
        double fine = fineCalculator.calculateFine(dueDate, returnDate);

        // Then
        assertEquals(0.0, fine);
    }

    @Test
    public void testCalculateFineForEarlyReturn() {

        LocalDate dueDate = LocalDate.of(2024, 1, 15);
        LocalDate returnDate = LocalDate.of(2024, 1, 10); // 5 days early

        double fine = fineCalculator.calculateFine(dueDate, returnDate);

        assertEquals(0.0, fine);
    }

    @Test
    public void testCalculateFineForOverdueReturn() {

        LocalDate dueDate = LocalDate.of(2024, 1, 15);
        LocalDate returnDate = LocalDate.of(2024, 1, 20); // 5 days late

        System.out.println("return date: " + returnDate);
        System.out.println("due date: " + dueDate);

        double fine = fineCalculator.calculateFine(dueDate, returnDate);
        // logic of 0.5 fine per day
        assertEquals(2.5, fine);
    }

    @Test
    public void testCalculateFineWithNullDueDate() {
        LocalDate returnDate = LocalDate.now();

        assertThrows(IllegalArgumentException.class, () -> {
            fineCalculator.calculateFine(null, returnDate);
        });
    }

    @Test
    public void testCalculateFineWithNullReturnDate() {
        LocalDate dueDate = LocalDate.now();

        assertThrows(IllegalArgumentException.class, () -> {
            fineCalculator.calculateFine(dueDate, null);
        });
    }

    @Test
    public void testCalculateCurrentFine() {
        LocalDate dueDate = LocalDate.now().minusDays(3);

        double fine = fineCalculator.calculateCurrentFine(dueDate);
        assertTrue(fine >= 1.5);
    }

}
