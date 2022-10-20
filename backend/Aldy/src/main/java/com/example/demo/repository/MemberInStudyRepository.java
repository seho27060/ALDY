package com.example.demo.repository;

import com.example.demo.domain.entity.Member;
import com.example.demo.domain.entity.MemberInStudy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberInStudyRepository extends JpaRepository<MemberInStudy, Long> {

    List<MemberInStudy> findByStudy_Id(Long studyId);

}
