package com.example.demo.controller;

import com.example.demo.domain.dto.ProblemChoiceRequestDto;
import com.example.demo.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    @PostMapping("/problem")
    private ResponseEntity study12(@RequestBody ProblemChoiceRequestDto problemChoiceRequestDto){

        calendarService.registerProblem(problemChoiceRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/problem/{study_id}/{date}/{problem_id}")
    private ResponseEntity study14(@PathVariable long study_id, @PathVariable int problem_id, @PathVariable String date){
        calendarService.deleteProblem(study_id, problem_id, date);
        return new ResponseEntity(HttpStatus.OK);
    }
}
