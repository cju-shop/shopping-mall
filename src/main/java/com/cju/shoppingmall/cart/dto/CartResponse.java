package com.cju.shoppingmall.cart.dto;

import java.util.List;

public class CartResponse {
    private Long cartId;
    private Long totalCount;
    private Long subtotalAmount;
    private Long discountAmount;
    private Long totalAmount;
    private List<CartItemResponse> items;

    public CartResponse(
            Long cartId,
            Long totalCount,
            Long subtotalAmount,
            Long discountAmount,
            Long totalAmount,
            List<CartItemResponse> items
    ) {
        this.cartId = cartId;
        this.totalCount = totalCount;
        this.subtotalAmount = subtotalAmount;
        this.discountAmount = discountAmount;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Long getCartId() { return cartId; }
    public Long getTotalCount() { return totalCount; }
    public Long getSubtotalAmount() { return subtotalAmount; }
    public Long getDiscountAmount() { return discountAmount; }
    public Long getTotalAmount() { return totalAmount; }
    public List<CartItemResponse> getItems() { return items; }
}
