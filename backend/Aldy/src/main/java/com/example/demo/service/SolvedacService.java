package com.example.demo.service;

import com.example.demo.domain.dto.ProblemDto;
import reactor.core.publisher.Flux;

import java.util.List;

public interface SolvedacService {

    List<ProblemDto> filter(String query);

}
