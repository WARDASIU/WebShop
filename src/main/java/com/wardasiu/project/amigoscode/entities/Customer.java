package com.wardasiu.project.amigoscode.entities;

import javax.persistence.*;

@Entity(name = "customer")
public class Customer {
    @Id
    @Column(name = "id_customer", nullable = false)
    private Long idCustomer;

    @Column(name = "Name")
    private String name;

    @Column(name = "Surname", nullable = false)
    private String surname;

    @Column(name = "Email", nullable = false)
    private String email;

    @Column(name = "Phone_number", nullable = false)
    private int phoneNumber;

    @Column(name = "Address", nullable = false)
    private String address;

    @Column(name = "Postcode", nullable = false)
    private String postcode;

    String getEmail() {
        return email;
    }

    void setEmail(final String email) {
        this.email = email;
    }

    String getSurname() {
        return surname;
    }

    void setSurname(final String surname) {
        this.surname = surname;
    }

    int getPhoneNumber() {
        return phoneNumber;
    }

    void setPhoneNumber(final int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    String getAddress() {
        return address;
    }

    void setAddress(final String address) {
        this.address = address;
    }

    String getPostcode() {
        return postcode;
    }

    void setPostcode(final String postcode) {
        this.postcode = postcode;
    }

    String getName() {
        return name;
    }

    void setName(final String name) {
        this.name = name;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long id) {
        this.idCustomer = id;
    }
}
