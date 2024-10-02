package com.reacttest.reactspring.controller;

import com.reacttest.reactspring.domain.PageRequestDTO;
import com.reacttest.reactspring.domain.PageResponseDTO;
import com.reacttest.reactspring.dto.TodoDTO;
import com.reacttest.reactspring.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

    @GetMapping("/{tno}")
    public TodoDTO get(@PathVariable(name = "tno") Long tno){
        return todoService.get(tno);
    }
    @GetMapping("/list")
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO){
        log.info(pageRequestDTO);
        return todoService.list(pageRequestDTO);
    }




}
