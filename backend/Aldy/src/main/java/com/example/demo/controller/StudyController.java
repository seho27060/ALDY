package com.example.demo.controller;

import com.example.demo.domain.dto.CreateStudyRequestDto;
import com.example.demo.domain.dto.MailDto;
import com.example.demo.domain.dto.StudyDto;
import com.example.demo.service.CalendarService;
import com.example.demo.service.EmailService;
import com.example.demo.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Study API", description = "스터디 관련 API")
@RequestMapping(value = "/api/study")
public class StudyController {

    private final StudyService studyService;
    private final CalendarService calendarService;

    private final EmailService emailService;
    @Operation(summary = "스터디 생성 API", description = "스터디 생성 관련 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @PostMapping()
    public ResponseEntity createStudy(@RequestBody CreateStudyRequestDto requestDto) {

        StudyDto studyDto = studyService.createStudy(requestDto);

        return new ResponseEntity(studyDto, HttpStatus.OK);

    }

    @Operation(summary = "전체 스터디 목록 조회 API", description = "[page : 페이지], [size : 페이지 당 정보 개수], [keyword : 검색어]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "스터디 없음"),
    })
    @GetMapping()
    public ResponseEntity getAllStudyPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        Page<StudyDto> studyDtoPage = studyService.getAllStudyPage(page, size, keyword);

        return new ResponseEntity(studyDtoPage, HttpStatus.OK);
    }

    @Operation(summary = "내 스터디 목록 조회 API", description = "[page : 페이지], [size : 페이지 당 정보 개수], [keyword : 검색어], [memberId : 내아이디]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "스터디 없음"),
    })
    @GetMapping("/mystudy/{memberId}")
    public ResponseEntity getMyStudyPage(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @PathVariable("memberId") Long memberId
    ) {
        Page<StudyDto> studyDtoPage = studyService.getMyStudyPage(page, size, keyword, memberId);

        return new ResponseEntity(studyDtoPage, HttpStatus.OK);
    }

    @Operation(summary = "스터디 상세 API", description = "스터디 상세 조회 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @GetMapping("/{studyId}")
    public ResponseEntity getDetailStudy(@PathVariable("studyId") Long studyId) {
        StudyDto studyDto = studyService.getById(studyId);

        return new ResponseEntity(studyDto, HttpStatus.OK);
    }

    @Operation(summary = "스터디 삭제 API", description = "스터디 삭제 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @DeleteMapping("/{studyId}")
    public ResponseEntity deleteStudy(@PathVariable("studyId") Long studyId) {

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

    @Operation(summary = "이메일 발송 테스트 API", description = "스웨거에서만 테스트 용도로 사용, 실제로는 서비스 단에서 끌어서 사용할 것")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "뭔가 잘못됨"),
    })
    @PostMapping("/mail/send")
    public String sendMail(MailDto mailDto) {
        emailService.sendSimpleMessage(mailDto);
        System.out.println("메일 전송 완료");
        return "AfterMail.html";
    }
}
