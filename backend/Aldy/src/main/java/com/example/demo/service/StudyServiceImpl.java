package com.example.demo.service;

import com.example.demo.domain.dto.CreateStudyPostDto;
import com.example.demo.domain.dto.StudyDto;
import com.example.demo.domain.entity.Member;
import com.example.demo.domain.entity.MemberInStudy;
import com.example.demo.domain.entity.Study;
import com.example.demo.repository.MemberInStudyRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;

    private final MemberRepository memberRepository;

    private final MemberInStudyRepository memberInStudyRepository;

    @Override
    public StudyDto createStudy(CreateStudyPostDto requestDto) {

        Study study = studyRepository.save(new Study(requestDto));
        StudyDto studyDto = new StudyDto(study, countMember(study.getId()));

        ///////////////////////////////////////////////////////////////

        // Member가 null인데 에러가 안뜬다!?
        Member member = memberRepository.findByBackjoonId(requestDto.getLeaderBackjoonId());
        MemberInStudy memberInStudy = new MemberInStudy(study, member, 1);

        memberInStudyRepository.save(memberInStudy);

        ///////////////////////////////////////////////////////////////

        return studyDto;
    }

    @Override
    public Page<StudyDto> getAllStudyPage(int page, int size, String keyword) {

        Page<Study> studyPage = studyRepository.findAllByNameContaining(keyword,
                PageRequest.of(page, size).withSort(Sort.by("id").descending()));

        Page<StudyDto> studyDtoPage = studyPage.map(e ->
                new StudyDto(e, countMember(e.getId())));

        return studyDtoPage;
    }

    @Override
    public StudyDto getById(Long studyID) {

        Optional<Study> study = studyRepository.findById(studyID);
        StudyDto studyDto = new StudyDto(study.get(), countMember(study.get().getId()));

        return studyDto;

    }


    private int countMember(Long studyId) {

        List<MemberInStudy> memberInStudyList = memberInStudyRepository.findByStudy_Id(studyId);

        return memberInStudyList.size();

    }


}
