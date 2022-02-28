package com.example.etiqatest.ErrorHandler;

import com.example.etiqatest.commonconstant.CommonConstant;
import com.example.etiqatest.commonconstant.ErrorConstant;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityWithIDNotFoundException.class)
    public ResponseEntity<Object> EntityWithIDNotFoundException(
            EntityWithIDNotFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CommonConstant.TIMESTAMP, LocalDateTime.now());
        body.put(CommonConstant.MESSAGE, ErrorConstant.ENTITY_NOT_FOUND);
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CourseExceptionError.class)
    public ResponseEntity<Object> CourseExceptionError(
            CourseExceptionError ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CommonConstant.TIMESTAMP, LocalDateTime.now());
        body.put(CommonConstant.MESSAGE, ErrorConstant.COURSE_NOT_ASSIGNED);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<Object> CommonException(
            CommonException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CommonConstant.TIMESTAMP, LocalDateTime.now());
        body.put(CommonConstant.MESSAGE, ErrorConstant.COMMON_EXCEPTION);
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Object> NoDataFoundException(
            NoDataFoundException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CommonConstant.TIMESTAMP, LocalDateTime.now());
        body.put(CommonConstant.MESSAGE, ErrorConstant.NO_ENTITY_FOUND);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(FieldErrorException.class)
    public ResponseEntity<Object> FieldErrorException(
            FieldErrorException ex, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CommonConstant.TIMESTAMP, LocalDateTime.now());
        body.put(CommonConstant.MESSAGE, ErrorConstant.FIELD_EMPTY_ERROR);

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(CommonConstant.TIMESTAMP, LocalDateTime.now());
        body.put(CommonConstant.STATUS,  status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put(ErrorConstant.ERROR, errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
