package com.cju.shoppingmall.product.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.cju.shoppingmall.member.entity.Member;

import lombok.Getter;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column
    private String thumbnail;
    @Column(nullable = false)
    private Long basePrice;
    @Column
    private Double discountRate;
    @Column(precision = 2, scale = 1)
    private BigDecimal rating;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
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
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<ProductVariant> variants;

    public Product(String name, String description, String thumbnail, Long basePrice, Category category,
        Member createdBy) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
        this.basePrice = basePrice;
        this.category = category;
        this.createdBy = createdBy;
    }

    protected Product() {

    }
}
