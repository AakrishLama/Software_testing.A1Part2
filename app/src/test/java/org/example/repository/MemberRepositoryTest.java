package org.example.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberRepositoryTest {

    @Test
    public void testRepositoryCreation() {
        MemberRepository repository = new MemberRepository();
        assertNotNull(repository);
    }
    
}
