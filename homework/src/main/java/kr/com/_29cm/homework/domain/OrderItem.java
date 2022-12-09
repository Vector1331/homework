package kr.com._29cm.homework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "product_id")
    private Long pid;

    @Column(name = "order_id")
    private Long orderId;

    private int orderPrice;
    private int count;


    /**
     * 주문 아이템 가격  = 상품가격 * 주문 수
     * */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
