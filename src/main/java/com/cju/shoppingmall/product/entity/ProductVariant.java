package com.cju.shoppingmall.product.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.cju.shoppingmall.member.entity.Member;

import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    @Column(nullable = false)
    private Long price;
    private Double discountRate;
    @Column(nullable = false)
    private Long stockQty;
    @Column(nullable = false)
    private Boolean isActive;
    private String fingerprint;
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Member createdBy;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private Member updatedBy;

    public ProductVariant(
            Product product,
            Long price,
            Double discountRate,
            Long stockQty,
            Boolean isActive,
            String fingerprint,
            Member createdBy
    ) {
        this.product = product;
        this.price = price;
        this.discountRate = discountRate;
        this.stockQty = stockQty;
        this.isActive = isActive;
        this.fingerprint = fingerprint;
        this.createdBy = createdBy;
    }

    protected ProductVariant() {

    }
}
