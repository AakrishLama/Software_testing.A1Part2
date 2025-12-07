package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MemberTest{

    @Test
    public void testMemberCreation(){
        // hard coded
        String memberId = "M001";
        String name = "John Doe";
        String email = "john@example.com";

        Member member = new Member(memberId, name, email);

        assertEquals(memberId, member.getMemberId());
        assertEquals(name, member.getName());
        assertEquals(email, member.getEmail());
    }

    @Test
    public void testInitialBorrowedBookIsEmpty(){
        Member member = new Member("M001", "John Doe", "john@example.com");

        // check if the member has no borrowed books
        assertNotNull(member.getBorrowedBooks());
        assertTrue(member.getBorrowedBooks().isEmpty());
    }
}  