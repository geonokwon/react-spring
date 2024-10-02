package com.reacttest.reactspring.service;

import com.reacttest.reactspring.domain.PageRequestDTO;
import com.reacttest.reactspring.domain.PageResponseDTO;
import com.reacttest.reactspring.dto.TodoDTO;

public interface TodoService {
    //등록기능 구현
    Long register(TodoDTO todoDTO);
    //조회기능 구현
    TodoDTO get(Long tno);
    //수정기능 구현
    void modify(TodoDTO todoDTO);
    //삭제기능 구현
    void delete(Long tno);

    //페이징처리 구현
    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);

}
