package com.cju.shoppingmall.product.dto;

public class VariantPriceResponse {
    private Long variantId;
    private Long price;

    public VariantPriceResponse(Long variantId, Long price) {
        this.variantId = variantId;
        this.price = price;
    }

    public Long getVariantId() { return variantId; }
    public Long getPrice() { return price; }
}
