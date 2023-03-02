package com.wardasiu.project.wardasiu.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCart;

    @Column(name = "user_id_user")
    private Long user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    public Cart(final CartItem cartItem) {
        cartItems.add(cartItem);
    }

    public void addItem(CartItem cartItem) {
        boolean itemExists = false;
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(cartItem.getProduct())) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());
                itemExists = true;
                break;
            }
        }
        if (!itemExists) {
            cartItem.setCart(this.getIdCart());
            cartItems.add(cartItem);
        }
    }
}