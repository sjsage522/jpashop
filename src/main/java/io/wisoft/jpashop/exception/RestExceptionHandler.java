package io.wisoft.jpashop.exception;

import io.wisoft.jpashop.controller.api.ApiResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice("io.wisoft.jpashop.controller")
public class RestExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    protected ApiResult<Object> illegalArgument(final IllegalArgumentException ex) {
        return ApiResult.failed(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    protected ApiResult<Object> illegalState(final IllegalStateException ex) {
        return ApiResult.failed(ex.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    protected ApiResult<Object> noSuchElement(final NoSuchElementException ex) {
        return ApiResult.failed(ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    protected ApiResult<Object> bindException(
            final BindException ex) {
        return ApiResult.failed(ex
                .getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage());
    }
}
