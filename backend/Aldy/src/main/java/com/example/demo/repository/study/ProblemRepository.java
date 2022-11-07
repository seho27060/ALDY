package com.example.demo.repository.study;

import com.example.demo.domain.entity.Study.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
//    void deleteByCalendar_idAndProblemIdAndProblemDay(Long id, int problem_id, int day);

    List<Problem> findByCalendar_id(long calendar_id);

    List<Problem> findByCalendar_idAndProblemDay(long calendar_id, int day);

//    Optional<Problem> findByCalendar_idAndProblemId(Long id, int problemId);
}
