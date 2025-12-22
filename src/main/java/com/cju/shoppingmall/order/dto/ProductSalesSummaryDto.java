package com.cju.shoppingmall.order.dto;

public class ProductSalesSummaryDto {

    private final Long productId;
    private final Long totalQuantity;

    public ProductSalesSummaryDto(Long productId, Long totalQuantity) {
        this.productId = productId;
        this.totalQuantity = totalQuantity;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }
}
