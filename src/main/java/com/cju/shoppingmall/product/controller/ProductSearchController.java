package com.cju.shoppingmall.product.controller;

import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.service.ProductSearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductSearchController {

    private final ProductSearchService productSearchService;

    public ProductSearchController(ProductSearchService productSearchService) {
        this.productSearchService = productSearchService;
    }

    @GetMapping("/products")
    public String productList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "rating")
                        .and(Sort.by(Sort.Direction.DESC, "createdAt"))
        );

        Page<Product> result = productSearchService.searchByName(keyword, pageable);

        model.addAttribute("page", result);                 // 페이지 정보 전체
        model.addAttribute("products", result.getContent()); // 현재 페이지 상품 리스트
        model.addAttribute("keyword", keyword);             // 검색어 유지용
        model.addAttribute("size", size);                   // 페이지 사이즈 유지용
        return "screens/product_list";
    }

}
