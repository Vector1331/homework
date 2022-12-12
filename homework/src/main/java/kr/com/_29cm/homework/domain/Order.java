package kr.com._29cm.homework.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_price")
    private int orderPrice;

    @Column(name = "delivery_fee")
    private int deliveryFee;

    @Transient
    private List<OrderItem> orderItems;

    @Transient
    private Pay pay;


    /**
     * 주문 상품과 수량 추가
     * @param idx : 주문상품 ID
     * @param count : 주문상품 개수
     * @return : 추가된 상품
     */
    public OrderItem addOrderItem(String idx, String count){
        Long itemId = Long.valueOf(idx);
        int orderItemCount = Integer.valueOf(count);

        OrderItem orderItem = OrderItem.builder()
                .itemId(itemId)
                .count(orderItemCount)
                .build();

        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }

        orderItems.add(orderItem);

        return orderItem;
    }

    /**
     * 상품아이템들의 order id 정보 변경
     */
    public void changeOrderId() {
        for(OrderItem orderItem : orderItems) {
            orderItem.changeOrderId(this.id);
        }
    }

    /**
     * space + ENTER 로 주문시 상품의 주문 정상 확인
     * @return 정상여부
     */
    public boolean checkOrderItemCount() {
        return orderItems != null && orderItems.size() > 0;
    }

    /**
     * 주문아이템 제거
     * @param item : 제거하려는 아이템
     */
    public void removeOrderItem(Item item) {
        orderItems.remove(item);
    }


    /**
     * 주문 내역 출력 : 주문금액, 배송비(있을 경우), 지불금액
     */
    public void printOrderInfo() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        log.info("주문 내역 : ");
        log.info("---------------------------------------");

        for(OrderItem orderItem : orderItems) {
            orderItem.printOrderItem();
        }

        log.info("---------------------------------------");
        log.info("주문 금액 : {} 원", decimalFormat.format(orderPrice) );

        if(deliveryFee > 0){
            log.info("배송비 : {} 원" ,decimalFormat.format(deliveryFee) );
        }

        log.info("---------------------------------------");
        log.info("지불 금액 : {} 원" ,  decimalFormat.format(orderPrice + deliveryFee)) ;
        log.info("---------------------------------------");
    }

    /**
     * 주문 금액, 배송비 계산
     */
    public void calcPrice() {
        calcTotalPrice();
        calcDeliveryFee();
    }

    /**
     * 주문 금액 계산 : 주문아이템의 각 상품금액 * 주문개수
     */
    private void calcTotalPrice() {
        if(checkOrderItemCount()){
            for (OrderItem orderItem : orderItems) {
                orderPrice += orderItem.getOrderItemPrice() * orderItem.getCount();
            }
        } else {
            orderPrice = 0;
        }
    }

    /**
     * 베송비 계산 : 5만원 미만 2500원 부과
     * @return 비송비
     */
    private int calcDeliveryFee() {
        return orderPrice < 50000 ? 2500 : 0;
    }

    /**
     * 결제 진행
     * @return 결제정보
     */
    public Pay payOrder() {
        Pay pay = Pay.builder()
                .orderId(id)
                .payPrice(orderPrice + deliveryFee)
                .build();

        return pay;
    }
}
