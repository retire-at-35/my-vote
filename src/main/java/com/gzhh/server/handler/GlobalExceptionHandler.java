package com.gzhh.server.handler;

import com.gzhh.common.exception.BaseException;
import com.gzhh.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {



    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Result<?>> handleCustomException(BaseException ex, HttpServletResponse response) {
        Result<?> result = Result.error(ex.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value()); // 设置400状态码
        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
