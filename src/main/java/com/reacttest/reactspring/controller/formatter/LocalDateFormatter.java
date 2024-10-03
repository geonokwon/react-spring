package com.reacttest.reactspring.controller.formatter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

//날짜/시간 데이터가 JSON 으로 String(문자열)로 전송되지만 서버에서는 LocalDate 로 처리된다
//이를 변환해주는 Formatter 를 추가해서 이 과정을 자동으로 처리할 수 있도록 함
public class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
    }
}
