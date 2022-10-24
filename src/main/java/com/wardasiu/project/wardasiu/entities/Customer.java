package com.wardasiu.project.wardasiu.entities;

import javax.persistence.*;

@Entity(name = "customer")
public class Customer {
    @Id
    @Column(name = "id_customer", nullable = false)
    @GeneratedValue
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

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(final String postcode) {
        this.postcode = postcode;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long id) {
        this.idCustomer = id;
    }
}
