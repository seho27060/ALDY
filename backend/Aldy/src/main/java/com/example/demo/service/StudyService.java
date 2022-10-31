package com.example.demo.service;

import com.example.demo.domain.dto.CreateStudyRequestDto;
import com.example.demo.domain.dto.StudyDto;
import org.springframework.data.domain.Page;

public interface StudyService {

    StudyDto createStudy(CreateStudyRequestDto createStudyPostDto);

    Page<StudyDto> getAllStudyPage(int page, int size, String keyword);

    Page<StudyDto> getMyStudyPage(int page, int size, String keyword, Long memberId);

    StudyDto getById(Long studyId);

    void deleteById(Long studyId);
}
