package com.cju.shoppingmall.product.service;

import java.util.List;
import java.util.Optional;

import com.cju.shoppingmall.product.controller.ProductRegisterForm;
import com.cju.shoppingmall.product.dto.OptionTypeView;
import com.cju.shoppingmall.product.entity.Category;
import com.cju.shoppingmall.product.entity.Product;

public interface ProductService {
    Product create(Product product);
    Category createCategory(Category category);
    List<Product> getDailyRecommentProducts();
    List<Product> getNewProducts();
    List<Product> getBestProductsLast7Days(int limit);
    Optional<Product> findById(Long id);
    List<OptionTypeView> getOptionViewsByProduct(Long productId);
    Long register(ProductRegisterForm form);
}
