package com.example.demo.repository;

import com.example.demo.domain.entity.ProblemTable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemTableRepository extends JpaRepository<ProblemTable, Long> {
    void deleteByCalendar_idAndProblemIdAndProblemDay(Long id, int problem_id, int day);

    List<ProblemTable> findByCalendar_id(long calendar_id);
}
