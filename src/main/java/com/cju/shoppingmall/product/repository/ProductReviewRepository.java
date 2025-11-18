package com.cju.shoppingmall.product.repository;

import com.cju.shoppingmall.product.entity.ProductReview;
import com.cju.shoppingmall.product.entity.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByProduct(Product product);
}
