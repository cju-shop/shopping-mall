package com.cju.shoppingmall.delivery.entity;

import java.time.LocalDateTime;

import com.cju.shoppingmall.order.entity.Order;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;
    @Column(nullable = false)
    private DeliveryStatus status;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    @Column(nullable = false)
    private String receiverName;
    @Column(nullable = false)
    private String receiverPhone;
    @Column(nullable = false)
    private String zipCode;
    @Column(nullable = false)
    private String address1;
    @Column(nullable = false)
    private String address2;
    private String preference;
}
