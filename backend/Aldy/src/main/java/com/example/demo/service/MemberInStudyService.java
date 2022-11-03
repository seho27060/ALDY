package com.example.demo.service;

import com.example.demo.domain.dto.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.MemberInStudyChangeAuthDto;
import com.example.demo.domain.dto.MemberInStudyDto;

import java.util.List;

public interface MemberInStudyService {

    void setRoomLeader(Long studyId, String baekjoonId);

    int getAuthByBaekjoonId(String baekjoonId, Long studyId);

    MemberInStudyDto applicateStudy(ApplicateStudyRequestDto requestDto, String baekjoonId);

    List<MemberInStudyDto> getAllMemberInStudy(Long studyId);

    MemberInStudyDto changeAuth(MemberInStudyChangeAuthDto requestDto, String loginMemberBaekjoonId, int auth);

    void rejectMember(MemberInStudyChangeAuthDto requestDto, String loginMemberBaekjoonId);

}
