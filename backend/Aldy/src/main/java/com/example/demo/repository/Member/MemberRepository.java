package com.example.demo.repository.Member;

import com.example.demo.domain.entity.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByBackjoonId(String backjoonId);
}
