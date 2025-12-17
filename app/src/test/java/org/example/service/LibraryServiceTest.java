package org.example.service;

import org.example.model.Book;
import org.example.model.Member;
import org.example.repository.BookRepository;
import org.example.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceTest {

  private LibraryService libraryService;
  private BookRepository bookRepository;
  private MemberRepository memberRepository;
  private FakeNotificationService notificationService;
  private FineCalculator fineCalculator;

  // A simple fake to verify notifications without sending emails
  static class FakeNotificationService extends NotificationService {
    java.util.List<String> messages = new java.util.ArrayList<>();

    @Override
    public void sendBorrowConfirmation(Member member, String bookTitle) {
      messages.add("Borrow: " + member.getName() + " - " + bookTitle);
    }

    @Override
    public void sendReturnConfirmation(Member member, String bookTitle) {
      messages.add("Return: " + member.getName() + " - " + bookTitle);
    }

    @Override
    public void sendFineNotification(Member member, double fineAmount) {
      messages.add("Fine: " + member.getName() + " - " + fineAmount);
    }
  }

  @BeforeEach
  void setUp() {
    bookRepository = new BookRepository();
    memberRepository = new MemberRepository();
    notificationService = new FakeNotificationService();
    fineCalculator = new FineCalculator();

    libraryService = new LibraryService(bookRepository, memberRepository, notificationService, fineCalculator);
  }

  @Test
  void testAddBook() {
    libraryService.addBook("123", "Title", "Author");
    Book book = libraryService.getBook("123");
    assertNotNull(book);
    assertEquals("Title", book.getTitle());
  }

  @Test
  void testRegisterMember() {
    libraryService.registerMember("M1", "John", "email");
    Member member = libraryService.getMember("M1");
    assertNotNull(member);
    assertEquals("John", member.getName());
  }

  @Test
  void testBorrowBookSuccess() {
    libraryService.addBook("123", "Title", "Author");
    libraryService.registerMember("M1", "John", "email");

    boolean success = libraryService.borrowBook("M1", "123");
    assertTrue(success);

    Book book = libraryService.getBook("123");
    assertFalse(book.isAvailable());
    assertEquals("M1", book.getBorrowedBy());
    assertTrue(notificationService.messages.stream().anyMatch(m -> m.contains("Borrow")));
  }

  @Test
  void testBorrowBookFailures() {
    libraryService.addBook("123", "Title", "Author");
    libraryService.registerMember("M1", "John", "email");

    // 1. Book not found -> Exception
    assertThrows(IllegalArgumentException.class, () -> libraryService.borrowBook("M1", "999"));

    // 2. Member not found -> Exception
    assertThrows(IllegalArgumentException.class, () -> libraryService.borrowBook("M99", "123"));

    // 3. Already borrowed
    libraryService.borrowBook("M1", "123");
    boolean success = libraryService.borrowBook("M1", "123");
    assertFalse(success);
  }

  @Test
  void testReturnBookSuccess() {
    libraryService.addBook("123", "Title", "Author");
    libraryService.registerMember("M1", "John", "email");
    libraryService.borrowBook("M1", "123");

    double fine = libraryService.returnBook("M1", "123");
    assertEquals(0.0, fine);

    Book book = libraryService.getBook("123");
    assertTrue(book.isAvailable());
    assertNull(book.getBorrowedBy());
    assertTrue(notificationService.messages.stream().anyMatch(m -> m.contains("Return")));
  }

  @Test
  void testReturnBookWithFine() {
    libraryService.addBook("123", "Title", "Author");
    libraryService.registerMember("M1", "John", "email");
    libraryService.borrowBook("M1", "123");

    // Simulate overdue by manipulating the book's due date directly
    // Since we can't easily mock private LocalDate.now() inside
    // LibraryService/FineCalculator without more complex tools,
    // we will manually adjust the due date on the book object to be in the past.
    Book book = libraryService.getBook("123");
    book.setDueDate(java.time.LocalDate.now().minusDays(2)); // 2 days overdue

    double fine = libraryService.returnBook("M1", "123");
    // 2 days * 0.50 = 1.00
    assertEquals(1.00, fine);

    Member member = libraryService.getMember("M1");
    assertEquals(1.00, member.getTotalFines());
    assertTrue(notificationService.messages.stream().anyMatch(m -> m.contains("Fine")));
  }
}
