package com.example.demo.service;

import com.example.demo.domain.dto.CreateStudyPostDto;
import com.example.demo.domain.dto.StudyDto;

public interface StudyService {

    StudyDto createStudy(CreateStudyPostDto createStudyPostDto);
}
