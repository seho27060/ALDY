package com.example.demo.config.job;


import com.example.demo.domain.dto.study.MemberInStudyDto;
import com.example.demo.domain.entity.Code.Code;
import com.example.demo.domain.entity.Code.RequestedCode;
import com.example.demo.domain.entity.Member.Member;
import com.example.demo.domain.entity.Study.Calendar;
import com.example.demo.domain.entity.Study.MemberInStudy;
import com.example.demo.domain.entity.Study.Problem;
import com.example.demo.domain.entity.Study.Study;
import com.example.demo.repository.code.CodeRepository;
import com.example.demo.repository.code.RequestedCodeRepository;
import com.example.demo.repository.study.CalendarRepository;
import com.example.demo.repository.study.MemberInStudyRepository;
import com.example.demo.repository.study.ProblemRepository;
import com.example.demo.service.study.EmailServiceImpl;
import com.example.demo.service.study.MemberInStudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CountAlertJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    private final MemberInStudyRepository memberInStudyRepository;

    private final MemberInStudyService memberInStudyService;

    private final CalendarRepository calendarRepository;

    private final ProblemRepository problemRepository;

    private final CodeRepository codeRepository;

    private final RequestedCodeRepository requestedCodeRepository;

    private final EmailServiceImpl emailService;


    @Bean
    public Job countAlertJob() {
        return jobBuilderFactory.get("countAlertJob")
                .start(countAlertStep())
                .build();
    }

    public Step countAlertStep() {
        int chunkSize = 5;
        return stepBuilderFactory.get("countAlertStep")
                .<Study, Study>chunk(chunkSize)
                .reader(
                        new JpaPagingItemReaderBuilder<Study>()
                                .name("countAlertItemReader")
                                .entityManagerFactory(entityManagerFactory)
                                .pageSize(chunkSize)
                                .queryString("SELECT s FROM Study s")
                                .build()
                )
                .processor(
                        (ItemProcessor<Study, Study>) item -> {

                            LocalDate now = LocalDate.now();

                            List<Calendar> calendarList = calendarRepository.findByStudy_id(item.getId());
                            for(Calendar calendar : calendarList) {

                                List<Problem> problemList = problemRepository.findByCalendar_id(calendar.getId());
                                for(Problem problem : problemList) {

                                    LocalDate problemDate = LocalDate.of(problem.getCalendar().getCalendarYear(), problem.getCalendar().getCalendarMonth(), problem.getProblemDay());
                                    if(ChronoUnit.DAYS.between(problemDate, now) <= 0) {
                                        continue;
                                    }

                                    // ?????? ?????? ?????? study??? ?????? - study??? ?????? member hashmap??? ??????
                                    HashMap<Member, Boolean> memberMap = new HashMap<>();
                                    for(MemberInStudy memberInStudy : memberInStudyRepository.findByStudy_Id(item.getId())) {
                                        memberMap.put(memberInStudy.getMember(), false);
                                    }

                                    List<Long> receiverList = new ArrayList<>();
                                    List<Code> codeList = codeRepository.findByStudy_idAndProblem_id(item.getId(), problem.getId());
                                    for(Code code : codeList) {
                                        // ?????? ?????? ?????? study??? ?????? - code ????????? member hash ??? ??????
                                        memberMap.put(code.getWriter(), true);

                                        List<RequestedCode> requestedCodeList = requestedCodeRepository.findByCodeId(code.getId());
                                        for(RequestedCode reqCode : requestedCodeList) {
                                            // ????????? ?????? - ??????????????? ?????????, ????????? ????????? ??????
                                            if(reqCode.getIsChecked() || reqCode.getIsDone()) {
                                                reqCode.batchCheck();
                                                continue;
                                            }

                                            // ????????? ?????? - ????????? ????????? 3?????? ????????? ???????????? ??????
                                            LocalDateTime reqDate = reqCode.getRequestDate();
                                            LocalDateTime nowTime = LocalDateTime.now();
                                            if(ChronoUnit.SECONDS.between(reqDate, nowTime) <= 24 * 60 * 60 * 3) {
                                                continue;
                                            }

                                            // ????????? ?????? - ????????? ???????????? ????????? ?????????
                                            Long memberId = reqCode.getReceiver().getId();
                                            MemberInStudy memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(item.getId(), memberId).get();
                                            if(!(memberInStudy.getAuth() == 1 || memberInStudy.getAuth() == 2)) {
                                                problem.batchCheck();
                                                continue;
                                            }

                                            // ??? ????????? ?????? ??????????????? ????????? ??????
                                            boolean checkMember = false;

                                            for(Long receiver : receiverList) {
                                                if(memberId.equals(receiver)) {
                                                    checkMember = true;
                                                    break;
                                                }
                                            }
                                            if(checkMember) {
                                                reqCode.batchCheck();
                                                continue;
                                            } else {
                                                receiverList.add(memberId);
                                            }

                                            // ?????? ?????????
                                            plusAlert(memberInStudy);

                                            reqCode.batchCheck();
                                        }
                                    }

                                    // ??? ?????? Batch?????? ???????????? ???????????? ???????????? ?????? ??????
                                    if(problem.getIsChecked()) {
                                        continue;
                                    }

                                    // ?????? ?????? ?????? study??? ?????? - code ???????????? ?????? ???????????? ??????
                                    memberMap.forEach((key, value) -> {
                                        if(value) {
                                            return;
                                        }
                                        MemberInStudy memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(item.getId(), key.getId()).get();
                                        if(!(memberInStudy.getAuth() == 1 || memberInStudy.getAuth() == 2)) {
                                            return;
                                        }

                                        // ?????? ?????????
                                        plusAlert(memberInStudy);
                                    });
                                    problem.batchCheck();
                                }
                            }
                            return item;
                        }
                )
                .writer(countAlertItemWriter())
                .build();
    }

    public JpaItemWriter<Study> countAlertItemWriter() {

        JpaItemWriter<Study> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

        return jpaItemWriter;

    }

    private void plusAlert(MemberInStudy memberInStudy) {
        int numberOfAlerts = memberInStudy.getNumberOfAlerts() + 1;

        memberInStudy.setNumberOfAlerts(numberOfAlerts);

        // ?????? ?????? 3??? ?????? ??? ?????? & ?????? ?????????
        if(numberOfAlerts > 3) {
            memberInStudy.setAuth(0);

            emailService.sendEvictionMail(memberInStudy.getStudy(), memberInStudy.getMember().getEmail(), memberInStudy.getMember().getNickname(), "evict");

            memberInStudyService.checkLeader(new MemberInStudyDto(memberInStudy));
        }
    }

}
