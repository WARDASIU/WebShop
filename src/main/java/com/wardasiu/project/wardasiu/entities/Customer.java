package com.wardasiu.project.wardasiu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "customer")
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "id_customer", nullable = false)
    private Long idCustomer;

    @Setter
    @Getter
    @Column(name = "Name")
    private String name;

    @Setter
    @Getter
    @Column(name = "Surname", nullable = false)
    private String surname;

    @Setter
    @Getter
    @Column(name = "Email", nullable = false)
    private String email;

    @Setter
    @Getter
    @Column(name = "Phone_number", nullable = false)
    private int phoneNumber;

    @Setter
    @Getter
    @Column(name = "Address", nullable = false)
    private String address;

    @Setter
    @Getter
    @Column(name = "Postcode", nullable = false)
    private String postcode;
}
