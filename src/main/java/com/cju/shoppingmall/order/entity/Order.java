package com.cju.shoppingmall.order.entity;

import com.cju.shoppingmall.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "orders")
@Getter
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

}
