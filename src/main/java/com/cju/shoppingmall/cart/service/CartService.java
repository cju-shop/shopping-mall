package com.cju.shoppingmall.cart.service;

import com.cju.shoppingmall.cart.dto.CartResponse;

import java.util.List;

public interface CartService {

    void addToCart(Long productVariantId, Long qty, Long memberId);
    void addToCartByOptions(Long productId, List<Long> optionValueIds, Long qty, Long memberId);
    CartResponse getCart(Long memberId);
    void updateQuantity(Long cartDetailId, Long qty, Long memberId);
    void removeItem(Long cartDetailId, Long memberId);
    void removeItems(List<Long> cartDetailIds, Long memberId);

}