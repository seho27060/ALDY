package com.example.demo.repository;

import com.example.demo.domain.entity.Problem;
import org.springframework.data.repository.CrudRepository;

public interface ProblemRedisRepository extends CrudRepository<Problem, String> {

}
