package com.cju.shoppingmall.member.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true)
    String username;
    @Column(nullable = false, unique = true)
    String nickname;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    String phone;
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false)
    MemberRole role;
    @CreatedDate
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}