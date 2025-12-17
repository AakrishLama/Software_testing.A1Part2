package org.example.repository;

import org.example.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

class BookRepositoryTest {

  private BookRepository repository;

  @BeforeEach
  void setUp() {
    repository = new BookRepository();
  }

  @Test
  void testSaveAndFind() {
    Book book = new Book("123", "Title", "Author");
    repository.save(book);

    Optional<Book> found = repository.findByIsbn("123");
    assertTrue(found.isPresent());
    assertEquals("Title", found.get().getTitle());
  }

  @Test
  void testFindByIsbnNotFound() {
    Optional<Book> found = repository.findByIsbn("999");
    assertFalse(found.isPresent());
  }

  @Test
  void testFindAll() {
    repository.save(new Book("1", "T1", "A1"));
    repository.save(new Book("2", "T2", "A2"));

    List<Book> all = repository.findAll();
    assertEquals(2, all.size());
  }

  @Test
  void testFindAvailableBooks() {
    Book b1 = new Book("1", "T1", "A1");
    Book b2 = new Book("2", "T2", "A2");
    b2.setAvailable(false);

    repository.save(b1);
    repository.save(b2);

    List<Book> available = repository.findAvailableBooks();
    assertEquals(1, available.size());
    assertEquals("1", available.get(0).getIsbn());
  }

  @Test
  void testDelete() {
    Book book = new Book("123", "Title", "Author");
    repository.save(book);

    repository.delete("123");
    assertFalse(repository.findByIsbn("123").isPresent());
  }
}
