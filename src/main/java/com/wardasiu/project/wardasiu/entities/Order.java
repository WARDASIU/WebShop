package com.wardasiu.project.wardasiu.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "order")
@NoArgsConstructor
public class Order {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_order", nullable = false)
    private Long idOrder;

    @Column(name = "id_user")
    private Long idUser;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "delivery_method")
    private String deliveryMethod;

    @Column(name = "post_code")
    private String postCode;

    @Column(name = "city")
    private String city;

    @OneToMany(mappedBy = "idOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public Order(final List<OrderItem> items) {

    }
}
