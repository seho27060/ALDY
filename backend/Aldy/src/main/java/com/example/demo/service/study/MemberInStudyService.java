package com.example.demo.service.study;

import com.example.demo.domain.dto.study.ApplicateStudyRequestDto;
import com.example.demo.domain.dto.study.MemberInStudyChangeAuthDto;
import com.example.demo.domain.dto.study.MemberInStudyDto;

import java.util.List;

public interface MemberInStudyService {

    void setRoomLeader(Long studyId, String baekjoonId);

    int getAuthByBaekjoonId(String baekjoonId, Long studyId);

    MemberInStudyDto applicateStudy(ApplicateStudyRequestDto requestDto, String baekjoonId);

    List<MemberInStudyDto> getAllMemberInStudy(Long studyId);

    List<MemberInStudyDto> getAllApplicateMemberInStudy(Long studyId);

    MemberInStudyDto changeAuth(Long studyId, String loginMemberBaekjoonId, int auth);
    MemberInStudyDto changeAuth(MemberInStudyChangeAuthDto requestDto, String loginMemberBaekjoonId, int auth);

    void rejectMember(MemberInStudyChangeAuthDto requestDto, String loginMemberBaekjoonId);

    void checkLeader(MemberInStudyDto memberInStudyDto);

    void sendMessage(MemberInStudyDto memberInStudyDto);

}
