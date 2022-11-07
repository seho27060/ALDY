package com.example.demo.service.study;

import com.example.demo.domain.dto.study.*;

import com.example.demo.domain.entity.Study.Calendar;
import com.example.demo.domain.entity.Study.MemberInStudy;
import com.example.demo.domain.entity.Study.Problem;
import com.example.demo.domain.entity.Study.Study;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;

import com.example.demo.repository.study.CalendarRepository;
import com.example.demo.repository.study.MemberInStudyRepository;
import com.example.demo.repository.study.ProblemRepository;
import com.example.demo.repository.study.StudyRepository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class StudyServiceImpl implements StudyService {

    private final StudyRepository studyRepository;

    private final CalendarRepository calendarRepository;

    private final ProblemRepository problemRepository;

    private final MemberInStudyRepository memberInStudyRepository;

    private final List<Integer> authList = List.of(1, 2);

    @Override
    public StudyDto createStudy(CreateStudyRequestDto requestDto) {

        Study study = studyRepository.save(new Study(requestDto));

        return new StudyDto(study, countMember(study.getId()));

    }

    @Override
    public StudyPageResponseDto getAllStudyPage(int page, int size, String keyword) {

        Page<Study> studyPage = studyRepository.findAllByNameContaining(keyword,
                PageRequest.of(page, size).withSort(Sort.by("id").descending()));

        if(studyPage.isEmpty()) {
            throw new CustomException(ErrorCode.STUDY_NOT_FOUND);
        }

        Page<StudyDto> studyDtoPage =  studyPage.map(e ->
                new StudyDto(e, countMember(e.getId()))
        );

        return new StudyPageResponseDto(studyDtoPage.getTotalPages(), studyDtoPage.getTotalElements(), studyDtoPage);

    }

    @Override
    public MyStudyPageResponseDto getMyStudyPage(int page, int size, String baekjoonId) {

        Page<MemberInStudy> memberInStudyPage = memberInStudyRepository.findAllByMember_BaekjoonId(baekjoonId,
                PageRequest.of(page, size).withSort(Sort.by("id").descending()));

        if(memberInStudyPage.isEmpty()) {
            throw new CustomException(ErrorCode.STUDY_NOT_FOUND);
        }

        Page<MyStudyDto> myStudyDtoPage = memberInStudyPage.map(e -> {

            ProblemInfo problemInfo = getProblemInfo(e.getStudy().getId());
            return new MyStudyDto(e.getStudy(), countMember(e.getStudy().getId()), problemInfo.getCount(), problemInfo.getTier());

        });

        return new MyStudyPageResponseDto(myStudyDtoPage.getTotalPages(), myStudyDtoPage.getTotalElements(), myStudyDtoPage);

    }

    @Override
    public StudyDetailResponseDto getById(Long studyId) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        StudyDetailResponseDto studyDetailResponseDto = new StudyDetailResponseDto(study);

        studyDetailResponseDto.setCountMember(countMember(study.getId()));

        studyDetailResponseDto.setLeaderBaekjoonId(
                memberInStudyRepository.findByStudyAndAuth(study, 1)
                        .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND))
                        .getMember().getBaekjoonId()
        );

        studyDetailResponseDto.setStatsByTier(statsByTier(study));

        return studyDetailResponseDto;

    }

    @Override
    public void deleteById(Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(()-> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        studyRepository.delete(study);
    }


    public int countMember(Long studyId) {

        return memberInStudyRepository.countAllByStudyIdAndAuthIn(studyId, authList);

    }

    public ProblemInfo getProblemInfo(long studyId) {

        List<Calendar> calendarList = calendarRepository.findByStudy_id(studyId);

        LocalDate now = LocalDate.now();
        LocalDate max = LocalDate.of(1, 1, 1);
        int count = 0;
        int tier = 0;

        for (Calendar calendar : calendarList) {
            List<Problem> problemList = problemRepository.findByCalendar_id(calendar.getId());

            for (Problem problem : problemList) {
                LocalDate problemDate = LocalDate.of(calendar.getCalendarYear(), calendar.getCalendarMonth(), problem.getProblemDay());

                if (problemDate.compareTo(now) < 0) {
                    count += 1;

                    if(problemDate.compareTo(max) > 0) {
                        max = problemDate;
                        tier = problem.getProblemTier();
                    }
                }
            }
        }

        return new ProblemInfo(count, tier);
    }

    public HashMap<Integer, Integer> statsByTier(Study study) {

        HashMap<Integer, Integer> stats = new HashMap<>();
        List<Calendar> calendarList = calendarRepository.findByStudy_id(study.getId());

        for(Calendar calendar : calendarList) {
            List<Problem> problemList = problemRepository.findByCalendar_id(calendar.getId());
            for(Problem problem : problemList) {
                int count = 1;
                if(stats.containsKey(problem.getProblemTier())) {
                    count = stats.get(problem.getProblemTier()) + 1;
                }
                stats.put(problem.getProblemTier(), count);
            }
        }

        return stats;
    }

}

@Getter
@AllArgsConstructor
class ProblemInfo {
    private int count;
    private int tier;
}