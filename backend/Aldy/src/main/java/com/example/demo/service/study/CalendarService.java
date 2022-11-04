package com.example.demo.service.study;

import com.example.demo.domain.dto.study.CalendarDto;
import com.example.demo.domain.dto.study.ProblemChoiceRequestDto;

// 캘린더 서비스에서 문제 테이블까지 다뤄저야 하나? 일단 연관되어 있기는 해야할 듯.
public interface CalendarService {
    void registerProblem(ProblemChoiceRequestDto problemChoiceRequestDto);

    void deleteProblem(long study_id, int problem_id, String date);

    CalendarDto getCalendar(long study_id, int year, int month);
}
