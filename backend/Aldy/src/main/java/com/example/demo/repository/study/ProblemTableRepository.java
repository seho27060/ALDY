package com.example.demo.repository.study;

import com.example.demo.domain.entity.Study.ProblemTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemTableRepository extends JpaRepository<ProblemTable, Long> {
    void deleteByCalendar_idAndProblemIdAndProblemDay(Long id, int problem_id, int day);

    List<ProblemTable> findByCalendar_id(long calendar_id);

    Optional<ProblemTable> findByCalendar_idAndProblemId(Long id, long problemId);
}
