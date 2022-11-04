package com.example.demo.config.job;

import com.example.demo.domain.entity.Study.MemberInStudy;
import com.example.demo.domain.entity.Code.RequestedCode;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.repository.study.MemberInStudyRepository;
import com.example.demo.service.study.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.Duration;
import java.time.LocalDateTime;


@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    private final MemberInStudyRepository memberInStudyRepository;

    private final EmailServiceImpl emailService;

    private int chunkSize = 5;

    @Bean
    public Job countAlertJob() {
        return jobBuilderFactory.get("countAlertJob")
                .start(countAlertStep())
                .build();
    }

    @Bean
    @JobScope
    public Step countAlertStep() {
        return stepBuilderFactory.get("countAlertStep")
//                <Reader에서 반환, Writer로 넘어갈>chunk(chunkSize)
                .<RequestedCode, MemberInStudy>chunk(chunkSize)
                .reader(jpaPagingItemReader())
                .processor(itemProcessor())
                .writer(jpaItemWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<RequestedCode> jpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<RequestedCode>()
                .name("countAlertItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT r FROM RequestedCode r")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<RequestedCode, MemberInStudy> itemProcessor() {
        return new ItemProcessor<RequestedCode, MemberInStudy>() {
            @Override
            public MemberInStudy process(RequestedCode item) throws Exception {

                // 데이터 필터
                LocalDateTime now = LocalDateTime.now();

                if(item.getIsChecked()) {
                    item.batchCheck();
                    return null;
                }
                item.batchCheck();

                if(!item.getIsDone()) {
                    return null;
                }

                // 3대신 변수 처리 가능?
                if(Duration.between(item.getRequestDate(), now).getSeconds() < 60*60*24*3) {
                    return null;
                }

                // 경고 카운터 +1
                Long studyId = item.getCode().getStudy().getId();
                Long memberId = item.getReceiver().getId();

                MemberInStudy memberInStudy = memberInStudyRepository.findByStudy_IdAndMember_Id(studyId, memberId)
                        .orElseThrow(() -> new CustomException(ErrorCode.MEMBERINSTUDY_NOT_FOUND));

                int numberOfAlerts = memberInStudy.getNumberOfAlerts();

                memberInStudy.setNumberOfAlerts(numberOfAlerts + 1);

                // 경고 횟수 3회 초과 시 강퇴 & 메일 보내기
                if(numberOfAlerts > 3) {
                    memberInStudy.setAuth(0);

                    emailService.sendEvictionMail(memberInStudy.getStudy(), memberInStudy.getMember().getEmail(), memberInStudy.getMember().getNickname(), "evict");
                }

                return memberInStudy;

            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<MemberInStudy> jpaItemWriter() {

        JpaItemWriter<MemberInStudy> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

        return jpaItemWriter;

    }


}
