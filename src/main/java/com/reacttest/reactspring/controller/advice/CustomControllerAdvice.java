package com.reacttest.reactspring.controller.advice;


import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//API 서버는 화면이 없는 상태에서 개발되기 때문에 잘못된 파라미터 등으로 인한 서버 내부의 예외처리를 해주는 것이 안전
@RestControllerAdvice
public class CustomControllerAdvice {
    //예외처리는 @ExceptionHandler 를 이용해서 특정한 타입의 예외가 발생하면 이에 대한 결과 데이터를 만들어 내는 방싣으로 작성함

    @ExceptionHandler(Exception.class)

}
