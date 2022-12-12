package kr.com._29cm.homework.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_item_price")
    private int orderItemPrice;
    @Column(name = "count")
    private int count;
    @Transient
    private Item item;

    /**
     * 주문 아이템에 주문하려는 아이템 추가
     * @param item : 주문하려는 아이템
     */
    public void changeItem(Item item) {
        this.orderItemPrice = item.getPrice();
        this.itemId = item.getId();
        this.item = item;
    }

    /**
     * 주문 아이템 주문 수량만큼 재고 감소
     * @return 주문 아이템
     */
    public Item reduceStock() {

        item.changeStock(count * -1);

        return item;
    }

    /**
     * 주문 아이템 주문 수량만큼 재고 복구
     */
    public void addStock() {
        item.changeStock(count);
    }

    /**
     * 주문 아이템 가격  = 상품가격 * 주문 수
     * */
    public int getTotalPrice() {
        return getOrderItemPrice() * getCount();
    }

    /**
     * 상품아이템의 order id 정보 변경
     */
    public void changeOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 상품아이템 주문 내역 출력
     */
    public void printOrderItem() {
        log.info("{} - {}개", item.getName(), count);
    }

}
