package org.example.service;

import org.example.model.Book;
import org.example.model.Member;
import org.example.repository.BookRepository;
import org.example.repository.MemberRepository;

import java.time.LocalDate;
import java.util.List;

public class LibraryService {
  private final BookRepository bookRepository;
  private final MemberRepository memberRepository;
  private final NotificationService notificationService;
  private final FineCalculator fineCalculator;

  public LibraryService(BookRepository bookRepository,
      MemberRepository memberRepository,
      NotificationService notificationService,
      FineCalculator fineCalculator) {
    this.bookRepository = bookRepository;
    this.memberRepository = memberRepository;
    this.notificationService = notificationService;
    this.fineCalculator = fineCalculator;
  }

  public void addBook(String isbn, String title, String author) {
    Book book = new Book(isbn, title, author);
    bookRepository.save(book);
  }

  public void registerMember(String memberId, String name, String email) {
    Member member = new Member(memberId, name, email);
    memberRepository.save(member);
  }

  public boolean borrowBook(String memberId, String isbn) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

    Book book = bookRepository.findByIsbn(isbn)
        .orElseThrow(() -> new IllegalArgumentException("Book not found: " + isbn));

    if (!book.isAvailable()) {
      return false;
    }

    if (!member.canBorrow()) {
      return false;
    }

    book.setAvailable(false);
    book.setBorrowedBy(memberId);
    book.setDueDate(LocalDate.now().plusWeeks(2));

    member.addBorrowedBook(isbn);

    bookRepository.save(book);
    memberRepository.save(member);

    notificationService.sendBorrowConfirmation(member, book.getTitle());

    return true;
  }

  public double returnBook(String memberId, String isbn) {
    Member member = memberRepository.findById(memberId)
        .orElseThrow(() -> new IllegalArgumentException("Member not found: " + memberId));

    Book book = bookRepository.findByIsbn(isbn)
        .orElseThrow(() -> new IllegalArgumentException("Book not found: " + isbn));

    if (book.isAvailable() || !memberId.equals(book.getBorrowedBy())) {
      throw new IllegalStateException("Book is not borrowed by this member");
    }

    double fine = fineCalculator.calculateCurrentFine(book.getDueDate());

    book.setAvailable(true);
    book.setBorrowedBy(null);
    book.setDueDate(null);

    member.removeBorrowedBook(isbn);

    if (fine > 0) {
      member.addFine(fine);
      notificationService.sendFineNotification(member, fine);
    }

    bookRepository.save(book);
    memberRepository.save(member);

    notificationService.sendReturnConfirmation(member, book.getTitle());

    return fine;
  }

  public List<Book> getAvailableBooks() {
    return bookRepository.findAvailableBooks();
  }

  public Member getMember(String memberId) {
    return memberRepository.findById(memberId).orElse(null);
  }

  public Book getBook(String isbn) {
    return bookRepository.findByIsbn(isbn).orElse(null);
  }
}