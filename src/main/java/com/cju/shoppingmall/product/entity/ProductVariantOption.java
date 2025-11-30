package com.cju.shoppingmall.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(ProductVariantOptionId.class)
public class ProductVariantOption {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_value_id")
    private OptionValue optionValue;


    public ProductVariantOption(ProductVariant productVariant, OptionValue optionValue) {
        this.productVariant = productVariant;
        this.optionValue = optionValue;
    }

    public ProductVariantOption() {
    }
}
