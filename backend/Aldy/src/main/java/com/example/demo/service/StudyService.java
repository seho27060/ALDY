package com.example.demo.service;

import com.example.demo.domain.dto.CreateStudyRequestDto;
import com.example.demo.domain.dto.StudyDto;
import org.springframework.data.domain.Page;

public interface StudyService {

    Long createStudy(CreateStudyRequestDto createStudyPostDto);

    Page<StudyDto> getAllStudyPage(int page, String keyword);

    Page<StudyDto> getMyStudyPage(int page, String backjoonId);

    StudyDto getById(Long studyId);

    void deleteById(Long studyId);
}
