package com.example.demo.controller;

import com.example.demo.domain.dto.ProblemChoiceRequestDto;
import com.example.demo.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "달력에 문제 추가/삭제/반환 API - 담당자 조성민", description = "달력에 문제를 추가하거나 삭제, 반환하는 API")
@RequestMapping("/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    @PostMapping("/problem")
    @Operation(summary = "달력에 문제 추가 API", description = "달력 특정 요일에 문제를 추가하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    private ResponseEntity study12(@RequestBody ProblemChoiceRequestDto problemChoiceRequestDto){

        calendarService.registerProblem(problemChoiceRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/problem/{study_id}/{date}/{problem_id}")
    @Operation(summary = "달력에서 문제 삭제 API", description = "달력에서 문제를 하나씩 삭제시키는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    private ResponseEntity study14(@PathVariable long study_id, @PathVariable int problem_id, @PathVariable String date){
        calendarService.deleteProblem(study_id, problem_id, date);
        return new ResponseEntity(HttpStatus.OK);
    }
}
