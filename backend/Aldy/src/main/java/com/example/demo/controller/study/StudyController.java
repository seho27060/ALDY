package com.example.demo.controller.study;

import com.example.demo.config.jwt.JwtTokenProvider;
import com.example.demo.domain.dto.study.*;
import com.example.demo.service.code.CodeService;
import com.example.demo.service.study.CalendarService;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.service.study.MemberInStudyService;
import com.example.demo.service.study.EmailServiceImpl;
import com.example.demo.service.study.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Study API", description = "스터디 관련 API, [담당자 : 홍석호]")
@RequestMapping(value = "/api/study")
public class StudyController {

    private final CodeService codeService;
    private final JwtTokenProvider jwtTokenProvider;
    private final StudyService studyService;
    private final MemberInStudyService memberInStudyService;
    private final CalendarService calendarService;
    private final EmailServiceImpl emailServiceImpl;

    @Operation(summary = "스터디 생성 API", description = "스터디 생성 관련 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = StudyDto.class))),
            @ApiResponse(responseCode = "404", description = "MEMBER_NOT_FOUND"),
            @ApiResponse(responseCode = "404", description = "STUDY_NOT_FOUND"),
    })
    @PostMapping()
    public ResponseEntity<StudyDto> createStudy(@RequestBody CreateStudyRequestDto requestDto, HttpServletRequest request) {

        String loginMember = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));

        StudyDto studyDto = studyService.createStudy(requestDto);

        memberInStudyService.setRoomLeader(studyDto.getId(), loginMember);

        return new ResponseEntity<>(studyDto, HttpStatus.OK);

    }

    @Operation(summary = "전체 스터디 목록 조회 API", description = "[page : 페이지], [size : 페이지 당 정보 개수], [keyword : 검색어]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = StudyPageResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "STUDY_NOT_FOUND"),
    })
    @GetMapping()
    public ResponseEntity<StudyPageResponseDto> getAllStudyPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {

        StudyPageResponseDto studyDtoPage = studyService.getAllStudyPage(page - 1, size, keyword);

        return new ResponseEntity<>(studyDtoPage, HttpStatus.OK);

    }

    @Operation(summary = "내 스터디 목록 조회 API", description = "[page : 페이지], [size : 페이지 당 정보 개수]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = MyStudyPageResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "STUDY_NOT_FOUND"),
    })
    @GetMapping("/mystudy")
    public ResponseEntity<MyStudyPageResponseDto> getMyStudyPage(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            HttpServletRequest request
    ) {

        String loginMember = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));

        MyStudyPageResponseDto myStudyDtoPage = studyService.getMyStudyPage(page - 1, size, loginMember);

        return new ResponseEntity<>(myStudyDtoPage, HttpStatus.OK);
    }

    @Operation(summary = "스터디 상세 API", description = "[studyId : 스터디 Id]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS", content = @Content(schema = @Schema(implementation = StudyDetailResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "STUDY_NOT_FOUND"),
            @ApiResponse(responseCode = "404", description = "MEMBERINSTUDY_NOT_FOUND"),
    })
    @GetMapping("/{studyId}")
    public ResponseEntity<StudyDetailResponseDto> getDetailStudy(@PathVariable("studyId") Long studyId, HttpServletRequest request) {

        String loginMember = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));

        StudyDetailResponseDto studyDetailResponseDto = studyService.getById(studyId, loginMember);

        return new ResponseEntity<>(studyDetailResponseDto, HttpStatus.OK);

    }

    @Operation(summary = "스터디 삭제 API", description = "[studyId : 스터디 Id]")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "401", description = "STUDY_NOT_FOUND"),
            @ApiResponse(responseCode = "404", description = "UNAUTHORIZED_REQUEST"),
    })
    @DeleteMapping("/{studyId}")
    public ResponseEntity<?> deleteStudy(@PathVariable("studyId") Long studyId, HttpServletRequest request) {

        String loginMember = jwtTokenProvider.getBaekjoonId(request.getHeader("Authorization"));

        if(memberInStudyService.getAuthByBaekjoonId(loginMember, studyId) != 1) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REQUEST);
        }

        studyService.deleteById(studyId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "문제 선정된 요일 반환 API - [담당자 조성민]", description = "문제가 설정된 요일들을 반환하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    @GetMapping("/{study_id}/{year}/{month}")
    public ResponseEntity<CalendarDto> getCalendar(@PathVariable long study_id, @PathVariable int year, @PathVariable int month){
        return new ResponseEntity<>(calendarService.getCalendar(study_id, year, month), HttpStatus.OK);
    }

    @Operation(summary = "이메일 발송 테스트 API - [담당자 조성민]", description = "스웨거에서만 테스트 용도로 사용, 실제로는 서비스 단에서 끌어서 사용할 것")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "500", description = "뭔가 잘못됨"),
    })
    @PostMapping("/mail/send")
    public String sendMail(MailDto mailDto) throws InterruptedException {
        emailServiceImpl.sendSimpleMessage(mailDto);
        System.out.println("메일 전송 완료");
        return "AfterMail.html";
    }

    @PostMapping("/problem")
    @Operation(summary = "달력에 문제 추가 API - [담당자 조성민]", description = "달력 특정 요일에 문제를 추가하는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    private ResponseEntity<List<ProblemDto>> study12(@RequestBody ProblemChoiceRequestDto problemChoiceRequestDto){

        List<ProblemDto> problemDtoList = calendarService.registerProblem(problemChoiceRequestDto);
        return new ResponseEntity<>(problemDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/problem/{problem_id}")
    @Operation(summary = "달력에서 문제 삭제 API - [담당자 조성민]", description = "달력에서 문제를 하나씩 삭제시키는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    private ResponseEntity<?> study14(long problem_id){
        calendarService.deleteProblem(problem_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/process/{study_id}/{problem_id}")
    @Operation(summary = "각 팀원들의 문제별 단계를 보여주는 API - [담당자 조성민]", description = "팀원들이 현재 특정문제에서" +
            "몇 단계까지 진행했는지 보여주는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    private ResponseEntity<StudyStatusDto> getProcessOfStudy(@PathVariable long study_id ,@PathVariable long problem_id
    , HttpServletRequest request){

        StudyStatusDto studyStatusDto = codeService.getProcessOfStudy(study_id, problem_id, request);
        return new ResponseEntity<>(studyStatusDto, HttpStatus.OK);
    }

    @GetMapping("/problem/{study_id}/{year}/{month}/{day}")
    @Operation(summary = "해당 날짜에 어떤 문제 있는지 반환해주는 API - [담당자 조성민]", description = "머리아파 죽겠음" +
            " 그래도 기어코 해내주는 API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
    })
    private ResponseEntity getProblemsOfDay(@PathVariable long study_id
                                            , @PathVariable int year, @PathVariable int month,
                                            @PathVariable int day,
                                            HttpServletRequest request){

        List<ProblemDto> problemDtoList = codeService.getProblemsOfDay(study_id, year, month, day, request);
        return new ResponseEntity(problemDtoList, HttpStatus.OK);
    }
}
