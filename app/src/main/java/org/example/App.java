package org.example;

import org.example.repository.BookRepository;
import org.example.repository.MemberRepository;
import org.example.service.FineCalculator;
import org.example.service.LibraryService;
import org.example.service.NotificationService;
import org.example.ui.ConsoleUI;

import java.util.Scanner;

public class App {
  public static void main(String[] args) {
    // Create repositories (in-memory storage)
    BookRepository bookRepository = new BookRepository();
    MemberRepository memberRepository = new MemberRepository();

    // Create services
    FineCalculator fineCalculator = new FineCalculator();
    NotificationService notificationService = new NotificationService();

    // Create library service with dependency injection
    LibraryService libraryService = new LibraryService(
        bookRepository,
        memberRepository,
        notificationService,
        fineCalculator);

    // Add some sample data
    initializeSampleData(libraryService);

    // Create and start console UI
    Scanner scanner = new Scanner(System.in);
    ConsoleUI consoleUI = new ConsoleUI(libraryService, scanner);

    consoleUI.start();

    scanner.close();
  }

  private static void initializeSampleData(LibraryService libraryService) {
    // Add sample books
    libraryService.addBook("978-0132350884", "Clean Code", "Robert C. Martin");
    libraryService.addBook("978-0201633610", "Design Patterns", "Gang of Four");
    libraryService.addBook("978-0134685991", "Effective Java", "Joshua Bloch");
    libraryService.addBook("978-0596007126", "Head First Design Patterns", "Eric Freeman");
    libraryService.addBook("978-0321125215", "Domain-Driven Design", "Eric Evans");

    // Add sample members
    libraryService.registerMember("M001", "Alice Johnson", "alice@example.com");
    libraryService.registerMember("M002", "Bob Smith", "bob@example.com");
    libraryService.registerMember("M003", "Carol White", "carol@example.com");

    System.out.println("Sample data initialized: 5 books and 3 members added.\n");
  }
}