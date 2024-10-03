package com.reacttest.reactspring.controller;

import com.reacttest.reactspring.domain.PageRequestDTO;
import com.reacttest.reactspring.domain.PageResponseDTO;
import com.reacttest.reactspring.dto.TodoDTO;
import com.reacttest.reactspring.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    //POST 방식의 등록 처리
    @PostMapping("/")
    public Map<String, Long> register(@RequestBody TodoDTO todoDTO){
        log.info("TodoDTO : " + todoDTO);
        Long tno = todoService.register(todoDTO);
        return Map.of("tno", tno);
    }

    //PUT 방식의 수정 처리
    @PutMapping("/{tno}")
    public Map<String, String> modify(@PathVariable(name = "tno")Long tno, @RequestBody TodoDTO todoDTO){
        todoDTO.setTno(tno);
        log.info("Modify : " + todoDTO);
        todoService.modify(todoDTO);
        return Map.of("RESULT", "SUCCESS");
    }

    //DELETE 방식의 삭제 처리
    @DeleteMapping("/{tno}")
    public Map<String, String> delete(@PathVariable(name = "tno")Long tno){
        log.info("Delete : " + tno);
        todoService.delete(tno);
        return Map.of("RESULT", "SUCCESS");
    }



}
