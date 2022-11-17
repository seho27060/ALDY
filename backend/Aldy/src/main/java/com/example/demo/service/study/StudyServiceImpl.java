package com.example.demo.service.study;

import com.example.demo.domain.dto.study.*;

import com.example.demo.domain.entity.Study.*;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;

import com.example.demo.repository.study.*;

import com.example.demo.service.solvedac.AlgoEnum;
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

    private final TagOfProblemRepository tagOfProblemRepository;

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

        Page<MemberInStudy> memberInStudyPage = memberInStudyRepository.findAllByMember_BaekjoonIdAndAuthIn(baekjoonId, authList,
                PageRequest.of(page, size).withSort(Sort.by("id").descending()));

        if(memberInStudyPage.isEmpty()) {
            throw new CustomException(ErrorCode.STUDY_NOT_FOUND);
        }

        Page<MyStudyDto> myStudyDtoPage = memberInStudyPage.map(e -> {

            MyStudyDto myStudyDto = new MyStudyDto(e.getStudy(), countMember(e.getStudy().getId()));

            return getProblemInfo(e.getStudy(), myStudyDto);

        });

        return new MyStudyPageResponseDto(myStudyDtoPage.getTotalPages(), myStudyDtoPage.getTotalElements(), myStudyDtoPage);

    }

    @Override
    public StudyDetailResponseDto getById(Long studyId, String loginMember) {

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        StudyDetailResponseDto studyDetailResponseDto = new StudyDetailResponseDto(study);

        memberInStudyRepository.findByStudy_IdAndMember_BaekjoonIdAndAuthIn(study.getId(), loginMember, authList)
                        .ifPresentOrElse(
                                m -> studyDetailResponseDto.setIsMember(true),
                                () -> {
                                    memberInStudyRepository.findByStudy_IdAndMember_BaekjoonIdAndAuthIn(study.getId(), loginMember, List.of(0))
                                                    .ifPresentOrElse(
                                                            k -> studyDetailResponseDto.setIsKick(true),
                                                            () -> studyDetailResponseDto.setIsKick(false)
                                                    );
                                    studyDetailResponseDto.setIsMember(false);
                                }
                        );

        studyDetailResponseDto.setCountMember(countMember(study.getId()));

        studyDetailResponseDto.setLeaderInfo(memberInStudyRepository.findByStudyAndAuth(study, 1)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND))
                .getMember());

        return getDetailInfo(study, studyDetailResponseDto);

    }

    @Override
    public void deleteById(Long studyId) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(()-> new CustomException(ErrorCode.STUDY_NOT_FOUND));

        studyRepository.delete(study);
    }

    @Override
    public StudyInfoListDto getStudyInfoList(Long studyId) {

        List<MemberInStudy> memberInStudyList = memberInStudyRepository.findAllByStudyIdAndAuthIn(studyId, authList);

        HashMap<String, String> memberHash = new HashMap<>();
        for(MemberInStudy memberInStudy : memberInStudyList) {
            memberHash.put(memberInStudy.getMember().getBaekjoonId(), memberInStudy.getMember().getNickname());
        }

        HashMap<AlgoEnum, String> algoHash = new HashMap<>();
        for(AlgoEnum algoEnum : AlgoEnum.values()) {
            algoHash.put(algoEnum, algoEnum.getKo());
        }

        return new StudyInfoListDto(memberHash, algoHash);

    }


    public int countMember(Long studyId) {

        return memberInStudyRepository.countAllByStudyIdAndAuthIn(studyId, authList);

    }

    public MyStudyDto getProblemInfo(Study study, MyStudyDto myStudyDto) {

        List<Calendar> calendarList = calendarRepository.findByStudy_id(study.getId());

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

        myStudyDto.setNumberOfSolvedProblem(count);
        myStudyDto.setTierOfRecentSolvedProblem(tier);

        return myStudyDto;
    }

    public StudyDetailResponseDto getDetailInfo(Study study, StudyDetailResponseDto studyDetailResponseDto) {

        HashMap<Integer, Integer> statsByTier = new HashMap<>();
        HashMap<String, Integer> statsByTag = new HashMap<>();

        List<Calendar> calendarList = calendarRepository.findByStudy_id(study.getId());

        for(Calendar calendar : calendarList) {
            List<Problem> problemList = problemRepository.findByCalendar_id(calendar.getId());

            for(Problem problem : problemList) {
                int countTier = 1;
                if(statsByTier.containsKey(problem.getProblemTier())) {
                    countTier = statsByTier.get(problem.getProblemTier()) + 1;
                }
                statsByTier.put(problem.getProblemTier(), countTier);

                List<TagOfProblem> tagOfProblemList = tagOfProblemRepository.findByProblemId(problem.getId());

                for(TagOfProblem tagOfProblem : tagOfProblemList) {
                    int countTag = 1;
                    if(statsByTag.containsKey(tagOfProblem.getProblemTag())) {
                        countTag = statsByTag.get(tagOfProblem.getProblemTag()) + 1;
                    }
                    statsByTag.put(tagOfProblem.getProblemTag(), countTag);
                }
            }
        }

        studyDetailResponseDto.setStatsByTier(statsByTier);
        studyDetailResponseDto.setStatsByTag(statsByTag);

        return studyDetailResponseDto;
    }
}