package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.*;
import kr.com._29cm.homework.exception.OrderException;
import kr.com._29cm.homework.exception.SoldOutException;
import kr.com._29cm.homework.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    /**
     * 주문 생성
     * */
    @Transactional
    public Long order(Long productId, int cnt) {
        Product product = productRepository.findById(productId);
        Long orderItemId;
        Order order = createOrder();

        if(product != null){
            orderItemId = createOrderItem(product.getId(), order.getId(), product.getPrice(), cnt);
            if(orderItemId == null) {
                return 0L;
            }
        }
        if(product == null) {
            throw new OrderException("OrderException 발생. 상품이 존재하지 않습니다. ");
        }

        return order.getId();

    }
    /**
     * 주문 아이템 생성
     */
    public Long createOrderItem(Long pid, Long orderId, int price, int cnt) {
        OrderItem orderItem = new OrderItem();

        Product product = productRepository.findById(pid);
        if(product != null){
            orderItem.setPid(pid);
            orderItem.setOrderId(orderId);
            orderItem.setOrderPrice(price);
            orderItem.setCount(cnt);
        }
        try{
            product.removeStock(cnt);
            orderItemRepository.save(orderItem);
        }
        catch (SoldOutException ex){
            System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
        }
        return orderItem.getId();
    }

    public Order createOrder() {
        Order order = new Order();
        orderRepository.save(order);
        return order;
    }

    public int getOrderPrice(Long orderId) {
        List<OrderItem> orderItems = getOrderItems(orderId);
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;

    }

    public int getPayPrice(Long orderId) {
        int payPrice, shippingFee;
        int orderPrice = getOrderPrice(orderId);

        shippingFee = getShippingFee(orderPrice);

        payPrice = orderPrice + shippingFee;
        return payPrice;
    }
    public int getShippingFee(int orderPrice) {
        if(orderPrice < 50000) {
            return 2500;
        }
        return 0;
    }

    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }
}
