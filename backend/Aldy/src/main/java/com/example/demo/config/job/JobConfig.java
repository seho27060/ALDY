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
    public Job checkLevelJob() {

        return jobBuilderFactory.get("checkLevelJob")
                .start(checkLevelStep())
                .build();
    }

    public Step checkLevelStep() {
        return stepBuilderFactory.get("checkLevelStep")
                .<Problem, Study>chunk(chunkSize)
                .reader(
                        new JpaPagingItemReaderBuilder<Problem>()
                                .name("checkLevelItemReader")
                                .entityManagerFactory(entityManagerFactory)
                                .pageSize(chunkSize)
                                .queryString("SELECT p FROM PROBLEM p")
                                .build()
                )
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
