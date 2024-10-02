package com.reacttest.reactspring.repository;

import com.reacttest.reactspring.domain.PageRequestDTO;
import com.reacttest.reactspring.domain.PageResponseDTO;
import com.reacttest.reactspring.domain.Todo;
import com.reacttest.reactspring.dto.TodoDTO;
import com.reacttest.reactspring.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    @Test
    public void test1(){
        log.info("--------------------------");
        log.info(todoRepository);
    }
    @Test
    public void testInsert(){
        for (int i = 0; i < 100; i++){
            Todo todo = Todo.builder()
                    .title("Title..." + i)
                    .dueDate(LocalDate.of(2024,10,02))
                    .writer("user00")
                    .build();
            todoRepository.save(todo);
        }
    }
    @Test
    public void testFindID(){
        //존재하는 번호로 확인
        Long tno = 33L;

        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        log.info(todo);
    }

    @Test
    public void testModify() {
        Long tno = 33L;
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        todo.changeTitle("Modified 33...");
        todo.changeComplete(true);
        todo.changeDueDate(LocalDate.of(2024,10,2));
        todoRepository.save(todo);
    }

    @Test
    public void testDelete() {
        Long tno = 1L;
        todoRepository.deleteById(tno);
    }

    @Test
    public void testPaging(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());
        Page<Todo> result = todoRepository.findAll(pageable);
        log.info(result.getTotalElements());
        result.getContent().stream().forEach(todo -> log.info(todo));
    }


    //등록 테스트
    @Test
    public void testRegister(){
        TodoDTO todoDTO = TodoDTO.builder()
                .title("서비스 테스트")
                .writer("tester")
                .dueDate(LocalDate.of(2024,10,2))
                .build();
        Long tno = todoService.register(todoDTO);
        log.info("TNO : " + tno);
    }
    //수정 테스트
    @Test
    public void testModifyA(){
        TodoDTO todoDTO = TodoDTO.builder()
                .tno(100L)
                .title("수정본입니다.")
                .complete(true)
                .dueDate(LocalDate.of(2024,10,2))
                .build();

        todoService.modify(todoDTO);
    }
    //삭제 테스트
    @Test
    public void testDeleteA(){
        todoService.delete(100L);
    }

    //페이징 테스트
    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(2)
                .size(10)
                .build();
        PageResponseDTO<TodoDTO> response = todoService.list(pageRequestDTO);

        log.info(response);
    }

}
