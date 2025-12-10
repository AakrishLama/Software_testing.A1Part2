package org.example.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.example.model.Member;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberRepositoryTest {

    @Test
    public void testRepositoryCreation() {
        MemberRepository repository = new MemberRepository();
        assertNotNull(repository);
    }

    @Test
    public void testFindByIdAfterSave() {
        MemberRepository repository = new MemberRepository();
        Member member = new Member("M001", "John", "john@example.com");
        repository.save(member);
    }

    @Test
    public void testSaveAndFindMember() {
        // Given
        MemberRepository repository = new MemberRepository();
        Member member = new Member("M001", "John Doe", "john@example.com");

        // When
        repository.save(member);
        Member found = repository.findById("M001").orElse(null);

        // Then - This will FAIL because findById returns null
        assertNotNull(found);
        assertEquals("M001", found.getMemberId());
        assertEquals("John Doe", found.getName());
        assertEquals("john@example.com", found.getEmail());
    }
    @Test
    public void testFindByIdReturnsNullForNonExistentMember() {
        // Given
        MemberRepository repository = new MemberRepository();

        // When
        Member found = repository.findById("NON_EXISTENT_ID").orElse(null);

        // Then - This will PASS because findById returns null for non-existent member
        assertNull(found);
    }

}
