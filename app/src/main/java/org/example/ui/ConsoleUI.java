package org.example.ui;

import org.example.model.Book;
import org.example.model.Member;
import org.example.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
  private final LibraryService libraryService;
  private final Scanner scanner;

  public ConsoleUI(LibraryService libraryService, Scanner scanner) {
    this.libraryService = libraryService;
    this.scanner = scanner;
  }

  public void start() {
    System.out.println("=================================");
    System.out.println("  Library Management System");
    System.out.println("=================================");

    boolean running = true;
    while (running) {
      printMenu();
      String choice = scanner.nextLine().trim();

      switch (choice) {
        case "1":
          addBook();
          break;
        case "2":
          registerMember();
          break;
        case "3":
          borrowBook();
          break;
        case "4":
          returnBook();
          break;
        case "5":
          viewAvailableBooks();
          break;
        case "6":
          viewMemberInfo();
          break;
        case "7":
          running = false;
          System.out.println("Thank you for using the Library Management System!");
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void printMenu() {
    System.out.println("\n--- Main Menu ---");
    System.out.println("1. Add Book");
    System.out.println("2. Register Member");
    System.out.println("3. Borrow Book");
    System.out.println("4. Return Book");
    System.out.println("5. View Available Books");
    System.out.println("6. View Member Info");
    System.out.println("7. Exit");
    System.out.print("Enter your choice: ");
  }

  private void addBook() {
    System.out.print("Enter ISBN: ");
    String isbn = scanner.nextLine().trim();
    System.out.print("Enter Title: ");
    String title = scanner.nextLine().trim();
    System.out.print("Enter Author: ");
    String author = scanner.nextLine().trim();

    libraryService.addBook(isbn, title, author);
    System.out.println("Book added successfully!");
  }

  private void registerMember() {
    System.out.print("Enter Member ID: ");
    String memberId = scanner.nextLine().trim();
    System.out.print("Enter Name: ");
    String name = scanner.nextLine().trim();
    System.out.print("Enter Email: ");
    String email = scanner.nextLine().trim();

    libraryService.registerMember(memberId, name, email);
    System.out.println("Member registered successfully!");
  }

  private void borrowBook() {
    System.out.print("Enter Member ID: ");
    String memberId = scanner.nextLine().trim();
    System.out.print("Enter Book ISBN: ");
    String isbn = scanner.nextLine().trim();

    try {
      boolean success = libraryService.borrowBook(memberId, isbn);
      if (success) {
        System.out.println("Book borrowed successfully!");
      } else {
        System.out.println("Unable to borrow book. Check if book is available and member can borrow.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void returnBook() {
    System.out.print("Enter Member ID: ");
    String memberId = scanner.nextLine().trim();
    System.out.print("Enter Book ISBN: ");
    String isbn = scanner.nextLine().trim();

    try {
      double fine = libraryService.returnBook(memberId, isbn);
      if (fine > 0) {
        System.out.printf("Book returned successfully! Fine charged: $%.2f%n", fine);
      } else {
        System.out.println("Book returned successfully! No fine charged.");
      }
    } catch (IllegalArgumentException | IllegalStateException e) {
      System.out.println("Error: " + e.getMessage());
    }
  }

  private void viewAvailableBooks() {
    List<Book> books = libraryService.getAvailableBooks();

    if (books.isEmpty()) {
      System.out.println("No available books at the moment.");
      return;
    }

    System.out.println("\n=== Available Books ===");
    for (Book book : books) {
      System.out.println(book);
    }
  }

  private void viewMemberInfo() {
    System.out.print("Enter Member ID: ");
    String memberId = scanner.nextLine().trim();

    Member member = libraryService.getMember(memberId);

    if (member == null) {
      System.out.println("Member not found.");
      return;
    }

    System.out.println("\n=== Member Information ===");
    System.out.println(member);

    if (!member.getBorrowedBooks().isEmpty()) {
      System.out.println("\nBorrowed Books:");
      for (String isbn : member.getBorrowedBooks()) {
        Book book = libraryService.getBook(isbn);
        if (book != null) {
          System.out.printf("  - %s (Due: %s)%n", book.getTitle(), book.getDueDate());
        }
      }
    }
  }
}