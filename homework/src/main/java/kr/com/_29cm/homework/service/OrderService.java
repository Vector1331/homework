package kr.com._29cm.homework.service;

import kr.com._29cm.homework.domain.*;
import kr.com._29cm.homework.exception.SoldOutException;
import kr.com._29cm.homework.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
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
    private final PayRepository payRepository;


    /**
     * 주문 시작, 상품번호와 수량 입력받음
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

                try{
                    //주문 저장
                    boolean check = order.checkOrderItemCount();
                    if(check) {
                        this.createOrder(order);
                    } else {
                        log.info("상품을 담아주세요");
                        continue;
                    }
                } catch (Exception ex) {
                    log.error("주문에 실패했습니다. 다시 주문해주세요");

                    List<OrderItem> orderItems = order.getOrderItems();
                    if(orderItems != null) {
                        // 주문 실패했으므로 item 재고 다시 add & save
                        List<Item> items = orderItems.stream()
                                .map(m -> {
                                    m.addStock();
                                    return m.getItem();
                                }).collect(Collectors.toList());
                        itemRepository.saveAll(items);
                    }
                }

                break;
            } else {
                if (" ".equals(idInput) || " ".equals(cntInput)) {
                    log.info("잘못된 주문정보입니다. 다시 입력해주세요");
                } else {
                    if (idInput.matches("^[0-9]*$") && cntInput.matches("^[0-9]*$")) {

                        // item Id  아이템 조회 > 그 아이템 OrderItem set
                        Optional<Item> foundItem = itemRepository.findById(Long.valueOf(idInput));
                        if(Optional.<Item>empty() == foundItem) {
                            log.info("해당 상품은 없습니다. 다시입력해주세요");

                            continue;
                        }
                        OrderItem orderItem = order.addOrderItem(idInput, cntInput);
                        Item item = foundItem.get();

                        orderItem.changeItem(item);

                        try {
                            this.reduceItemStock(orderItem);
                        } catch (SoldOutException e) {
                            order.removeOrderItem(orderItem);
                            order.resetItemStock();
                            List<OrderItem> orderItemList = order.getOrderItems();
                            for(OrderItem orderItem1 : orderItemList) {
                                itemRepository.save(orderItem1.getItem());
                            }
                            log.error(e.getMessage());

                            break;
                        }

                    } else {
                        log.info("잘못된 주문정보입니다. 상품과 수량은 숫자만 입력됩니다.");
                    }
                }
            }
        }
    }

    /**
     * 주문 생성
     * @param order : 생성할 주문 정보
     */
    private void createOrder(Order order) {
        order.calcPrice();
        orderRepository.save(order);
        order.changeOrderId();
        // 주문 아이템 저장
        List<OrderItem> orderItems = order.getOrderItems();
        orderItemRepository.saveAll(orderItems);

        // 결제 저장
        Pay pay = order.payOrder();
        payRepository.save(pay);

        order.printOrderInfo();
    }

    /**
     * 주문 아이템 재고 차감
     *
     * @param orderItem : 차감할 주문 아이템
     */
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void reduceItemStock(OrderItem orderItem) {

        Long itemId = orderItem.getItemId();
        int qty = orderItem.getCount();
        orderItem.reduceStock();

        itemRepository.reduceStock(itemId, qty);

        Optional<Item> byId = itemRepository.findById(itemId);
        Item item = byId.get();
        log.info("########### {}", item.getStock());

        if (item.getStock() < 0) {
            throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
        }
    }
}
