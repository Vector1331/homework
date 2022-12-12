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

    @Column(name = "pay_id")
    private Long payId;

    @Column(name = "total_price")
    private int totalPrice;

    @Column(name = "delivery_fee")
    private int deliveryFee;

    @Transient
    private List<OrderItem> orderItems;


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

    public void changeOrderId() {
        for(OrderItem orderItem : orderItems) {
            orderItem.changeOrderId(this.id);
        }
    }

    public boolean checkOrderItemCount() {
        return orderItems != null && orderItems.size() > 0;
    }

    public void removeOrderItem(Item item) {
        // TODo : list 사라지는지 확인!!@!
        orderItems.remove(item);
    }



    public void printOrderInfo() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        log.info("주문 내역 : ");
        log.info("---------------------------------------");
        for(OrderItem orderItem : orderItems) {
            orderItem.printOrderItem();
        }
        log.info("---------------------------------------");
        log.info("주문 금액 : {} 원", decimalFormat.format(totalPrice) );

        if(deliveryFee > 0){
            log.info("배송비 : {} 원" ,decimalFormat.format(deliveryFee) );
        }

        log.info("---------------------------------------");
        log.info("지불 금액 : {} 원" ,  decimalFormat.format(totalPrice + deliveryFee)) ;
        log.info("---------------------------------------");

    }

    public void calcPrice() {
        calcTotalPrice();
        calcDeliveryFee();
    }
    private void calcTotalPrice() {
        if(checkOrderItemCount()){
            for (OrderItem orderItem : orderItems) {
                totalPrice += orderItem.getOrderPrice() * orderItem.getCount();
            }
        } else {
            totalPrice = 0;
        }
    }

    private int calcDeliveryFee() {
        return totalPrice < 50000 ? 2500 : 0;
    }

   /* public void order() {
        for (int i = 0; i < orderItems.size(); i++) {
            orderItems.get(i).setOrderId(this.id);
        }
    }*/
}
