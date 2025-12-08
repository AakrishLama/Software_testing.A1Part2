package org.example.service;

import org.example.model.Member;

public class NotificationService {

    public void sendBorrowConfirmation(Member member, String bookTitle) {
        String message = String.format(
                "Dear %s, you have successfully borrowed '%s'. Please return it on time to avoid fines.",
                member.getName(), bookTitle);
        sendEmail(member.getEmail(), "Book Borrowed", message);
    }

    public void sendReturnConfirmation(Member member, String bookTitle) {
        String message = String.format(
                "Dear %s, you have successfully returned '%s'. Thank you!",
                member.getName(), bookTitle);
        sendEmail(member.getEmail(), "Book Returned", message);
    }

    public void sendOverdueNotification(Member member, String bookTitle, int daysOverdue) {
        String message = String.format(
                "Dear %s, the book '%s' is overdue by %d days. Please return it immediately.",
                member.getName(), bookTitle, daysOverdue);
        sendEmail(member.getEmail(), "Overdue Book Alert", message);
    }

    public void sendFineNotification(Member member, double fineAmount) {
        String message = String.format(
                "Dear %s, you have been charged a fine of $%.2f. Please pay at your earliest convenience.",
                member.getName(), fineAmount);
        sendEmail(member.getEmail(), "Fine Notification", message);
    }

    private void sendEmail(String email, String subject, String message) {
        // Simulated email sending
        System.out.println("=== EMAIL SENT ===");
        System.out.println("To: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
        System.out.println("==================");
    }
}