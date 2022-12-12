package kr.com._29cm.homework.domain;

import kr.com._29cm.homework.repository.ItemRepository;
import kr.com._29cm.homework.repository.OrderItemRepository;
import kr.com._29cm.homework.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@Transactional
@SpringBootTest
class OrderItemTest {
    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;


    @Test
    void 주문상품_추가() {
        String idInput = "782858";
        String cntInput = "10";

        Order order = Order.builder().build();

        order.addOrderItem(idInput, cntInput);

        OrderItem getItem = order.getOrderItems().get(0);

        Long itemId = getItem.getItemId();
        int itemCnt = getItem.getCount();

        assertEquals("주문 아이템에 추가된 상품 id는 입력받은 것과 같다.", Long.valueOf(idInput), itemId);
        assertEquals("주문 아이템에 추가된 상품 수량는 입력받은 것과 같다.", Integer.valueOf(cntInput), itemCnt);
    }

    @Test
    void 주문아이템_제거() {
        String idInput = "782858";
        String cntInput = "10";

        Order order = Order.builder().build();

        order.addOrderItem(idInput, cntInput);

        idInput = "782858";
        cntInput = "5";

        OrderItem orderItem = order.addOrderItem(idInput, cntInput);

        assertEquals("주문에 추가된 주문 아이템 list 사이즈는 2", 2, order.getOrderItems().size());

        order.removeOrderItem(orderItem);

        assertEquals("아이템 제거시 아이템 list 사이즈는 1", 1, order.getOrderItems().size());

    }

    @Test
    void 재고복구() {
        int originStock = 50;

        Item item = Item.builder()
                .id(12345L)
                .name("테스트")
                .price(15000)
                .stock(originStock)
                .build();

        itemRepository.save(item);


        String idInput = "12345";
        String cntInput = "10";

        Order order = Order.builder().build();

        OrderItem orderItem = order.addOrderItem(idInput, cntInput);
        orderItem.changeItem(item);
        orderItem.reduceStock();
        itemRepository.save(item);

        idInput = "12345";
        cntInput = "5";

        orderItem = order.addOrderItem(idInput, cntInput);
        orderItem.changeItem(item);
        orderItem.reduceStock();
        itemRepository.save(item);

        orderRepository.save(order);


        assertEquals("782858의 재고는 원래 재고에서 -10 -5", originStock-10-5,  itemRepository.findById(Long.valueOf(idInput)).get().getStock());

        order.resetItemStock();
        itemRepository.save(item);

        assertEquals("782858의 재고는 원래 재고로 다시 복구", originStock ,  itemRepository.findById(Long.valueOf(idInput)).get().getStock());

    }


}