package com.example.demo.repository.member;

import com.example.demo.domain.entity.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByBaekjoonId(String baekjoonId);
    Boolean existsByBaekjoonId(String baekjoonId);
    Boolean existsByNickname(String nickname);
    Boolean existsByEmail(String Email);

}
