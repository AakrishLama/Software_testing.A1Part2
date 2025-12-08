package org.example.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.example.model.Member;

public class NotificationServiceTest {
    @Test
    public void testNotificationServiceCreation() {
        NotificationService notificationService = new NotificationService();
        assertNotNull(notificationService);
    }

    @Test
    public void testSendBorrowConfirmation() {
        NotificationService service = new NotificationService();
        Member member = new Member("John", "john@example.com", "1234567890");
        String bookTitle = "book";
        service.sendBorrowConfirmation(member, bookTitle);
    }

    @Test
    public void testSendReturnConfirmation() {
        NotificationService service = new NotificationService();
        Member member = new Member("John", "john@example.com", "1234567890");
        String bookTitle = "koob";
        service.sendReturnConfirmation(member, bookTitle);
    }
}
