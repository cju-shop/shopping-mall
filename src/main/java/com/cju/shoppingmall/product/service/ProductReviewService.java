package com.cju.shoppingmall.product.service;

import java.util.List;

import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.entity.ProductReview;
import com.cju.shoppingmall.product.repository.ProductReviewRepository;

import org.springframework.stereotype.Service;

@Service
public class ProductReviewService {

    private final ProductReviewRepository reviewRepository;

    public ProductReviewService(ProductReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<ProductReview> getReviewsByProduct(Product product) {
        return reviewRepository.findByProduct(product);
    }
}
