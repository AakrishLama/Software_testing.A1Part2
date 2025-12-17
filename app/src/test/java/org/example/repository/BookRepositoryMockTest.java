package org.example.repository;

import org.example.model.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookRepositoryMockTest {

  @Mock
  private Book book;

  @Test
  void testSaveAndFind_withMockBook() {
    // Arrange
    BookRepository repository = new BookRepository();
    String isbn = "123";

    when(book.getIsbn()).thenReturn(isbn);
    when(book.getTitle()).thenReturn("Mock Title");

    // Act
    repository.save(book);
    Optional<Book> found = repository.findByIsbn(isbn);

    // Assert
    assertTrue(found.isPresent());
    assertEquals(book, found.get());
    assertEquals("Mock Title", found.get().getTitle());

    verify(book, atLeastOnce()).getIsbn();
  }
}
