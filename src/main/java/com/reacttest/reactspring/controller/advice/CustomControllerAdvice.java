package com.reacttest.reactspring.controller.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

//API 서버는 화면이 없는 상태에서 개발되기 때문에 잘못된 파라미터 등으로 인한 서버 내부의 예외처리를 해주는 것이 안전
//@RestControllerAdvice 가 적용되면 예외가 발생해도 호출한 쪽으로 HTTP 상태 코드와 JSON 메세지를 전달
@RestControllerAdvice
public class CustomControllerAdvice {
    //예외처리는 @ExceptionHandler 를 이용해서 특정한 타입의 예외가 발생하면 이에 대한 결과 데이터를 만들어 내는 방싣으로 작성함

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<?> notExist(NoSuchElementException e){
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", msg));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handlerIllegalArgumentException(MethodArgumentNotValidException e){
        String msg = e.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("msg", msg));
    }

}
