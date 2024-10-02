package com.reacttest.reactspring.repository;

import com.reacttest.reactspring.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
