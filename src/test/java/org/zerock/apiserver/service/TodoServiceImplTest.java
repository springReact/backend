package org.zerock.apiserver.service;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.apiserver.dto.PageRequestDTO;
import org.zerock.apiserver.dto.TodoDTO;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
class TodoServiceImplTest {

    @Autowired
    TodoService todoService;

    @DisplayName("get Test")
    @Test
    void testGet() {
        Long tno = 50L;

        log.info(todoService.get(tno));
    }

    @DisplayName("생성 테스트")
    @Test
    void testRegister() {

        TodoDTO todoDTO = TodoDTO.builder()
                .title("Title...")
                .content("Content........")
                .dueDate(LocalDate.of(2024, 04, 17))
                .build();

        log.info(todoService.register(todoDTO));

    }
    
    @DisplayName("페이징 리스트 테스트")
    @Test
    void testGetList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(11).build();

        log.info(todoService.getList(pageRequestDTO));
    }
}