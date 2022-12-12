package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.*;
import kr.com._29cm.homework.exception.SoldOutException;
import kr.com._29cm.homework.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;


    @Transactional
    public void startOrder() {
        Scanner sc = new Scanner(System.in);
        String idInput = "", cntInput = "";
        Order order = Order.builder().build();

        while(true) {
            System.out.print("상품번호 : ");
            idInput = sc.nextLine();

            System.out.print("수량 : ");
            cntInput = sc.nextLine();

            if(" ".equals(idInput) && " ".equals(cntInput)) {
                List<OrderItem> orderItems = null;
                try{
                    //주문 저장
                    boolean check = order.checkOrderItemCount();
                    if(check) {
                        order.calcPrice();
                        orderRepository.save(order);
                        order.changeOrderId();
                        // 주문 아이템 저장
                        orderItems = order.getOrderItems();
                        orderItemRepository.saveAll(orderItems);

                        order.printOrderInfo();
                    } else {
                        log.info("상품을 담아주세요");
                        continue;
                    }
                } catch (Exception ex) {
                    log.error("주문에 실패했습니다. 다시 주문해주세요");

                    // 주문 실패했으므로 item 재고 다시 add & save
                    List<Item> items = orderItems.stream()
                            .map(m -> {
                                m.addStock();
                                return m.getItem();
                            }).collect(Collectors.toList());
                    itemRepository.saveAll(items);
                }

                break;
            } else {
                if (" ".equals(idInput) || " ".equals(cntInput)) {
                    log.info("잘못된 주문정보입니다. 다시 입력해주세요");
                } else {
                    if (idInput.matches("^[0-9]*$") && cntInput.matches("^[0-9]*$")) {
                        OrderItem orderItem = order.addOrderItem(idInput, cntInput);
                        // item Id  아이템 조회 > 그 아이템 OrderItem set
                        Optional<Item> foundItem = itemRepository.findById(Long.valueOf(idInput));
                        if(Optional.<Item>empty() == foundItem) {
                            log.info("해당 상품은 없습니다. 다시입력해주세요");
                            continue;
                        }
                        Item item = foundItem.get();

                        orderItem.changeItem(item);

                        try {
                            orderItem.removeStock();
                            itemRepository.save(item);
                        } catch (SoldOutException e) {
                            order.removeOrderItem(item);
                            log.error(e.getMessage());
                        }

                    } else {
                        log.info("잘못된 주문정보입니다. 상품과 수량은 숫자만 입력됩니다.");
                    }
                }
            }
        }
    }


    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }


    /**
     * 비용 계산 : 주문비용, 지불비용, 배송비
     * **/

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

}
