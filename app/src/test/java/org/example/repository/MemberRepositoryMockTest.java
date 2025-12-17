package org.example.repository;

import org.example.model.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberRepositoryMockTest {

  @Mock
  private Member member;

  @Test
  void testSaveAndFind_withMockMember() {
    // Arrange
    MemberRepository repository = new MemberRepository();
    String memberId = "M1";

    when(member.getMemberId()).thenReturn(memberId);
    when(member.getName()).thenReturn("Mock Member");

    // Act
    repository.save(member);
    Optional<Member> found = repository.findById(memberId);

    // Assert
    assertTrue(found.isPresent());
    assertEquals(member, found.get());
    assertEquals("Mock Member", found.get().getName());

    verify(member, atLeastOnce()).getMemberId();
  }
}
