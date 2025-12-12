package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    private Book book;
    // final static makes it a constant so can't be reinitialized
    private static final String ISBN = "978-3-16-138410-0";
    private static final String TITLE = "Blood Meridian";
    private static final String AUTHOR = "Cormac McCarthy";

    @BeforeEach
    public void setUp() {
        book = new Book(ISBN, TITLE, AUTHOR);
    }

    @Test
    void shouldHaveValidIsbn() {
        assertEquals(ISBN, book.getIsbn());
    }

    @Test
    void shouldHaveValidTitle() {
        assertEquals(TITLE, book.getTitle());
    }

    @Test
    void shouldBeAvailable() {
        assertTrue(book.isAvailable());
    }

    @Test
    void shouldHaveNullBorrowedBy() {
        assertNull(book.getBorrowedBy());
    }

    @Test
    void shouldHaveNullDueDate() {
        assertNull(book.getDueDate());
    }
}
