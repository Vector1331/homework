package kr.com._29cm.homework.domain;

import kr.com._29cm.homework.exception.SoldOutException;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    private Long id;

    private String name;

    private int price;
    private int stock;

    /**
     * 재고 수량 감소
     * */
    public void changeStock(int cnt) {
        int leftStock = this.stock + cnt;

        if(leftStock < 0) {
            throw new SoldOutException("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
        }

        this.stock = leftStock;
    }
}
