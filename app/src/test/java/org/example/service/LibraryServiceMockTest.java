package org.example.service;

import org.example.model.Book;
import org.example.model.Member;
import org.example.repository.BookRepository;
import org.example.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibraryServiceMockTest {

  @Mock
  private BookRepository bookRepository;

  @Mock
  private MemberRepository memberRepository;

  @Mock
  private NotificationService notificationService;

  @Mock
  private FineCalculator fineCalculator;

  @InjectMocks
  private LibraryService libraryService;

  @Test
  void testAddBook_savesBookToRepository() {
    // Arrange
    String isbn = "123";
    String title = "Mockito in Action";
    String author = "Rogério";

    // Act
    libraryService.addBook(isbn, title, author);

    // Assert
    verify(bookRepository).save(any(Book.class));
  }

  @Test
  void testBorrowBook_success() {
    // Arrange
    String isbn = "123";
    String memberId = "M1";

    Book book = new Book(isbn, "Title", "Author");
    Member member = new Member(memberId, "John", "john@example.com");

    when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(book));
    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));

    // Act
    boolean result = libraryService.borrowBook(memberId, isbn);

    // Assert
    assertTrue(result);
    assertFalse(book.isAvailable());
    assertEquals(memberId, book.getBorrowedBy());

    verify(bookRepository).save(book);
    verify(memberRepository).save(member);
    verify(notificationService).sendBorrowConfirmation(member, book.getTitle());
  }

  @Test
  void testReturnBook_calculatesFine() {
    // Arrange
    String isbn = "123";
    String memberId = "M1";

    Book book = new Book(isbn, "Title", "Author");
    book.setAvailable(false);
    book.setBorrowedBy(memberId);
    // Due date was yesterday
    LocalDate dueDate = LocalDate.now().minusDays(1);
    book.setDueDate(dueDate);

    Member member = new Member(memberId, "John", "john@example.com");
    member.addBorrowedBook(isbn);

    when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(book));
    when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
    when(fineCalculator.calculateCurrentFine(dueDate)).thenReturn(5.0);

    // Act
    double fine = libraryService.returnBook(memberId, isbn);

    // Assert
    assertEquals(5.0, fine);
    assertTrue(book.isAvailable());

    verify(fineCalculator).calculateCurrentFine(dueDate);
    verify(notificationService).sendFineNotification(member, 5.0);
    verify(notificationService).sendReturnConfirmation(member, book.getTitle());
  }
}
