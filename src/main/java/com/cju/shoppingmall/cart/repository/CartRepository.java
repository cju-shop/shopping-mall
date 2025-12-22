package com.cju.shoppingmall.cart.repository;

import com.cju.shoppingmall.cart.entity.Cart;
import com.cju.shoppingmall.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMember(Member member);
}
