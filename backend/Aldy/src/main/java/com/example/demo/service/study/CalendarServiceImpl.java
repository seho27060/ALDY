package com.example.demo.service.study;

import com.example.demo.domain.dto.solvedac.ProblemTagsDto;
import com.example.demo.domain.dto.study.CalendarDto;
import com.example.demo.domain.dto.study.ProblemChoiceRequestDto;
import com.example.demo.domain.dto.solvedac.ProblemWithTagsVo;
import com.example.demo.domain.dto.study.ProblemDto;
import com.example.demo.domain.entity.Study.Calendar;
import com.example.demo.domain.entity.Study.Problem;
import com.example.demo.domain.entity.Study.Study;
import com.example.demo.domain.entity.Study.TagOfProblem;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.study.CalendarRepository;
import com.example.demo.repository.study.ProblemRepository;
import com.example.demo.repository.study.StudyRepository;
import com.example.demo.repository.study.TagOfProblemRepository;
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
    private final ProblemRepository problemRepository;
    private final StudyRepository studyRepository;
    private final TagOfProblemRepository tagOfProblemRepository;

    // 문제를 등록한다. 캘린더가 존재하지 않으면 존재하게 만들어준다.
    @Override
    public List<ProblemDto> registerProblem(ProblemChoiceRequestDto problemChoiceRequestDto) {
        long study_id = problemChoiceRequestDto.getStudyId();
        // 문제가 어느 스터디에서 선택된 건지 알아냄.
        Study study = studyRepository.findById(study_id).orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        int year = problemChoiceRequestDto.getYear();
        int month = problemChoiceRequestDto.getMonth();
        int day = problemChoiceRequestDto.getDay();
        // 달력이 있으면 가져오고 달력이 없으면 하나 만들어줌.
        Calendar calendar = calendarRepository.findByStudy_idAndCalendarYearAndCalendarMonth(problemChoiceRequestDto.getStudyId(),
                year, month).orElseGet(
                () -> calendarRepository.save(Calendar.builder()
                        .calendarMonth(month)
                        .calendarYear(year)
                        .study(study)
                        .build())
                );


        List<ProblemDto> problemDtoList = new ArrayList<>();
        // 문제 리스트
        List<ProblemWithTagsVo> problemList = problemChoiceRequestDto.getProblemList();
        // 문제들을 하나씩 문제테이블에 넣어줌.
        for(ProblemWithTagsVo problem : problemList){
            if(problemRepository.existsByCalendarIdAndProblemDayAndProblemName(calendar.getId(), day, problem.getTitleKo())) {
                continue;
            }

            Problem problemTable = Problem.builder()
                    .problemNum(problem.getProblemId())
                    .problemTier(problem.getLevel())
                    .problemName(problem.getTitleKo())
                    .calendar(calendar)
                    .problemDay(day)
                    .build();

            List<ProblemTagsDto> problemTagsDtoList = problem.getTags();
            for(ProblemTagsDto problemTagsDto : problemTagsDtoList) {
                tagOfProblemRepository.save(
                        new TagOfProblem(problemTagsDto.getKey(), problemTable)
                );
            }

            problemDtoList.add(new ProblemDto(problemRepository.save(problemTable)));
        }

        return problemDtoList;
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
//        problemRepository.deleteByCalendar_idAndProblemIdAndProblemDay(calendar.getId(), problem_id, day);
    }

    @Override
    public void deleteProblem(long problem_id) {
        problemRepository.deleteById(problem_id);
    }

    @Override
    public CalendarDto getCalendar(long study_id, int year, int month) {
        Calendar calendar = calendarRepository.findByStudy_idAndCalendarYearAndCalendarMonth(study_id, year, month).orElseThrow(
                () -> new CustomException(ErrorCode.CALENDAR_NOT_FOUND)
        );
        List<Problem> problemList = problemRepository.findByCalendar_id(calendar.getId());

        String year_str = String.valueOf(year);
        String month_str = String.format("%02d", month);

        List<String> dayList = new ArrayList<>();
        for(Problem problem : problemList){
            String day_str = String.format("%02d", problem.getProblemDay());
            dayList.add(day_str+"-"+month_str+"-"+year_str);
        }

        return CalendarDto.builder()
                .days(dayList)
                .build();
    }
}
