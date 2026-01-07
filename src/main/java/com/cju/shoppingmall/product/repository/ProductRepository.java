package com.cju.shoppingmall.product.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cju.shoppingmall.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByIdIn(List<Long> ids);
    List<Product> findTop4ByOrderByCreatedAtDesc();
    List<Product> findTop8ByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime date);
    List<Product> findTop8ByOrderByCreatedAtDesc();
    List<Product> findByNameContainingIgnoreCase(String keyword);
}
