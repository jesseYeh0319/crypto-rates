package com.example.cryptorates.exception;

import com.example.cryptorates.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<?> handleRuntime(RuntimeException e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ApiResponse<?> handleConflict(ResourceConflictException e) {
        return ApiResponse.fail(409, e.getMessage());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ApiResponse<?> handleDataNotFound(DataNotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }
}
