package com.example.demo.service.study;

import com.example.demo.domain.dto.study.*;

public interface StudyService {

    StudyDto createStudy(CreateStudyRequestDto createStudyPostDto);

    StudyPageResponseDto getAllStudyPage(int page, int size, String keyword);

    MyStudyPageResponseDto getMyStudyPage(int page, int size, String baekjoonId);

    StudyDetailResponseDto getById(Long studyId, String loginMember);

    void deleteById(Long studyId);

    StudyInfoListDto getStudyInfoList(Long studyId);
}
