package kr.com._29cm.homework.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "pay")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
