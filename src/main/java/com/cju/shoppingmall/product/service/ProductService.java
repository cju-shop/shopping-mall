package com.cju.shoppingmall.product.service;

import java.util.List;

import com.cju.shoppingmall.product.controller.ProductRegisterForm;
import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.product.entity.Category;
import com.cju.shoppingmall.product.entity.Product;

public interface ProductService {
    Product create(Product product);

    Category createCategory(Category category);

    List<Product> getNewProducts();

    Long register(ProductRegisterForm form, Member createdBy);
}
