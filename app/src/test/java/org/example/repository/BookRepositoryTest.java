package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class BookTest {

  @Test
  void testBookConstructorAndGetters() {
    Book book = new Book("123", "Test Title", "Test Author");

    assertEquals("123", book.getIsbn());
    assertEquals("Test Title", book.getTitle());
    assertEquals("Test Author", book.getAuthor());
    assertTrue(book.isAvailable());
    assertNull(book.getBorrowedBy());
    assertNull(book.getDueDate());
  }

  @Test
  void testSetAvailable() {
    Book book = new Book("123", "Title", "Author");
    book.setAvailable(false);
    assertFalse(book.isAvailable());

    book.setAvailable(true);
    assertTrue(book.isAvailable());
  }

  @Test
  void testSetBorrowedBy() {
    Book book = new Book("123", "Title", "Author");
    book.setBorrowedBy("M1");
    assertEquals("M1", book.getBorrowedBy());

    book.setBorrowedBy(null);
    assertNull(book.getBorrowedBy());
  }

  @Test
  void testSetDueDate() {
    Book book = new Book("123", "Title", "Author");
    LocalDate date = LocalDate.now();
    book.setDueDate(date);
    assertEquals(date, book.getDueDate());
  }

  @Test
  void testToString() {
    Book book = new Book("123", "Title", "Author");
    String str = book.toString();
    assertTrue(str.contains("123"));
    assertTrue(str.contains("Title"));
    assertTrue(str.contains("Author"));
    assertTrue(str.contains("Available"));

    book.setAvailable(false);
    assertTrue(book.toString().contains("Borrowed"));
  }
}
