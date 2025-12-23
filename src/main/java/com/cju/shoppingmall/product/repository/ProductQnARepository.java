package com.cju.shoppingmall.product.repository;

import com.cju.shoppingmall.product.entity.ProductQnA;
import com.cju.shoppingmall.product.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductQnARepository extends JpaRepository<ProductQnA, Long> {
    List<ProductQnA> findByProduct(Product product);  // 특정 상품의 Q&A 목록 조회
}
