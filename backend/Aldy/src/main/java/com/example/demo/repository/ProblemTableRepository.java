package com.example.demo.repository;

import com.example.demo.domain.entity.ProblemTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTableRepository extends JpaRepository<ProblemTable, Long> {
}
