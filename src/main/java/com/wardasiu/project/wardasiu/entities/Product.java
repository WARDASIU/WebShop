package com.wardasiu.project.wardasiu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "products")
public
class Product {
    @Setter
    @Getter
    @GeneratedValue
    @Id
    @Column(name = "id_products", nullable = false)
    private Long idProducts;

    @Setter
    @Getter
    @Column(name = "Name", nullable = false)
    private String Name;

    @Setter
    @Getter
    @Column(name = "Description", nullable = false)
    private String Description;

    @Setter
    @Getter
    @Column(name = "Price", nullable = false)
    private int Price;
}
