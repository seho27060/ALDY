package com.example.demo.service.study;

import com.example.demo.domain.dto.study.CalendarDto;
import com.example.demo.domain.dto.study.ProblemChoiceRequestDto;
import com.example.demo.domain.dto.study.ProblemDto;

import java.util.List;

// 캘린더 서비스에서 문제 테이블까지 다뤄저야 하나? 일단 연관되어 있기는 해야할 듯.
public interface CalendarService {
    List<ProblemDto> registerProblem(ProblemChoiceRequestDto problemChoiceRequestDto);

    void deleteProblem(long study_id, int problem_id, String date);
    void deleteProblem(long problem_id);

    CalendarDto getCalendar(long study_id, int year, int month);
}
