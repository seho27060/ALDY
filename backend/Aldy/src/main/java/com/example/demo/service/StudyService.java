package com.example.demo.service;

import com.example.demo.domain.dto.CreateStudyRequestDto;
import com.example.demo.domain.dto.StudyDto;
import com.example.demo.domain.dto.StudyPageResponseDto;
import org.springframework.data.domain.Page;

public interface StudyService {

    StudyDto createStudy(CreateStudyRequestDto createStudyPostDto);

    StudyPageResponseDto getAllStudyPage(int page, int size, String keyword);

    StudyPageResponseDto getMyStudyPage(int page, int size, String baekjoonId);

    StudyDto getById(Long studyId);

    void deleteById(Long studyId);
}
