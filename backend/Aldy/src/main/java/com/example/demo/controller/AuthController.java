package com.example.demo.controller;

import com.example.demo.domain.dto.member.request.LoginRequestDto;
import com.example.demo.domain.dto.member.request.MemberBackjoonIdRequestDto;
import com.example.demo.domain.dto.member.request.MemberRequestDto;
import com.example.demo.domain.dto.member.request.ValidateTokenRequestDto;
import com.example.demo.domain.dto.member.response.*;

import com.example.demo.exception.ErrorResponse;

import com.example.demo.service.member.AuthService;
import com.example.demo.service.member.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth API - [담당자 : 박세호]", description = "회원가입, 로그인, Solved 연동, 중복체크")

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @Operation(summary = "로그인 API", description = "입력정보로 로그인을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = TokenDto.class))),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않습니다.",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "가입되지 않은 아이디입니다.",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequestDto loginRequestDto){
        TokenDto tokenDto = authService.login(loginRequestDto);
        return new ResponseEntity<>(tokenDto, HttpStatus.OK);
    }

    @Operation(summary = "회원 가입 API", description = "입력된 정보로 회원가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
            @ApiResponse(responseCode = "409", description = "이미 가입된 아이디입니다.",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/join")
    public ResponseEntity<MemberResponseDto> join(@RequestBody MemberRequestDto memberRequestDto){
        MemberResponseDto memberResponseDto = authService.memberJoin(memberRequestDto);
        return new ResponseEntity<>(memberResponseDto, HttpStatus.OK);
    }
    @Operation(summary = "인증용 문자열 발급", description = "요청된 backjoonId를 검색 후, {backjoonId:인증용 문자열} 형식으로 서버에 캐시 저장합니다. ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = AuthStringResonseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 backjoonId 입니다.",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/verification")
    public ResponseEntity<AuthStringResonseDto> issueAuthString(@RequestBody MemberBackjoonIdRequestDto memberBackjoonIdRequestDto) {
        AuthStringResonseDto authStringResonseDto = new AuthStringResonseDto(authService.issueAuthString(memberBackjoonIdRequestDto.getBackjoonId()));
        return new ResponseEntity<>(authStringResonseDto, HttpStatus.OK);
    }
    @Operation(summary = "solved.ac 연동", description = "요청된 backjoonId를 검색 후, 캐시로 저장된 {backjoonId:인증용 문자열}의 인증용 문자열과 입력 문자열을 비교합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = InterlockResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 backjoonId 입니다.",content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/interlock/{backjoonId}")
    public ResponseEntity<InterlockResponseDto> interlock(@PathVariable String backjoonId){
       InterlockResponseDto interlockResponseDto = authService.interlock(backjoonId);
        return new ResponseEntity<>(interlockResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "nickname 중복체크", description = "가입된 사용자중에 동일한 nickname이 있는지 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = DoubleCheckResponseDto.class))),
    })
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<DoubleCheckResponseDto> doubleCheckBackjoonId(@PathVariable String nickname){
        DoubleCheckResponseDto doubleCheckResponseDto  = authService.doubleCheckNickname(nickname);
        return new ResponseEntity<>(doubleCheckResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "email 중복체크", description = "가입된 사용자중에 동일한 email가 있는지 확인합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",content = @Content(schema = @Schema(implementation = DoubleCheckResponseDto.class))),
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<DoubleCheckResponseDto> doubleCheckContact(@PathVariable String contact){
        DoubleCheckResponseDto doubleCheckResponseDto  = authService.doubleCheckContact(contact);
        return new ResponseEntity<>(doubleCheckResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "리프레쉬 토큰 재발급 API", description = "액세스 토큰이 만료됐을시, 리프레쉬토큰을 유효한지 확인 후 액세스 토큰 재발급/ 리프레쉬 토큰이 만료시 재로그인을 요청하는 예외 반환")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않습니다."),
    })
    // refreshtoken 받음 -> 만료안되면 새로운 accesstoken 발급/ 만료된거면 다시 로그인하라고 예외던지기
    @PostMapping("/refresh")
    public ResponseEntity<ValidateTokenResponseDto> validateRefreshToken(@RequestBody ValidateTokenRequestDto validateRefreshtokenRequestDto){
        System.out.println(validateRefreshtokenRequestDto.getRefreshtoken());

        ValidateTokenResponseDto validateTokenResponseDto = jwtService.validateRefreshToken(validateRefreshtokenRequestDto.getRefreshtoken());
        System.out.println(validateTokenResponseDto);
        return new ResponseEntity<>(validateTokenResponseDto,HttpStatus.OK);
    }
}
