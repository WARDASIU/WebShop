package com.wardasiu.project.wardasiu.entities;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Setter
    @Getter
    @Column(name = "detailed_description")
    private String detailedDescription;

    @Setter
    @Getter
    @Column(name = "sizes")
    private String sizes;

    public Product(final Long id, final String name, final String description, final int price, final int inStock) {
        this.idProducts = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.inStock = inStock;
    }
}