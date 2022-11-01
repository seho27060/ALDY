package com.example.demo.repository;

import com.example.demo.domain.entity.MemberInStudy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberInStudyRepository extends JpaRepository<MemberInStudy, Long> {

    Optional<MemberInStudy> findByStudy_IdAndMember_BackjoonId(Long studyId, String backjoonId);

    List<MemberInStudy> findAllByStudyIdAndAuthIn(Long studyId, List<Integer> auth);

    int countAllByStudyIdAndAuthIn(Long studyId, List<Integer> auth);

    Page<MemberInStudy> findAllByMember_BackjoonId(String backjoonId, Pageable pageable);

    Optional<MemberInStudy> findByStudy_IdAndMember_Id(Long studyId, Long memberId);

}
