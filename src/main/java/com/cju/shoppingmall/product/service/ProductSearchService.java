package com.cju.shoppingmall.product.service;

import com.cju.shoppingmall.product.entity.Product;

import java.util.List;

public interface ProductSearchService {
    List<Product> searchByName(String keyword);
}
