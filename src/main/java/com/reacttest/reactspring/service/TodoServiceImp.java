package com.reacttest.reactspring.service;

import com.reacttest.reactspring.dto.PageRequestDTO;
import com.reacttest.reactspring.dto.PageResponseDTO;
import com.reacttest.reactspring.domain.Todo;
import com.reacttest.reactspring.dto.TodoDTO;
import com.reacttest.reactspring.repository.TodoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor //생성자 자동 주입
public class TodoServiceImp implements TodoService {

    private final ModelMapper modelMapper;
    private final TodoRepository todoRepository;


    //등록기능 메서드 정의
    @Override
    public Long register(TodoDTO todoDTO) {
        log.info("......");
        Todo todo = modelMapper.map(todoDTO, Todo.class);
        Todo savedTodo = todoRepository.save(todo);

        return savedTodo.getTno();
    }

    //조회기능 메서드 정의
    @Override
    public TodoDTO get(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow();
        TodoDTO dto = modelMapper.map(todo, TodoDTO.class);

        return dto;
    }
    //수정기능 메서드 정의
    @Override
    public void modify(TodoDTO todoDTO) {
        Optional<Todo> result = todoRepository.findById(todoDTO.getTno());
        Todo todo = result.orElseThrow();

        todo.changeTitle(todoDTO.getTitle());
        todo.changeDueDate(todoDTO.getDueDate());
        todo.changeComplete(todoDTO.isComplete());

        todoRepository.save(todo);
    }

    //삭제기능 메서드 정의
    @Override
    public void delete(Long tno) {
        todoRepository.deleteById(tno);
    }

    @Override
    public PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, //1페이지가 0이므로 주의해야함!
                                            pageRequestDTO.getSize(),
                                            Sort.by("tno").descending());
        Page<Todo> result = todoRepository.findAll(pageable);
        List<TodoDTO> dtoList = result.getContent().stream()
                .map(todo -> modelMapper.map(todo, TodoDTO.class))
                .toList();
        long totalCount = result.getTotalElements();
        PageResponseDTO<TodoDTO> responseDTO = PageResponseDTO.<TodoDTO>withAll()
                .dtoList(dtoList)
                .pageRequestDTO(pageRequestDTO)
                .totalCount(totalCount)
                .build();
        return responseDTO;
    }
}
