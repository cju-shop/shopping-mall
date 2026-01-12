package com.cju.shoppingmall.product.service;

import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductSearchServiceImpl implements ProductSearchService {
    private final ProductRepository productRepository;
    ProductSearchServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> searchByName(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isBlank()) {
            return productRepository.findAll(pageable);
        }
        return productRepository.findByNameContainingIgnoreCase(keyword.trim(), pageable);
    }
}
