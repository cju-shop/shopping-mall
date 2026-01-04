package com.cju.shoppingmall.product.dto;

import java.util.List;

public class VariantPriceRequest {
    private Long productId;
    private List<Long> optionValueIds;

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public List<Long> getOptionValueIds() { return optionValueIds; }
    public void setOptionValueIds(List<Long> optionValueIds) { this.optionValueIds = optionValueIds; }
}
