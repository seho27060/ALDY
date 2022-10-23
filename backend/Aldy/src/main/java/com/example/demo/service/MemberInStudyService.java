package com.example.demo.service;

import com.example.demo.domain.dto.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.MemberInStudyDto;

import java.util.List;

public interface MemberInStudyService {

    MemberInStudyDto applicateStudy(ApplicateStudyRequestDto requestDto);

    List<MemberInStudyDto> getAllMemberInStudy(Long studyId);
}
