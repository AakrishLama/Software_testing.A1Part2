package org.example.repository;

import org.example.model.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookRepository {
  private final Map<String, Book> books = new HashMap<>();

  public void save(Book book) {
    books.put(book.getIsbn(), book);
  }

  public Optional<Book> findByIsbn(String isbn) {
    return Optional.ofNullable(books.get(isbn));
  }

  public List<Book> findAll() {
    return books.values().stream().collect(Collectors.toList());
  }

  public List<Book> findAvailableBooks() {
    return books.values().stream()
        .filter(Book::isAvailable)
        .collect(Collectors.toList());
  }

  public void delete(String isbn) {
    books.remove(isbn);
  }
}