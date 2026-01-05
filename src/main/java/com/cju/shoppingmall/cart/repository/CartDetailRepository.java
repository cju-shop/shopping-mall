package com.cju.shoppingmall.cart.repository;

import com.cju.shoppingmall.cart.entity.Cart;
import com.cju.shoppingmall.cart.entity.CartDetail;
import com.cju.shoppingmall.product.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    Optional<CartDetail> findByCartAndProductVariant(Cart cart, ProductVariant productVariant);

    List<CartDetail> findByCart(Cart cart);
}
