package com.cju.shoppingmall.cart.entity;

import com.cju.shoppingmall.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "cart")
@Getter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    @Column(nullable = false)
    private Integer totalCount;
    @Column(nullable = false)
    private Long subtotalAmount;
    @Column
    private Long discountAmount;
    @Column(nullable = false)
    private Long totalAmount;

    public Cart(Member member, Integer totalCount, Long subtotalAmount, Long discountAmount, Long totalAmount) {
        this.member = member;
        this.totalCount = totalCount;
        this.subtotalAmount = subtotalAmount;
        this.discountAmount = discountAmount;
        this.totalAmount = totalAmount;
    }

    public Cart() {
    }
}
