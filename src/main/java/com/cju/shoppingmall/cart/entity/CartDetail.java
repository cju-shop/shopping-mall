package com.cju.shoppingmall.cart.entity;

import com.cju.shoppingmall.product.entity.ProductVariant;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @Column
    private Long qty;
}
