package com.example.demo.service;

import com.example.demo.domain.dto.CreateStudyPostDto;
import com.example.demo.domain.dto.StudyDto;
import com.example.demo.domain.entity.Study;
import com.example.demo.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;

    @Override
    public StudyDto createStudy(CreateStudyPostDto requestDto) {

        Study study = studyRepository.save(new Study(requestDto));
        System.out.println(study.getCreatedDate());
        StudyDto studyDto = new StudyDto(study);

        return studyDto;
    }




}
