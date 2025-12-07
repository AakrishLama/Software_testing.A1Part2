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
}