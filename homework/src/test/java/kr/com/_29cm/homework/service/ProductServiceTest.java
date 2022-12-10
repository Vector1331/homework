package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional

class ProductServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    ProductService productService;

    @Test
    void 전체상품개수확인() {
        System.out.println("breakpoint로 멈춰놓고 데이터 insert중 ..");
        List<Product> products = productService.findAll();
        assertEquals("상품 개수는 모두 19개.", 19, products.size() );


    }
}