package com.cju.shoppingmall.cart.repository;

import com.cju.shoppingmall.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
