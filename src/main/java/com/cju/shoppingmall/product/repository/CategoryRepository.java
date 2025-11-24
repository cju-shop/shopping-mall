package com.cju.shoppingmall.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cju.shoppingmall.product.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}