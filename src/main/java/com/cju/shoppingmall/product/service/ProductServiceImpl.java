package com.cju.shoppingmall.product.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cju.shoppingmall.order.repository.OrderDetailRepository;
import org.springframework.stereotype.Service;

import com.cju.shoppingmall.product.entity.Category;
import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.repository.CategoryRepository;
import com.cju.shoppingmall.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository,OrderDetailRepository orderDetailRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.orderDetailRepository = orderDetailRepository;
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

    @Override
    public List<Product> getBestProductsLast7Days(int limit) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<Object[]> rows = orderDetailRepository.findProductSalesLast7Days(sevenDaysAgo);

        if (rows.isEmpty()) {
            return repository.findTop8ByOrderByCreatedAtDesc();
        }

        List<Long> productIds = new ArrayList<>();
        int count = 0;
        for (Object[] row : rows) {
            if (count >= limit) break;
            Long productId = (Long) row[0];
            productIds.add(productId);
            count++;
        }

        List<Product> products = repository.findByIdIn(productIds);

        // id 순서를 유지하기 위해 map으로 정렬
        Map<Long, Product> productMap = new HashMap<>();
        for (Product p : products) {
            productMap.put(p.getId(), p);
        }

        List<Product> bestProducts = new ArrayList<>();
        for (Long id : productIds) {
            Product p = productMap.get(id);
            if (p != null) {
                bestProducts.add(p);
            }
        }
        return bestProducts;
    }
}
