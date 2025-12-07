package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MemberTest {

    private Member member;
    private static final String MEMBERID = "M001";
    private static final String NAME = "John Doe";
    private static final String EMAIL = "john@example.com";

    @BeforeEach
    public void setUp() {
        member = new Member(MEMBERID, NAME, EMAIL);
    }

    @Test
    public void testMemberCreation() {
        assertEquals(MEMBERID, member.getMemberId());
        assertEquals(NAME, member.getName());
        assertEquals(EMAIL, member.getEmail());
    }

    @Test
    public void testInitialBorrowedBookIsEmpty() {
        // check if the member has no borrowed books
        assertNotNull(member.getBorrowedBooks());

        assertTrue(member.getBorrowedBooks().isEmpty());
    }

    @Test
    public void testAddBorrowedBook() {
        String isbn = "1234";
        member.addBorrowedBook(isbn);

        List<String> borrowedBooks = member.getBorrowedBooks();
        assertEquals(1, borrowedBooks.size());
        assertEquals(isbn, borrowedBooks.get(0));
    }

    @Test
    public void testRemoveBorrowedBook() {
        String isbn = "1234";
        member.addBorrowedBook(isbn);

        member.removeBorrowedBook(isbn);

        List<String> borrowedBooks = member.getBorrowedBooks();
        assertEquals(0, borrowedBooks.size());
    }

    @Test
    public void testInitialTotalFinesIsZero() {
        assertEquals(0.0, member.getTotalFines());
    }

    @Test
    public void testAddFines() {
        member.addFines(5.0);
        assertEquals(5.0, member.getTotalFines());
    }

    @Test
    public void testRemoveFines() {
        member.addFines(5.0);
        member.removeFines(2.0);
        assertEquals(3.0, member.getTotalFines());
    }

    @Test
    public void testMultipleFines() {
        member.addFines(5.0);
        member.addFines(10.0);
        // check if the total fines is 15.0
        assertEquals(15.0, member.getTotalFines());
    }

    @Test
    public void testPayFine() {
        member.addFines(5.0);
        member.payFines(2.0);
        assertEquals(3.0, member.getTotalFines());
    }

    @Test
    public void testPayFineCannotBeNegative() {
        member.addFines(5.0);
        member.payFines(10.0);
        assertEquals(0.0, member.getTotalFines());
    }

    @Test
    public void testCanBorrowUnderLimitAndNoFines() {
        assertTrue(member.canBorrow());
    }

    @Test
    public void testCannotBorrowWhenTooManyBooks() {
        // Add 5 books
        for (int i = 1; i <= 5; i++) {
            member.addBorrowedBook("ISBN" + i);
        }

        assertFalse(member.canBorrow());
    }

    @Test
    public void testCannotBorrowWhenHighFines() {
        // add fine of 15
        member.addFines(15.0);

        assertFalse(member.canBorrow());
    }

    @Test
    public void testCanBorrowWhen4BooksAndLowFines() {
        // Add 4 books
        for (int i = 1; i <= 4; i++) {
            member.addBorrowedBook("ISBN" + i);
        }
        member.addFines(5.0);

        assertFalse(member.canBorrow());
    }
}