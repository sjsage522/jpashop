package io.wisoft.jpashop.exception;

import io.wisoft.jpashop.controller.api.ApiResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice("io.wisoft.jpashop.controller")
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    protected ApiResult<Object> illegalArgument(final IllegalArgumentException ex) {
        return ApiResult.failed(ex.getMessage());
    }
}
