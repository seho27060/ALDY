package com.example.demo.service;

import com.example.demo.domain.dto.CreateStudyPostDto;
import com.example.demo.domain.dto.StudyDto;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface StudyService {

    StudyDto createStudy(CreateStudyPostDto createStudyPostDto);

    Page<StudyDto> getAllStudyPage(int page, int size, String keyword);

    StudyDto getById(Long studyID);
}
