package com.cju.shoppingmall.cart.dto;

public class CartItemResponse {
    private Long cartDetailId;
    private Long productId;
    private String productName;
    private String thumbnail;
    private Long variantId;
    private String optionText;
    private Long unitPrice;
    private Long qty;
    private Long totalPrice;

    public CartItemResponse(
            Long cartDetailId,
            Long productId,
            String productName,
            String thumbnail,
            Long variantId,
            String optionText,
            Long unitPrice,
            Long qty
    ) {
        this.cartDetailId = cartDetailId;
        this.productId = productId;
        this.productName = productName;
        this.thumbnail = thumbnail;
        this.variantId = variantId;
        this.optionText = optionText;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.totalPrice = unitPrice * qty;
    }

    public Long getCartDetailId() { return cartDetailId; }
    public Long getProductId() { return productId; }
    public String getProductName() { return productName; }
    public String getThumbnail() { return thumbnail; }
    public Long getVariantId() { return variantId; }
    public String getOptionText() { return optionText; }
    public Long getUnitPrice() { return unitPrice; }
    public Long getQty() { return qty; }
    public Long getTotalPrice() { return totalPrice; }
}
