package org.example.repository;

import org.example.model.Member;
import org.example.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MemberRepositoryTest {
    private MemberRepository repository;
    private Member member1;
    private Member member2;

    @BeforeEach
    public void setUp() {
        repository = new MemberRepository();
        member1 = new Member("M001", "John Doe", "john@example.com");
        member2 = new Member("M002", "Jane Smith", "jane@example.com");
    }

    @Test
    public void testMemberRepositoryCreation() {
        assertNotNull(repository);
    }

    @Test
    public void testSaveAndFindMember() {
        // When
        repository.save(member1);
        Member found = repository.findById("M001").orElse(null);

        // Then
        assertNotNull(found);
        assertEquals("M001", found.getMemberId());
        assertEquals("John Doe", found.getName());
        assertEquals("john@example.com", found.getEmail());
    }

    @Test
    public void testFindByIdReturnsNullForNonExistentMember() {
        // When
        Member found = repository.findById("NONEXISTENT").orElse(null);

        // Then
        assertNull(found);
    }

    @Test
    public void testFindAllEmptyRepository() {
        // When
        List<Member> allMembers = repository.findAll();

        // Then
        assertNotNull(allMembers);
        assertTrue(allMembers.isEmpty());
    }

    @Test
    public void testFindAllWithMembers() {
        // Given
        repository.save(member1);
        repository.save(member2);

        // When
        List<Member> allMembers = repository.findAll();

        // Then
        assertEquals(2, allMembers.size());
    }

    @Test
    public void testDeleteRemovesMember() {
        // Given
        repository.save(member1);

        // When
        repository.delete("M001");

        // Then
        assertNull(repository.findById("M001").orElse(null));
    }
}