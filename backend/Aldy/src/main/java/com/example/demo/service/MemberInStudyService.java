package com.example.demo.service;

import com.example.demo.domain.dto.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.MemberInStudyDto;

import java.util.List;

public interface MemberInStudyService {

    MemberInStudyDto setRoomLeader(Long studyId, String backjoonId);

    int getAuthByBackjoonId(String backjoonId, Long studyId);

    MemberInStudyDto applicateStudy(ApplicateStudyRequestDto requestDto);

    List<MemberInStudyDto> getAllMemberInStudy(Long studyId);

    MemberInStudyDto changeAuth(ApplicateStudyRequestDto requestDto, Long loginMemberId, int auth);

}
