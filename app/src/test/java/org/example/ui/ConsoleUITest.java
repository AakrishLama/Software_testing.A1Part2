package org.example.ui;

import org.example.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsoleUITest {

  @Mock
  private LibraryService libraryService;

  // Helper to simulate user input
  private Scanner createScanner(String input) {
    InputStream in = new ByteArrayInputStream(input.getBytes());
    return new Scanner(in);
  }

  @Test
  void testStart_exitImmediately() {
    // Choice 7 is exit
    String input = "7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    ui.start();

    // No interactions expected with service if just exiting
    verifyNoInteractions(libraryService);
  }

  @Test
  void testAddBook() {
    // 1 (Add Book) -> ISBN -> Title -> Author -> 7 (Exit)
    String input = "1\n123\nTitle\nAuthor\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    ui.start();

    verify(libraryService).addBook("123", "Title", "Author");
  }

  @Test
  void testRegisterMember() {
    // 2 (Register) -> ID -> Name -> Email -> 7 (Exit)
    String input = "2\nM1\nJohn\njohn@example.com\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    ui.start();

    verify(libraryService).registerMember("M1", "John", "john@example.com");
  }

  @Test
  void testBorrowBook_success() {
    // 3 (Borrow) -> M1 -> 123 -> 7
    String input = "3\nM1\n123\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    when(libraryService.borrowBook("M1", "123")).thenReturn(true);

    ui.start();

    verify(libraryService).borrowBook("M1", "123");
  }

  @Test
  void testBorrowBook_fail() {
    // 3 -> M1 -> 123 -> 7
    String input = "3\nM1\n123\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    when(libraryService.borrowBook("M1", "123")).thenReturn(false);

    ui.start();

    verify(libraryService).borrowBook("M1", "123");
  }

  @Test
  void testBorrowBook_exception() {
    // 3 -> M1 -> 123 -> 7
    String input = "3\nM1\n123\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    doThrow(new IllegalArgumentException("Error")).when(libraryService).borrowBook("M1", "123");

    ui.start();
    // Verification is just that it doesn't crash
  }

  @Test
  void testReturnBook_success() {
    // 4 (Return) -> M1 -> 123 -> 7
    String input = "4\nM1\n123\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    when(libraryService.returnBook("M1", "123")).thenReturn(0.0);

    ui.start();

    verify(libraryService).returnBook("M1", "123");
  }

  @Test
  void testReturnBook_withFine() {
    // 4 -> M1 -> 123 -> 7
    String input = "4\nM1\n123\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    when(libraryService.returnBook("M1", "123")).thenReturn(5.0);

    ui.start();

    verify(libraryService).returnBook("M1", "123");
  }

  @Test
  void testReturnBook_exception() {
    // 4 -> M1 -> 123 -> 7
    String input = "4\nM1\n123\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    doThrow(new IllegalStateException("Error")).when(libraryService).returnBook("M1", "123");

    ui.start();
  }

  @Test
  void testViewAvailableBooks_empty() {
    // 5 -> 7
    String input = "5\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    when(libraryService.getAvailableBooks()).thenReturn(java.util.Collections.emptyList());

    ui.start();
    verify(libraryService).getAvailableBooks();
  }

  @Test
  void testViewAvailableBooks_list() {
    // 5 -> 7
    String input = "5\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    org.example.model.Book b = new org.example.model.Book("1", "T", "A");
    when(libraryService.getAvailableBooks()).thenReturn(java.util.Collections.singletonList(b));

    ui.start();
    verify(libraryService).getAvailableBooks();
  }

  @Test
  void testViewMemberInfo_notFound() {
    // 6 -> M1 -> 7
    String input = "6\nM1\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    when(libraryService.getMember("M1")).thenReturn(null);

    ui.start();
    verify(libraryService).getMember("M1");
  }

  @Test
  void testViewMemberInfo_found() {
    // 6 -> M1 -> 7
    String input = "6\nM1\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    org.example.model.Member m = new org.example.model.Member("M1", "N", "E");
    m.addBorrowedBook("123");
    when(libraryService.getMember("M1")).thenReturn(m);
    when(libraryService.getBook("123")).thenReturn(new org.example.model.Book("123", "T", "A"));

    ui.start();
    verify(libraryService).getMember("M1");
  }

  @Test
  void testInvalidChoice() {
    // 99 -> 7
    String input = "99\n7\n";
    ConsoleUI ui = new ConsoleUI(libraryService, createScanner(input));

    ui.start();
  }
}
