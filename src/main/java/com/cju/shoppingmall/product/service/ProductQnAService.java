package com.cju.shoppingmall.product.service;

import java.util.List;

import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.entity.ProductQnA;
import com.cju.shoppingmall.product.repository.ProductQnARepository;

import org.springframework.stereotype.Service;

@Service
public class ProductQnAService {

    private final ProductQnARepository qnaRepository;

    public ProductQnAService(ProductQnARepository qnaRepository) {
        this.qnaRepository = qnaRepository;
    }

    public List<ProductQnA> getQnAListByProduct(Product product) {
        return qnaRepository.findByProduct(product);
    }
}
