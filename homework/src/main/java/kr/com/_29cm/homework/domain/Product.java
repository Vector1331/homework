package kr.com._29cm.homework.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "pid")
    private Long id;

    private String name;

    private int price;

    private int stock;


}
