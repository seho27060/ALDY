package com.example.demo.domain.entity;

import com.example.demo.repository.CalendarRepository;
import com.example.demo.repository.ProblemTableRepository;
import com.example.demo.repository.StudyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@SpringBootTest
class StudyTest {

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private ProblemTableRepository problemTableRepository;

    private final String name = "test";

    private final int upperLimit = 1;

    private final String introduction = "test dsc";

    private final int threshold = 3;

    private final int visibility = 1;

    private final Calendar calendar = new Calendar();

    private final List<Calendar> calendarList = new ArrayList<>();
    @Test
    public void saveStudy(){
//        calendarList.add(calendar);
//
//        Study study = studyRepository.save(Study.builder()
//                .name(name)
//                .threshold(threshold)
//                .introduction(introduction)
//                .upperLimit(upperLimit)
//                .visibility(visibility)
//                .calendarList(calendarList)
//                .build());
//        System.out.println(">>>>>"+calendarRepository.findAll().get(0).toString());
    }

}