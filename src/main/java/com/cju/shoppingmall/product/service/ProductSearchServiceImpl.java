package com.cju.shoppingmall.product.service;

import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {
    private final ProductRepository productRepository;
    ProductSearchServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll(); // 검색어 없으면 전체
        }
        return productRepository.findByNameContainingIgnoreCase(keyword.trim());
    }
}
