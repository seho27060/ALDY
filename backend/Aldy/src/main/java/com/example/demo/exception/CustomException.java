package com.example.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException  {
    private final ErrorCode errorCode;

}