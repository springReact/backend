package org.zerock.apiserver.repository;

import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.apiserver.domain.Product;
import org.zerock.apiserver.dto.PageRequestDTO;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 추가")
    @Test
    void testInsert() {

        for (int i = 0; i < 20; i++) {
            Product product = Product.builder()
                    .pname("Test " + i)
                    .pdesc("Test Desc " + i)
                    .price(1000)
                    .build();

            product.addImageString(UUID.randomUUID() + "_" + "IMAGE" + i + 1 + ".jpg");
            product.addImageString(UUID.randomUUID() + "_" + "IMAGE" + i + 11 + ".jpg");

            productRepository.save(product);
        }
    }

    @DisplayName("상품 조회 n + 1")
    @Test
    void testRead() {
        Long pno = 1L;

        Optional<Product> result = productRepository.findById(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());
    }

    @DisplayName("상품 조회 n + 1(2)")
    @Test
    void testRead2() {
        Long pno = 1L;

        Optional<Product> result = productRepository.selectOne(pno);

        Product product = result.orElseThrow();

        log.info(product);

        log.info(product.getImageList());
    }

    @Commit
    @Transactional
    @DisplayName("상품 삭제")
    @Test
    void testDelete() {
        Long pno = 2L;

        productRepository.updateToDelete(pno, true);
    }

    @DisplayName("상품 수정")
    @Test
    void testUpdate() {
        Product product = productRepository.selectOne(1L).get();

        product.changePrice(3000);

        product.clearList();

        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE1.jpg");
        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE2.jpg");
        product.addImageString(UUID.randomUUID() + "_" + "PIMAGE3.jpg");

        productRepository.save(product);
    }

    @DisplayName("상품 리스트 조회 페이징 n + 1")
    @Test
    void testList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("pno").descending());

        Page<Object[]> result = productRepository.selectList(pageable);

        result.getContent().forEach(arr -> log.info(Arrays.toString(arr)));
    }

    @DisplayName("검색 ")
    @Test
    void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();

        productRepository.searchList(pageRequestDTO);
    }
}