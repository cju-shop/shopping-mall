package com.cju.shoppingmall.cart.service;

public interface CartService {

    void addToCart(Long productId, Integer quantity);
}