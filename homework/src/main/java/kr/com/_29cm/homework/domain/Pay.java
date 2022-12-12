package kr.com._29cm.homework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pay")
@Getter
@Setter
public class Pay {
    @Id
    @GeneratedValue
    @Column(name = "pay_id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "pay_price")
    private int payPrice;



}
