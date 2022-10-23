package com.example.demo.service;

import com.example.demo.domain.dto.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.MemberInStudyDto;
import com.example.demo.domain.entity.Member;
import com.example.demo.domain.entity.MemberInStudy;
import com.example.demo.domain.entity.Study;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.MemberInStudyRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberInStudyServiceImpl implements MemberInStudyService {

    private  final MemberInStudyRepository memberInStudyRepository;

    private final MemberRepository memberRepository;

    private final StudyRepository studyRepository;

    @Override
    public MemberInStudyDto applicateStudy(ApplicateStudyRequestDto requestDto) {

        Member member = memberRepository.findByBackjoonId(requestDto.getBackjoonId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Study study = studyRepository.findById(requestDto.getStudyId())
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        MemberInStudy memberInStudy = new MemberInStudy(study, member, 3, requestDto.getMessage());

        MemberInStudyDto memberInStudyDto = new MemberInStudyDto(memberInStudyRepository.save(memberInStudy));

        return memberInStudyDto;

    }

    @Override
    public List<MemberInStudyDto> getAllMemberInStudy(Long studyId) {

        List<MemberInStudy> memberInStudyList = memberInStudyRepository.findByStudy_Id(studyId);

        List<MemberInStudyDto> memberInStudyDtoList = memberInStudyList.stream().map(e ->
                new MemberInStudyDto(e)).collect(Collectors.toList());

        return memberInStudyDtoList;

    }

}
