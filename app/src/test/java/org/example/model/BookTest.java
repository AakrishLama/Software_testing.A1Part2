package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class BookTest {
    private Book book;
    // final static makes it a constant so can't be reinitialized
    private static final String ISBN = "978-3-16-138410-0";
    private static final String TITLE = "Blood Meridian";
    private static final String AUTHOR = "Cormac McCarthy";
    private static final boolean AVAILABLE = true;
    private static final String BORROWEDBY = "John Doe";

    @BeforeEach
    public void setUp() {
        book = new Book(ISBN, TITLE, AUTHOR);
    }

}
