package com.cju.shoppingmall.product.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class ProductOptionId implements Serializable {
    private Long product;
    private Long optionType;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        ProductOptionId that = (ProductOptionId)o;
        return Objects.equals(product, that.product) && Objects.equals(optionType, that.optionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, optionType);
    }
}