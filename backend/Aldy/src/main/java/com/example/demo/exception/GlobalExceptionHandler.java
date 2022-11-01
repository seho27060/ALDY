package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice // <- 3 키워드
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}



// 요청 -> 디스패처 서블릿 -> 컨트롤러 -> 서비스 -> 레포지토리 -> 디비 -> 레포지토리 -> 서비스 -> 컨트롤러 -> 디스패처 서블릿 -> 프론트
// 디스패처 서블릿
// 컨트롤러
// 서비스
// 레포지토리