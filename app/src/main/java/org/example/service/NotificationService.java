package org.example.service;

import java.time.LocalDate;

import org.example.model.Member;

public class NotificationService {
    public String createBorrowMessage(Member member, String bookTitle) {
        return String.format(
                "Dear %s, you have successfully borrowed '%s'. Please return it on time to avoid fines.",
                member.getName(), bookTitle);
    }

    public String createReturnMessage(Member member, String bookTitle) {
        return String.format(
                "Dear %s, you have successfully returned '%s'. Thank you!.",
                member.getName(), bookTitle);
    }

    public String createOverdueMessage(Member member, LocalDate dueDate, String bookTitle) {
        long daysOverdue = java.time.temporal.ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        return String.format(
                "Dear %s, the book '%s' is overdue by %d days. Please return it immediately.",
                member.getName(), bookTitle, daysOverdue);
    }

    public String createFineMessage(Member member, double fineAmount) {
        return String.format(
                "Dear %s, you have been charged a fine of $%.2f. Please pay at your earliest convenience.",
                member.getName(), fineAmount);
    }
}
