package com.example.demo.service;

import com.example.demo.domain.dto.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.MemberInStudyChangeAuthDto;
import com.example.demo.domain.dto.MemberInStudyDto;

import java.util.List;

public interface MemberInStudyService {

    void setRoomLeader(Long studyId, String backjoonId);

    int getAuthByBackjoonId(String backjoonId, Long studyId);

    MemberInStudyDto applicateStudy(ApplicateStudyRequestDto requestDto, String backjoonId);

    List<MemberInStudyDto> getAllMemberInStudy(Long studyId);

    MemberInStudyDto changeAuth(MemberInStudyChangeAuthDto requestDto, String loginMemberBackjoonId, int auth);

    void rejectMember(MemberInStudyChangeAuthDto requestDto, String loginMemberBackjoonId);

}
