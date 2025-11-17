package com.cju.shoppingmall.product.service;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<Product> getDailyRecommentProducts() {
        return repository.findTop4ByOrderByCreatedAtDesc();
    }

    @Override
    public List<Product> getNewProducts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        List<Product> newProducts =
                repository.findTop8ByCreatedAtAfterOrderByCreatedAtDesc(sevenDaysAgo);

        if (newProducts.isEmpty()) {
            return repository.findTop8ByOrderByCreatedAtDesc();
        }

        return newProducts;
    }
}
