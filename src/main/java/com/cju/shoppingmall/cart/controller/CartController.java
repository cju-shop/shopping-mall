package com.cju.shoppingmall.cart.controller;

import com.cju.shoppingmall.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @RequestParam("productVariantId") Long productVariantId,
            @RequestParam("quantity") Long quantity
    ) {
        Long memberId = 1L; // 임시(로그인 전까지)
        cartService.addToCart(productVariantId, quantity, memberId);
        return ResponseEntity.ok().build();
    }
}
