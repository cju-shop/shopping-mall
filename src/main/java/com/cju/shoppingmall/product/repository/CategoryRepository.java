package com.cju.shoppingmall.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cju.shoppingmall.product.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
