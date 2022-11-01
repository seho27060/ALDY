package com.example.demo.controller;

import com.example.demo.service.ProblemTableService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problem")
@RequiredArgsConstructor
public class ProblemTableController {
    private ProblemTableService problemTableService;

}
