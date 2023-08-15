package com.tcha.utils.exceptions.controller;

import com.tcha.utils.exceptions.business.BusinessLogicException;
import io.lettuce.core.ScriptOutputType;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentTypeMismatchException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(
            ConstraintViolationException e) {
        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }

    @ExceptionHandler
    public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
        final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode()
                .getStatus()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST,
                "Required request body is missing");

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST,
                e.getMessage());

        return response;
    }

    //잘못된 인자값 들어온 경우, 에러 핸들러(예: Stirng -> Long)
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleNumberFormatException(NumberFormatException e) {
        log.error("# handle Exception", e);
        // TODO 애플리케이션의 에러는 에러 로그를 로그에 기록하고, 관리자에게 이메일이나 카카오 톡,
        //  슬랙 등으로 알려주는 로직이 있는게 좋습니다.

        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, "Invalid format: " + e.getMessage());

        return response;
    }

//    //잘못된 인자값 들어온 경우, 에러 핸들러 (예: String -> UUID)
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
//        log.error("# handle Exception", e);
//
//        final ErrorResponse response = ErrorResponse.of(HttpStatus.BAD_REQUEST, "Invalid  format: " + e.getMessage());
//
//        return response;
//    }

    //
//    //exception이 존재하면 NUMBERFOMAT이 안걸림
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e) {
        System.out.println("엥 여기??");
        log.error("# handle Exception", e);
        System.out.println(e.getMessage() + " >> " + e.getCause() + " << " + e.getClass());
        // TODO 애플리케이션의 에러는 에러 로그를 로그에 기록하고, 관리자에게 이메일이나 카카오 톡,
        //  슬랙 등으로 알려주는 로직이 있는게 좋습니다.

        final ErrorResponse response = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR);

        return response;
    }


}

/*
Exception Handler Method 만들기
1. 핸들링할 예외를 정한다.
2. 해당 예외에 대한 @ExceptionHandler 애너테이션을 붙인 메서드를 만든다.
3. ErrorResponse.class 에 해당 Exception 에 대한 정보를 담아내도록 구현한다. //ErrorResponse.class 에서 자세히 설명
4. 응답하는 Response 에 ErrorResponse 형의 반환값으로 정보를 담고, @ResponseStatus 으로 HttpStatus 를 담아서 반환한다.
 */