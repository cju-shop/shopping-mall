package com.cju.shoppingmall.order.entity;

import com.cju.shoppingmall.member.entity.Member;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @Column(nullable = false)
    private Integer totalCount;
    @Column(nullable = false)
    private Long totalPrice;
    @Column(nullable = false)
    private Long subTotalPrice;
    @Column(nullable = false)
    private Long discountPrice;
    @Column(nullable = false)
    private Long shippingPrice;
    @Column(nullable = false)
    private OrderStatus status;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

}
