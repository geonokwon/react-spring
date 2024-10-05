package com.reacttest.reactspring.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;

    private String title;
    private String writer;
    private boolean complete;
    private LocalDate dueDate;

    public void changeTitle(String title){
        this.title = title;
    }
    public void changeComplete(boolean completed){
        this.complete = completed;
    }
    public void changeDueDate(LocalDate dueDate){
        this.dueDate = dueDate;
    }

}
