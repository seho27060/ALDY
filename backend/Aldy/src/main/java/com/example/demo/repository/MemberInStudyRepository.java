package com.example.demo.repository;

import com.example.demo.domain.entity.MemberInStudy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberInStudyRepository extends JpaRepository<MemberInStudy, Long> {
}
