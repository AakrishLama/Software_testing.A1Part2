package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Member {
    private final String memberId;
    private final String name;
    private final String email;
    private final List<String> borrowedBooks;
    private double totalFines;

    public Member(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
        this.totalFines = 0.0;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getBorrowedBooks() {
        return new ArrayList<>(borrowedBooks);
    }

    public void addBorrowedBook(String isbn) {
        borrowedBooks.add(isbn);
    }

    public void removeBorrowedBook(String isbn) {
        borrowedBooks.remove(isbn);
    }

    public void addFines(double amount) {
        totalFines += amount;
    }

    public void removeFines(double amount) {
        totalFines -= amount;
    }

    public double getTotalFines() {
        return totalFines;
    }

}