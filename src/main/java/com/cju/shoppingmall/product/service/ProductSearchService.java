package com.cju.shoppingmall.product.service;

import com.cju.shoppingmall.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearchService {
    Page<Product> searchByName(String keyword, Pageable pageable);
}
