package org.example.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class NotificationServiceTest {
    @Test
    public void testNotificationServiceCreation() {
        NotificationService notificationService = new NotificationService();
        assertNotNull(notificationService);
    }
}
