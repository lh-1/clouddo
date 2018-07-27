package com.bootdo.clouddocommon.exception;

import com.bootdo.clouddocommon.utils.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(Exception.class)
    ResponseResult exception(Exception e) {
        return ResponseResult.error(500, e.getMessage());
    }
}
