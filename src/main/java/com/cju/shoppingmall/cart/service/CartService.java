package com.cju.shoppingmall.cart.service;

public interface CartService {

    void addToCart(Long productVariantId, Long qty, Long memberId);
}