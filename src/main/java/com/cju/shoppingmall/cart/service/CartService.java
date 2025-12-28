package com.cju.shoppingmall.cart.service;

public interface CartService {

    void addToCart(Long productVariantId, Long qty, Long memberId);
    void addToCartByOptions(Long productId, java.util.List<Long> optionValueIds, Long qty, Long memberId);

}