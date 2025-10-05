package com.cju.shoppingmall.product.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class ProductVariantOptionId implements Serializable {
    private Long productVariant;
    private Long optionValue;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        ProductVariantOptionId that = (ProductVariantOptionId)o;
        return Objects.equals(productVariant, that.productVariant) && Objects.equals(optionValue,
            that.optionValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productVariant, optionValue);
    }
}