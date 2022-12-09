package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.Order;
import kr.com._29cm.homework.domain.Product;
import kr.com._29cm.homework.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    EntityManager em;


    @Test
    void 상품주문() throws Exception {
        Product product = createItem( "테스트상품", 10000, 10);

        int orderCnt = 5;

        Long orderId = orderService.order(product.getId(), orderCnt);

        Optional<Order> newOrder = orderRepository.findById(orderId);

        if(newOrder != Optional.<Order>empty()) {
            assertEquals("주문 가격은 가격 * 수량이다.", 10000 * 5, orderService.getTotalPrice(newOrder.get().getId()) );
            assertEquals( "주문 수량만큼 재고 감수", 10-5, product.getStock());
        }

    }

    @Test
    void 상품주문개수확인() throws Exception {
        Product product = createItem( "테스트상품", 10000, 10);

        int orderCnt = 5;

        Long orderId = orderService.order(product.getId(), orderCnt);

        assertEquals("주문한 상품 종류 수가 정확해야한다.", 1, orderService.getOrderItems(orderId).size());




    }

    private Product createItem(String name, int price, int stock) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        em.persist(product);
        return product;
    }
}