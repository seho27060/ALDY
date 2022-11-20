package com.example.demo.repository.study;

import com.example.demo.domain.entity.Study.MemberInStudy;
import com.example.demo.domain.entity.Study.Study;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberInStudyRepository extends JpaRepository<MemberInStudy, Long> {

    Optional<MemberInStudy> findByStudy_IdAndMember_BaekjoonId(Long studyId, String baekjoonId);

    Optional<MemberInStudy> findByStudy_IdAndMember_BaekjoonIdAndAuthIn(Long studyId, String baekjoonId, List<Integer> auth);

    List<MemberInStudy> findAllByStudyIdAndAuthIn(Long studyId, List<Integer> auth);

    int countAllByStudyIdAndAuthIn(Long studyId, List<Integer> auth);

    Page<MemberInStudy> findAllByMember_BaekjoonIdAndAuthIn(String baekjoonId, List<Integer> auth,Pageable pageable);

    Optional<MemberInStudy> findByStudy_IdAndMember_Id(Long studyId, Long memberId);

    Optional<MemberInStudy> findByStudyAndAuth(Study study, int auth);

    List<MemberInStudy> findByStudy_Id(Long studyId);
}
