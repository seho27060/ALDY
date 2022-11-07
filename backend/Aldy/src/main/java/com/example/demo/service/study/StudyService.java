package com.example.demo.service.study;

import com.example.demo.domain.dto.study.CreateStudyRequestDto;
import com.example.demo.domain.dto.study.MyStudyPageResponseDto;
import com.example.demo.domain.dto.study.StudyDto;
import com.example.demo.domain.dto.study.StudyPageResponseDto;

public interface StudyService {

    StudyDto createStudy(CreateStudyRequestDto createStudyPostDto);

    StudyPageResponseDto getAllStudyPage(int page, int size, String keyword);

    MyStudyPageResponseDto getMyStudyPage(int page, int size, String baekjoonId);

    StudyDto getById(Long studyId);

    void deleteById(Long studyId);
}
