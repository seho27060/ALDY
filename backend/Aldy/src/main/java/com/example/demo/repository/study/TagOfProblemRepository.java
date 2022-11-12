package com.example.demo.repository.study;

import com.example.demo.domain.entity.Study.TagOfProblem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagOfProblemRepository extends JpaRepository<TagOfProblem, Long> {

    List<TagOfProblem> findByProblemId(Long problemId);

}
