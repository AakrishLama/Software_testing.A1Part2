package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.bytebuddy.asm.Advice.Local;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

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

    @Test
    void testSetAvailibility() {
        // checks that a new book is available
        assertTrue(book.isAvailable());

        // then we test set false
        book.setAvailable(false);
        assertFalse(book.isAvailable());

        // then true
        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }

    @Test
    void testSetBorrowedBy() {
        assertNull(book.getBorrowedBy());

        book.setBorrowedBy("John Doe");
        assertEquals("John Doe", book.getBorrowedBy());
    }

    @Test
    void testSetDueDate() {
        assertNull(book.getDueDate());

        // we need to make a date object
        LocalDate dueDate = LocalDate.of(2025, 12, 18);
        book.setDueDate(dueDate);

        assertEquals(dueDate, book.getDueDate());
    }

    @Test
    void testBorrowLogic() {
        LocalDate dueDateTest = LocalDate.of(2025, 12, 24);

        // use setters to simulate borrow book
        book.setAvailable(false);
        book.setBorrowedBy("Jane Doe");
        book.setDueDate(dueDateTest);

        // use assertions to match expected values
        assertFalse(book.isAvailable());
        assertEquals("Jane Doe", book.getBorrowedBy());
        assertEquals(dueDateTest, book.getDueDate());
    }

    @Test
    void testReturnLogic() {
        // use setters to simulate return book
        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setDueDate(null);

        // use assertions to match expected values
        assertTrue(book.isAvailable());
        assertNull(book.getBorrowedBy());
        assertNull(book.getDueDate());
    }
}
