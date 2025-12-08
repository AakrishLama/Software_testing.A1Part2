package org.example.service;

import org.example.model.Member;

public class NotificationService {

    public void sendBorrowConfirmation(Member member, String bookTitle) {
        String message = String.format(
                "Dear %s, you have successfully borrowed '%s'. Please return it on time to avoid fines.",
                member.getName(), bookTitle);
        System.out.println(message);
    }

}
