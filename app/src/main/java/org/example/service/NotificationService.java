package org.example.service;

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
}
