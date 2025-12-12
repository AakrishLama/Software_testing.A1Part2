package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.example.model.Book;
import org.example.model.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotificationServiceTest {
    private NotificationService ns;
    private Member member;
    private Book book;
    private FineCalculator fc;

    @BeforeEach
    public void setUp() {
        ns = new NotificationService();
        member = new Member("001", "Jane Doe", "janedoe@gmail.com");
        book = new Book("978-3-16-138410-0", "To Kill a Mockingbird", "Harper Lee");
        fc = new FineCalculator();
    }

    @Test
    void testBorrowConfirmation() {
        // use params of notification service
        String message = ns.createBorrowMessage(member, book.getTitle());

        assertTrue(message.contains("Dear Jane Doe"));
        assertTrue(message.contains("To Kill a Mockingbird"));
        assertTrue(message.contains("return it on time"));
    }

    @Test
    void testReturnConfirmation() {
        String message = ns.createReturnMessage(member, book.getTitle());

        assertTrue(message.contains("Dear Jane Doe"));
        assertTrue(message.contains("To Kill a Mockingbird"));
        assertTrue(message.contains("successfully returned"));
    }

    @Test
    void testOverdueNotification() {
        String message = ns.createOverdueMessage(member, book.getTitle());

        assertTrue(message.contains("Dear Jane Doe"));
        assertTrue(message.contains("To Kill a Mockingbird"));
        assertTrue(message.contains("is overdue by"));
    }

    @Test
    void testFineNotification() {
        // create mock object
        Book bookTest = new Book("978-3-16-138410-0", "To Kill a Mockingbird", "Harper Lee");
        LocalDate dueDate = LocalDate.of(2026, 12, 18);
        bookTest.setDueDate(dueDate);

        // create the amount based on the book
        double fineAmount = fc.calculateCurrentFine(book.getDueDate());

        String message = ns.createFineMessage(member, book.getTitle(), fineAmount);

        assertTrue(message.contains("Dear Jane Doe"));
        assertTrue(message.contains("you have been charged a fine"));
    }
}
