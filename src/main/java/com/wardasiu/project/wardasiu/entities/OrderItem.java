package com.wardasiu.project.wardasiu.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Column(name = "id_order_item")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idOrderItem;

    @Column(name = "quantity")
    private int quantity;


    @Column(name = "id_order")
    private Long idOrder;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;
}
