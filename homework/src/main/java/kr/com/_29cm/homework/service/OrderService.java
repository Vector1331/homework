package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.Order;
import kr.com._29cm.homework.domain.OrderItem;
import kr.com._29cm.homework.domain.Pay;
import kr.com._29cm.homework.domain.Product;
import kr.com._29cm.homework.exception.OrderException;
import kr.com._29cm.homework.repository.OrderItemRepository;
import kr.com._29cm.homework.repository.OrderRepository;
import kr.com._29cm.homework.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    /**
     * 주문 생성*/
    @Transactional
    public Long order(Long productId, int cnt) {
        Optional<Product> product = productRepository.findById(productId);
        OrderItem orderItem = new OrderItem();
        Order order = createOrder();

        if(product != Optional.<Product>empty()){
            orderItem = createOrderItem(product.get().getId(), order.getId(), product.get().getPrice(), cnt);
        }
        if(product == Optional.<Product>empty()) {
            throw new OrderException("OrderException 발생. 상품이 존재하지 않습니다. ");
        }

        return order.getId();

    }
    /**
     * 주문 아이템 생성
     * */
    public OrderItem createOrderItem(Long pid, Long orderId, int price, int cnt) {
        OrderItem orderItem = new OrderItem();

        Optional<Product> product = productRepository.findById(pid);
        if(product != Optional.<Product>empty()){
            orderItem.setPid(pid);
            orderItem.setOrderId(orderId);
            orderItem.setOrderPrice(price);
            orderItem.setCount(cnt);
        }
        product.get().removeStock(cnt);
        orderItemRepository.save(orderItem);
        return orderItem;
    }

    public Order createOrder() {
        Order order = new Order();
        orderRepository.save(order);
        return order;
    }



    public int getTotalPrice(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findAllByOrderId(orderId);
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;

    }

}
