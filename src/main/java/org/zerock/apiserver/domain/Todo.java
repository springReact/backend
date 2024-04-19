package org.zerock.apiserver.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = PROTECTED)
@Builder
@Table(name = "tbl_todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long tno;

    @Column(length = 500, nullable = false)
    private String title;

    private String content;

    private boolean complete;

    private LocalDate dueDate;

    public Todo(Long tno, String title, String content, boolean complete, LocalDate dueDate) {
        this.tno = tno;
        this.title = title;
        this.content = content;
        this.complete = complete;
        this.dueDate = dueDate;
        boolean flag = true;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeComplete(boolean complete) {
        this.complete = complete;
    }

    public void changeDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}
