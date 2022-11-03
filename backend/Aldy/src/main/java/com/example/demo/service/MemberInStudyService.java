package com.example.demo.service;

import com.example.demo.domain.dto.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.MemberInStudyChangeAuthDto;
import com.example.demo.domain.dto.MemberInStudyDto;

import java.util.List;

public interface MemberInStudyService {

    void setRoomLeader(Long studyId, String baeckjoonId);

    int getAuthByBaeckjoonId(String baeckjoonId, Long studyId);

    MemberInStudyDto applicateStudy(ApplicateStudyRequestDto requestDto, String baeckjoonId);

    List<MemberInStudyDto> getAllMemberInStudy(Long studyId);

    MemberInStudyDto changeAuth(MemberInStudyChangeAuthDto requestDto, String loginMemberBaeckjoonId, int auth);

    void rejectMember(MemberInStudyChangeAuthDto requestDto, String loginMemberBaeckjoonId);

}
