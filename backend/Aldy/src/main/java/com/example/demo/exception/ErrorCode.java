package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

// Enum <- enumeration 타입 열거형
@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    INVALID_ACCESS_TOKEN(BAD_REQUEST, "액세스 토큰이 유효하지 않습니다"),
    MISMATCH_ACCESS_TOKEN(BAD_REQUEST, "액세스 토큰의 유저 정보가 일치하지 않습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),
    WRONG_PASSWORD(UNAUTHORIZED,"비밀번호가 일치하지 않습니다"),
    DISABLED(UNAUTHORIZED, "사용할 수 없는 계정입니다."),
    LOCKED(UNAUTHORIZED, "잠긴 계정입니다."),
    ACCESSTOKEN_EXPIRED(UNAUTHORIZED, "만료된 액세스 토큰입니다."),
    REFRESHTOKEN_EXPIRED(UNAUTHORIZED, "만료된 리프레쉬 토큰입니다."),

    /* 403 LIMIT_EXCEEDED : 데이터 개수 초과 */
    MEMBER_LIMIT_EXCEEDED(FORBIDDEN, "스터디 제한 인원이 초과했습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    NOT_EXIST_MEMBER(NOT_FOUND, "해당 유저 정보를 solved.ac에서 찾을 수 없습니다."),
    CODE_NOT_FOUND(NOT_FOUND, "해당 코드를 찾을 수 없습니다"),
    STUDY_NOT_FOUND(NOT_FOUND, "해당 스터디를 찾을 수 없습니다"),
    MEMBERINSTUDY_NOT_FOUND(NOT_FOUND, "해당 맴버를 스터디에서 찾을 수 없습니다"),
    CALENDAR_NOT_FOUND(NOT_FOUND, "해당 캘린더를 찾을 수 없습니다"),
    PROBLEMTABLE_NOT_FOUND(NOT_FOUND,"해당 문제를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "존재하지 않는 리프레쉬 토큰입니다."),

    /* 403 FORBIDDEN : 권한에 의한 거절 */
//    TEST_TEST(HttpStatus.),
    UNAUTHORIZED_REQUEST(UNAUTHORIZED,"권한이 없는 요청입니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
    ALREADY_JOIN(CONFLICT,"이미 회원가입 한 유저입니다"),

    /* 500 */
    SAVE_ERROR(INTERNAL_SERVER_ERROR, "데이터 저장에 실패했습니다"),
    SOLVEDAC_ERROR(INTERNAL_SERVER_ERROR, "solvedac api 요청에 실패했습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}