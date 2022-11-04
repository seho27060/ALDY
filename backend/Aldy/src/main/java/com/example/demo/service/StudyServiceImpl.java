package com.example.demo.service;

import com.example.demo.domain.dto.CreateStudyRequestDto;
import com.example.demo.domain.dto.StudyDto;

import com.example.demo.domain.dto.StudyPageResponseDto;
import com.example.demo.domain.entity.MemberInStudy;
import com.example.demo.domain.entity.Study;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;

import com.example.demo.repository.Member.MemberRepository;
import com.example.demo.repository.MemberInStudyRepository;
import com.example.demo.repository.StudyRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;

    private final MemberRepository memberRepository;

    private final MemberInStudyRepository memberInStudyRepository;

    private final List<Integer> authList = List.of(1, 2);

    @Override
    public StudyDto createStudy(CreateStudyRequestDto requestDto) {

        Study study = studyRepository.save(new Study(requestDto));

        return new StudyDto(study, countMember(study.getId()));

    }

    @Override
    public StudyPageResponseDto getAllStudyPage(int page, int size, String keyword) {

        Page<Study> studyPage = studyRepository.findAllByNameContaining(keyword,
                PageRequest.of(page, size).withSort(Sort.by("id").descending()));

        if(studyPage.isEmpty()) {
            throw new CustomException(ErrorCode.STUDY_NOT_FOUND);
        }

        Page<StudyDto> studyDtoPage =  studyPage.map(e ->
                new StudyDto(e, countMember(e.getId()))
        );

        return new StudyPageResponseDto(studyDtoPage.getTotalPages(), studyDtoPage);

    }

    @Override
    public StudyPageResponseDto getMyStudyPage(int page, int size, String baekjoonId) {

        Page<MemberInStudy> memberInStudyPage = memberInStudyRepository.findAllByMember_BaekjoonId(baekjoonId,
                PageRequest.of(page, size).withSort(Sort.by("id").descending()));

        if(memberInStudyPage.isEmpty()) {
            throw new CustomException(ErrorCode.STUDY_NOT_FOUND);
        }

        Page<StudyDto> studyDtoPage = memberInStudyPage.map(e ->
                new StudyDto(e.getStudy(), countMember(e.getStudy().getId()))
        );

        return new StudyPageResponseDto(studyDtoPage.getTotalPages(), studyDtoPage);

    }

    @Override
    public StudyDto getById(Long studyId) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        return new StudyDto(study, countMember(study.getId()));

    }

    @Override
    public void deleteById(Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(()-> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        studyRepository.delete(study);
    }


    public int countMember(Long studyId) {

        return memberInStudyRepository.countAllByStudyIdAndAuthIn(studyId, authList);

    }

}
