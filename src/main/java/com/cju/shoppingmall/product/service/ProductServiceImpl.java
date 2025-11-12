package com.cju.shoppingmall.product.service;

import java.time.LocalDateTime;
import java.util.List;

import com.cju.shoppingmall.controller.ProductRegisterForm;
import org.springframework.stereotype.Service;

import com.cju.shoppingmall.product.entity.Category;
import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.repository.CategoryRepository;
import com.cju.shoppingmall.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product create(Product product) {
        Product save = repository.save(product);
        return save;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Product> getNewProducts() {
        return repository.findTop4ByOrderByCreatedAtDesc();
    }

    public Long register(ProductRegisterForm form, String createdBy){
        return null;
    }
}