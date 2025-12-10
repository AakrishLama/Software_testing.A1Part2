package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.model.Member;

public class NotificationServiceTest {
    private NotificationService service;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private Member testMember;

    @BeforeEach
    public void setUp() {
        service = new NotificationService();
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        testMember = new Member("1", "test", "test@example.com");
    }

    @AfterEach
    public void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    public void testNotificationServiceCreation() {
        assertNotNull(service);
    }

    @Test
    public void testSendBorrowConfirmationPrintsMessage() {
        // When
        service.sendBorrowConfirmation(testMember, "Test Book");

        // Then
        assertFalse(outputStream.toString().isEmpty());
    }

    @Test
    public void testSendBorrowConfirmationContainsCorrectInfo() {
        // When
        service.sendBorrowConfirmation(testMember, "Test Book");

        // Then
        String output = outputStream.toString();
        assertTrue(output.contains("test"));
        assertTrue(output.contains("Test Book"));
        assertTrue(output.contains("test@example.com"));
    }

    @Test
    public void testSendReturnConfirmation() {
        service.sendReturnConfirmation(testMember, "Returned Book");
        String output = outputStream.toString();
        assertTrue(output.contains("Returned Book"));
        assertTrue(output.contains("Book Returned"));
    }

    @Test
    public void testSendOverdueNotification() {
        service.sendOverdueNotification(testMember, "Overdue Book", 3);
        String output = outputStream.toString();
        assertTrue(output.contains("3 days"));
        assertTrue(output.contains("Overdue Book Alert"));
    }

    @Test
    public void testSendFineNotificationFormatsAmountCorrectly() {
        service.sendFineNotification(testMember, 7.50);
        String output = outputStream.toString();
        assertTrue(output.contains("$7.50"));
        assertTrue(output.contains("Fine Notification"));
    }
}