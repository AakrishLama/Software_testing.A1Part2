package org.example.service;

import org.example.model.Member;

public class NotificationService {
    public String sendBorrowConfirmation(Member member, String bookTitle) {
        String message = "Successfully borrowed book.";
        return message;
    }
}
