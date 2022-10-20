package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;


// 자바에는 2가지의 예외
// checked, unchecked
// 컴파일 시 발견되는 에러, 런타임 도중 발견되는 에러
// 3 / 0
@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException  {
    private final ErrorCode errorCode;

}