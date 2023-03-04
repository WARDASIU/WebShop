package com.wardasiu.project.wardasiu.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue
    private Long id_cart_item;

    @Column(name = "id_cart")
    private Long cart;

    @Column(name = "id_products")
    private Long product;

    @Column(name = "quantity")
    private Integer quantity;
}