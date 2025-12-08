package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.model.Member;

public class NotificationServiceTest {
    private NotificationService notificationService;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        notificationService = new NotificationService();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testNotificationServiceCreation() {
        assertNotNull(notificationService);
    }

    @Test
    public void testSendBorrowConfirmation() {
        Member member = new Member("John", "john@example.com", "1234567890");
        String bookTitle = "book";
        notificationService.sendBorrowConfirmation(member, bookTitle);
        String output = outputStream.toString();
        assertFalse(output.isEmpty());
    }

    @Test
    public void testSendReturnConfirmation() {
        Member member = new Member("John", "john@example.com", "1234567890");
        String bookTitle = "koob";
        notificationService.sendReturnConfirmation(member, bookTitle);
    }

    @Test
    public void testSendOverdueNotification() {
        Member member = new Member("John", "john@example.com", "1234567890");
        String bookTitle = "koob";
        int daysOverdue = 5;
        notificationService.sendOverdueNotification(member, bookTitle, daysOverdue);
    }

    @Test
    public void testSendFineNotification() {
        Member member = new Member("John", "john@example.com", "1234567890");
        double fineAmount = 10.0;
        notificationService.sendFineNotification(member, fineAmount);
    }

    @Test
    public void testSendBorrowConfirmationPrintsMessage() {
        // Given
        Member member = new Member("John Doe", "john@example.com", "1234567890");
        String bookTitle = "The Great Gatsby";

        // When
        notificationService.sendBorrowConfirmation(member, bookTitle);

        // Then - Check that something was printed
        String output = outputStream.toString();
        assertFalse(output.isEmpty());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }
}
