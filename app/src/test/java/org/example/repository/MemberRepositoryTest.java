package org.example.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

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
    @Test
    public void testFindAllEmptyRepository() {
        // Given
        MemberRepository repository = new MemberRepository();
        // When
        List<Member> allMembers = repository.findAll();
        // Then
        assertTrue(allMembers.isEmpty());
    }
    @Test
    public void testFindAllWithMembers() {
        // Given
        MemberRepository repository = new MemberRepository();
        Member member1 = new Member("M001", "John", "john@test.com");
        Member member2 = new Member("M002", "Jane", "jane@test.com");
        repository.save(member1);
        repository.save(member2);
        
        // When
        List<Member> allMembers = repository.findAll();
        
        assertEquals(2, allMembers.size());
    }
    @Test
    public void testDeleteMember() {
        // Given
        MemberRepository repository = new MemberRepository();
        Member member = new Member("M001", "John", "john@test.com");
        repository.save(member);
        

        repository.delete("M001");
        
        assertNull(repository.findById("M001"));
    }
}
