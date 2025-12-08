package org.example.service;

import org.example.model.Member;

public class NotificationService {

    public void sendBorrowConfirmation(Member member, String bookTitle) {
        String message = String.format(
                "Dear %s, you have successfully borrowed '%s'. Please return it on time to avoid fines.",
                member.getName(), bookTitle);
    }

    public void sendReturnConfirmation(Member member, String bookTitle) {
        String message = String.format(
                "Dear %s, you have successfully returned '%s'. Thank you!",
                member.getName(), bookTitle);
    }

    public void sendOverdueNotification(Member member, String bookTitle, int daysOverdue) {
        String message = String.format(
                "Dear %s, the book '%s' is overdue by %d days. Please return it immediately.",
                member.getName(), bookTitle, daysOverdue);
    }

    public void sendFineNotification(Member member, double fineAmount) {
        String message = String.format(
                "Dear %s, you have a fine of $%.2f for overdue books. Please pay it as soon as possible.",
                member.getName(), fineAmount);
    }

}
