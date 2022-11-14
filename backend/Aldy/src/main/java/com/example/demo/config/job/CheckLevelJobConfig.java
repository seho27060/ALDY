package com.example.demo.config.job;

import com.example.demo.domain.entity.Code.Code;
import com.example.demo.domain.entity.Study.Calendar;
import com.example.demo.domain.entity.Study.Problem;
import com.example.demo.domain.entity.Study.Study;
import com.example.demo.repository.study.CalendarRepository;
import com.example.demo.repository.study.ProblemRepository;
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
import java.util.List;


@Configuration
@RequiredArgsConstructor
public class CheckLevelJobConfig {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final EntityManagerFactory entityManagerFactory;

    private final CalendarRepository calendarRepository;

    private final ProblemRepository problemRepository;

    @Bean
    public Job checkLevelJob() {
        return jobBuilderFactory.get("checkLevelJob")
                .start(checkLevelStep())
                .build();
    }

    public Step checkLevelStep() {
        int chunkSize = 5;
        return stepBuilderFactory.get("checkLevelStep")
                .<Study, Study>chunk(chunkSize)
                .reader(
                        new JpaPagingItemReaderBuilder<Study>()
                                .name("checkLevelItemReader")
                                .entityManagerFactory(entityManagerFactory)
                                .pageSize(chunkSize)
                                .queryString("SELECT s FROM Study s")
                                .build()
                )
                .processor(
                        (ItemProcessor<Study, Study>) item -> {

                            LocalDate now = LocalDate.now();
                            LocalDateTime nowTime = LocalDateTime.now();

                            LocalDateTime studyTime = item.getCreatedDate();
                            long daysBeweenStudy = ChronoUnit.DAYS.between(studyTime, nowTime);
                            if(0 <= daysBeweenStudy && daysBeweenStudy < 2) {
                                item.setActivationLevel(2);
                                return item;
                            }
                            if(2 <= daysBeweenStudy && daysBeweenStudy < 4) {
                                item.setActivationLevel(3);
                                return item;
                            }

                            long minDaysBetween = 2134567890;

                            List<Calendar> calendarList = calendarRepository.findByStudy_id(item.getId());
                            for(Calendar calendar : calendarList) {

                                List<Problem> problemList = problemRepository.findByCalendar_id(calendar.getId());
                                for(Problem problem : problemList) {

                                    LocalDate problemDate = LocalDate.of(problem.getCalendar().getCalendarYear(), problem.getCalendar().getCalendarMonth(), problem.getProblemDay());
                                    long daysBetweenProblem = ChronoUnit.DAYS.between(problemDate, now);
                                    if(daysBetweenProblem <= 0) {
                                        continue;
                                    }
                                    minDaysBetween = Math.min(daysBetweenProblem, minDaysBetween);

                                    if(problem.getIsLevelChecked()) {
                                        continue;
                                    }

                                    List<Code> codeList = problem.getCodeList();
                                    boolean checkCode = false;
                                    for(Code code : codeList) {
                                        if(code.getProcess() >= 2) {
                                            checkCode = true;
                                            break;
                                        }
                                    }

                                    if(checkCode) {
                                        problem.batchLevelCheck();
                                        item.setLevel(item.getLevel() + 1);
                                        if(item.getActivationLevel() == 0 || item.getActivationLevel() == 1) {
                                            item.setActivationLevel(3);
                                        } else {
                                            int activationLevel = item.getActivationLevel() + 1;
                                            if(activationLevel > 5) {
                                                item.setActivationLevel(5);
                                            } else {
                                                item.setActivationLevel(activationLevel);
                                            }
                                        }
                                    }
                                    else {
                                        problem.batchLevelCheck();
                                    }
                                }
                            }

                            if(2 < minDaysBetween && minDaysBetween <= 4) {
                                item.setActivationLevel(1);
                            }
                            if(4 < minDaysBetween) {
                                item.setActivationLevel(0);
                            }

                            return item;
                        }
                )
                .writer(jpaItemWriter())
                .build();
    }

    public JpaItemWriter<Study> jpaItemWriter() {

        JpaItemWriter<Study> jpaItemWriter = new JpaItemWriter<>();
        jpaItemWriter.setEntityManagerFactory(entityManagerFactory);

        return jpaItemWriter;

    }

}
