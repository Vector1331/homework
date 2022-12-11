package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.*;
import kr.com._29cm.homework.exception.OrderException;
import kr.com._29cm.homework.exception.SoldOutException;
import kr.com._29cm.homework.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
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

    /**
     * 주문 생성
     * */
    /*@Transactional
    public Order createOrder() {
        Order order = new Order();
        orderRepository.save(order);
        return order;
    }*/
    /*public Long order(Long productId, int cnt ){
        Order order = createOrder();
        return order.getId();

    }*/
    /**
     * 주문 아이템 생성
     */
/*    @Transactional
    public void createOrderItem(Long pid, Long oid, int cnt) {
        try {
            Item item = productService.findById(pid);
            OrderItem orderItem = OrderItem.createOrderItem(pid, oid, item.getPrice(), cnt);
            try{
                item.removeStock(cnt);
                orderItemRepository.save(orderItem);
                System.out.print(orderItem.getId());
            }
            catch (SoldOutException ex){
                System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
            }
        }
        catch (OrderException e) {
            System.out.println("OrderException 발생, 상품이 존재하지 않습니다. ");
        }
        //return orderItem;
    }
    */

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

                    // 주문 실패했으므로 item 재고 다시 add하고 save
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
                        Item item = itemRepository.findById(Long.valueOf(idInput));
                        if(item == null) {
                            log.info("해당 상품은 없습니다. 다시입력해주세요");
                            continue;
                        }

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
