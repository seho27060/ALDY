package com.example.demo.controller;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.CreateStudyRequestDto;
import com.example.demo.domain.dto.StudyDto;
import com.example.demo.service.CalendarService;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.service.MemberInStudyService;
import com.example.demo.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@Tag(name = "Study API", description = "스터디 관련 API, [담당자 : 홍석호]")
@RequestMapping(value = "/api/study")
public class StudyController {

    private final JwtTokenProvider jwtTokenProvider;

    private final StudyService studyService;

    private final MemberInStudyService memberInStudyService;

    private final CalendarService calendarService;
    @Operation(summary = "스터디 생성 API", description = "스터디 생성 관련 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "404", description = "MEMBER_NOT_FOUND"),
            @ApiResponse(responseCode = "404", description = "STUDY_NOT_FOUND"),
    })
    @PostMapping()
    public ResponseEntity createStudy(@RequestBody CreateStudyRequestDto requestDto, HttpServletRequest request) {

        String loginMember = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));

        StudyDto studyDto = studyService.createStudy(requestDto);

        memberInStudyService.setRoomLeader(studyDto.getId(), loginMember);

        return new ResponseEntity(studyDto, HttpStatus.OK);

    }

    @Operation(summary = "전체 스터디 목록 조회 API", description = "[page : 페이지], [size : 페이지 당 정보 개수], [keyword : 검색어]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "404", description = "STUDY_NOT_FOUND"),
    })
    @GetMapping()
    public ResponseEntity getAllStudyPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {

        Page<StudyDto> studyDtoPage = studyService.getAllStudyPage(page, size, keyword);

        return new ResponseEntity(studyDtoPage, HttpStatus.OK);

    }

    @Operation(summary = "내 스터디 목록 조회 API", description = "[page : 페이지], [size : 페이지 당 정보 개수]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "404", description = "STUDY_NOT_FOUND"),
    })
    @GetMapping("/mystudy")
    public ResponseEntity getMyStudyPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "15") int size,
            HttpServletRequest request
    ) {

        String loginMember = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));

        Page<StudyDto> studyDtoPage = studyService.getMyStudyPage(page, size, loginMember);

        return new ResponseEntity(studyDtoPage, HttpStatus.OK);
    }

    @Operation(summary = "스터디 상세 API", description = "[studyId : 스터디 Id]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "404", description = "STUDY_NOT_FOUND"),
    })
    @GetMapping("/{studyId}")
    public ResponseEntity getDetailStudy(@PathVariable("studyId") Long studyId) {

        StudyDto studyDto = studyService.getById(studyId);

        return new ResponseEntity(studyDto, HttpStatus.OK);

    }

    @Operation(summary = "스터디 삭제 API", description = "[studyId : 스터디 Id]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "401", description = "STUDY_NOT_FOUND"),
            @ApiResponse(responseCode = "404", description = "STUDY_NOT_FOUND"),
            @ApiResponse(responseCode = "404", description = "UNAUTHORIZED_REQUEST"),
    })
    @DeleteMapping("/{studyId}")
    public ResponseEntity deleteStudy(@PathVariable("studyId") Long studyId, HttpServletRequest request) {

        String loginMember = jwtTokenProvider.getBackjoonId(request.getHeader("Authorization"));

        if(memberInStudyService.getAuthByBackjoonId(loginMember, studyId) != 1) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        studyService.deleteById(studyId);

        return new ResponseEntity(HttpStatus.OK);
    }
    @Operation(summary = "문제 선정된 요일 반환 API", description = "문제가 설정된 요일들을 반환하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @GetMapping("/{study_id}/{year}/{month}")
    public ResponseEntity getCalendar(@PathVariable long study_id, @PathVariable int year, @PathVariable int month){
        return new ResponseEntity<>(calendarService.getCalendar(study_id, year, month), HttpStatus.OK);
    }

}
