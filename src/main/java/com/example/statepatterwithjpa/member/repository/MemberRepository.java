package com.example.statepatterwithjpa.member.repository;


import com.example.statepatterwithjpa.member.domain.Member;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);

    Optional<Member> findById(Long id);
}
