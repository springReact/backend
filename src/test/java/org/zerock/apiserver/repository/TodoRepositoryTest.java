package org.zerock.apiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.apiserver.domain.Todo;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Log4j2
class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @DisplayName("Todo 엔티티 테스트")
    @Test
    void test1() {
        Assertions.assertNotNull(todoRepository);
        log.info("테스트 리포지토리 = " + todoRepository.getClass().getName());
    }

    @DisplayName("TODO 테이블 INSERT 테스트")
    @Test
    void testInsert() {

        for (int i = 0; i < 100; i++) {

            Todo todo = Todo.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .dueDate(LocalDate.of(2024, 04, 17))
                    .build();

            Todo result = todoRepository.save(todo);

            log.info("Insert Result = " + result);
        }
    }

    @DisplayName("Todo 테이블 ReadTest")
    @Test
    void testRead() {
        Long tno = 1L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        log.info("Read Todo = " + todo);
    }

    @DisplayName("Todo 테이블 UpdateTest")
    @Test
    void testUpdate() {
        // 먼저 로딩 하고 엔티티 객체 변경 /setter
        Long tno = 1L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo todo = result.orElseThrow();

        todo.changeTitle("Update Title");
        todo.changeContent("Update Content");
        todo.changeComplete(true);

        todoRepository.save(todo);
    }

    @DisplayName("Todo 테이블 PagingTest")
    @Test
    void testPaging() {
        // 페이지 번호는 0번부터
        Pageable pageable = PageRequest.of(0, 10, Sort.by("tno").descending());

        Page<Todo> result = todoRepository.findAll(pageable);

        log.info("페이지 result.getTotalElements = " + result.getTotalElements());

        log.info("페이지 result.getContent = " + result.getContent());
    }

//    @DisplayName("Todo 테이블 QueryDslTest")
//    @Test
//    void testSearch1() {
//        todoRepository.search1();
//    }
}