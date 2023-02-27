package com.wardasiu.project.wardasiu.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
public class Product {
    @Setter
    @Getter
    @GeneratedValue
    @Id
    @Column(name = "id_products", nullable = false)
    private Long idProducts;

    @Setter
    @Getter
    @Column(name = "Name", nullable = false)
    private String name;

    @Setter
    @Getter
    @Column(name = "Description", nullable = false)
    private String description;

    @Setter
    @Getter
    @Column(name = "Price", nullable = false)
    private int price;

    @Setter
    @Getter
    @Column(name = "in_stock")
    private int inStock;

    public Product(final Long id, final String name, final String description, final int price, final int inStock) {
        this.idProducts = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inStock = inStock;
    }

}
