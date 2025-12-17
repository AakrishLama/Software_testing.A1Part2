package org.example;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class AppTest {
  @Test
  void testMain() {
    // Save original System.in
    InputStream originalIn = System.in;

    try {
      // Provide "7" (Exit) as input to avoid infinite loop
      String input = "7\n";
      ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
      System.setIn(in);

      // Run main
      App.main(new String[] {});
    } finally {
      // Restore System.in
      System.setIn(originalIn);
    }
  }

  @Test
  void testAppConstructor() {
    new App();
  }
}
