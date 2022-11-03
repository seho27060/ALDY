package com.example.demo.service;

import com.example.demo.domain.dto.CalendarDto;
import com.example.demo.domain.dto.ProblemChoiceRequestDto;
import com.example.demo.domain.entity.Calendar;
import com.example.demo.domain.entity.ProblemTable;
import com.example.demo.domain.entity.Study;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.CalendarRepository;
import com.example.demo.repository.ProblemTableRepository;
import com.example.demo.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CalendarServiceImpl implements CalendarService{

    private final CalendarRepository calendarRepository;
    private final ProblemTableRepository problemTableRepository;
    private final StudyRepository studyRepository;
    // 문제를 등록한다. 캘린더가 존재하지 않으면 존재하게 만들어준다.
    @Override
    public void registerProblem(ProblemChoiceRequestDto problemChoiceRequestDto) {
        long study_id = problemChoiceRequestDto.getStudyId();
        // 문제가 어느 스터디에서 선택된 건지 알아냄.
        Study study = studyRepository.findById(study_id).orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        int year = problemChoiceRequestDto.getYear();
        int month = problemChoiceRequestDto.getMonth();;
        int day = problemChoiceRequestDto.getDay();
        // 달력이 있으면 가져오고 달력이 없으면 하나 만들어줌.
        Calendar calendar = calendarRepository.findById(problemChoiceRequestDto.getStudyId()).orElse(
                calendarRepository.save(Calendar.builder()
                        .calendarMonth(month)
                        .calendarYear(year)
                        .study(study)
                        .build())
                );
        // 문제 리스트
        List<Integer> problemList = problemChoiceRequestDto.getProblemList();
        // 문제들을 하나씩 문제테이블에 넣어줌.
        for(int problem : problemList){
            ProblemTable problemTable = ProblemTable.builder()
                    .problemId(problem)
                    .calendar(calendar)
                    .problemDay(day)
                    .build();
            problemTableRepository.save(problemTable);
        }

    }

    @Override
    public void deleteProblem(long study_id, int problem_id, String date) {
        String[] dates = date.split("-");
        int year = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]);
        int day = Integer.parseInt(dates[2]);

        Calendar calendar = calendarRepository.findByStudy_idAndCalendarYearAndCalendarMonth(study_id, year, month).orElseThrow(
                () -> new CustomException(ErrorCode.CALENDAR_NOT_FOUND)
        );
        problemTableRepository.deleteByCalendar_idAndProblemIdAndProblemDay(calendar.getId(), problem_id, day);
    }

    @Override
    public CalendarDto getCalendar(long study_id, int year, int month) {
        Calendar calendar = calendarRepository.findByStudy_idAndCalendarYearAndCalendarMonth(study_id, year, month).orElseThrow(
                () -> new CustomException(ErrorCode.CALENDAR_NOT_FOUND)
        );
        List<ProblemTable> problemTableList = problemTableRepository.findByCalendar_id(calendar.getId());

        String year_str = String.valueOf(year);
        String month_str = String.format("%02d", month);

        List<String> dayList = new ArrayList<>();
        for(ProblemTable problemTable : problemTableList){
            String day_str = String.format("%02d", problemTable.getProblemDay());
            dayList.add(day_str+"-"+month_str+"-"+year_str);
        }
        CalendarDto calendarDto = CalendarDto.builder()
                .days(dayList)
                .build();
        return calendarDto;
    }
}
