package org.example.repository;

import org.example.model.Member;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemberRepository {
    private final Map<String, Member> members = new HashMap<>();

    public void save(Member member) {
        members.put(member.getMemberId(), member);
    }

    public Optional<Member> findById(String memberId) {
        return Optional.ofNullable(members.get(memberId));
    }

    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }
    public void delete(String memberId) {
    }
}