package com.example.demo.config.job;

import com.example.demo.domain.entity.Code.Code;
import com.example.demo.domain.entity.Study.MemberInStudy;
import com.example.demo.domain.entity.Code.RequestedCode;
import com.example.demo.domain.entity.Study.Problem;
import com.example.demo.domain.entity.Study.Study;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.code.RequestedCodeRepository;
import com.example.demo.repository.study.MemberInStudyRepository;
import com.example.demo.service.study.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    private final RequestedCodeRepository requestedCodeRepository;

    private final MemberInStudyRepository memberInStudyRepository;

    private final EmailServiceImpl emailService;

    private int chunkSize = 5;

    @Bean
    public Job countAlertJob() {
        return jobBuilderFactory.get("countAlertJob")
                .start(countAlertStep())
                .build();
    }

    public Step countAlertStep() {
        return stepBuilderFactory.get("countAlertStep")
//                <Reader에서 반환, Writer로 넘어갈>chunk(chunkSize)
                .<Problem, List<MemberInStudy>>chunk(chunkSize)
                .reader(jpaPagingItemReader())
                .processor(itemProcessor())
                .writer(jpaItemListWriter())
                .build();
    }

    public JpaPagingItemReader<Problem> jpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Problem>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT p FROM Problem p")
                .build();
    }

    public ItemProcessor<Problem, List<MemberInStudy>> itemProcessor() {
        return new ItemProcessor<Problem, List<MemberInStudy>>() {
            @Override
            public List<MemberInStudy> process(Problem item) throws Exception {

                List<MemberInStudy> memberInStudyList = new ArrayList<>();

                List<Code> codeList = item.getCodeList();
                for(Code code : codeList) {

                    List<RequestedCode> requestedCodeList = requestedCodeRepository.findByCodeId(code.getId());
                    List<Long> receiverList = new ArrayList<>();
                    for(RequestedCode reqCode : requestedCodeList) {

                        // 데이터 필터 - 진작 경고를 받았는지
                        if(reqCode.getIsChecked()) {
                            continue;
                        }

                        // 데이터 필터 - 응답을 했는지
                        if(reqCode.getIsDone()) {
                            continue;
                        }

                        // 데이터 필터 - 응답을 보낸지 3일이 지났는지
                        LocalDateTime now = LocalDateTime.now();
                        LocalDateTime reqDate = reqCode.getRequestDate();
                        if(ChronoUnit.SECONDS.between(reqDate, now) <= 24 * 60 * 60 * 3) {
                            continue;
                        }

                        // 한 문제에 여러 응답요청을 받았을 경우
                        Boolean checkMember = false;
                        Long memberId = reqCode.getReceiver().getId();

                        for(Long receiver : receiverList) {
                            if(memberId == receiver) {
                                checkMember = true;
                                break;
                            }
                        }
                        reqCode.batchCheck();
                        if(checkMember) {
                            continue;
                        } else {
                            receiverList.add(memberId);
                        }

                        // 2. 경고 카운터
                        Long studyId = code.getStudy().getId();
                        MemberInStudy memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(studyId, memberId)
                                .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND));

                        // 스터디장 특혜... 강퇴 안당함
                        if(memberInStudy.getAuth() == 1) {
                            return null;
                        }

                        int numberOfAlerts = memberInStudy.getNumberOfAlerts() + 1;
                        memberInStudy.setNumberOfAlerts(numberOfAlerts);

                        // 경고 횟수 3회 초과 시 강퇴 & 메일 보내기
                        if(numberOfAlerts > 3) {
                            memberInStudy.setAuth(0);

                            emailService.sendEvictionMail(memberInStudy.getStudy(), memberInStudy.getMember().getEmail(), memberInStudy.getMember().getNickname(), "evict");
                        }
                        memberInStudyList.add(memberInStudy);
                    }
                }
                return memberInStudyList;
            }
        };
    }

    public JpaItemListWriter<MemberInStudy> jpaItemListWriter() {

        JpaItemWriter<MemberInStudy> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

        return new JpaItemListWriter<>(jpaItemWriter);

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Bean
    public Job checkLevelJob() {

        return jobBuilderFactory.get("checkLevelJob")
                .start(checkLevelStep())
                .build();
    }

    public Step checkLevelStep() {
        return stepBuilderFactory.get("checkLevelStep")
                .<Problem, Study>chunk(chunkSize)
                .reader(jpaPagingItemReader())
                .processor(itemProcessor2())
                .writer(jpaItemWriter())
                .build();
    }

    public ItemProcessor<Problem, Study> itemProcessor2() {
        return new ItemProcessor<Problem, Study>() {
            @Override
            public Study process(Problem item) throws Exception {

                LocalDate problemDate = LocalDate.of(item.getCalendar().getCalendarYear(), item.getCalendar().getCalendarMonth(), item.getProblemDay());
                LocalDate now = LocalDate.now();

                if(ChronoUnit.DAYS.between(problemDate, now) <= 0) {
                    return null;
                }

                if(item.getIsChecked()) {
                    return null;
                }

                List<Code> codeList = item.getCodeList();
                if(codeList == null) {
                    return null;
                }

                Study study = item.getCalendar().getStudy();
                Boolean checkCode = false;

                for(Code code : codeList) {
                    if(code.getProcess() >= 3) {
                        checkCode = true;
                        break;
                    }
                }

                if(checkCode) {
                    item.batchCheck();
                    study.setLevel(study.getLevel() + 1);
                    study.setActivationLevel(study.getActivationLevel() + 1);
                } else {
                    return null;
                }

                return study;
            }
        };
    }

    public JpaItemWriter<Study> jpaItemWriter() {

        JpaItemWriter<Study> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

        return jpaItemWriter;

    }

}
