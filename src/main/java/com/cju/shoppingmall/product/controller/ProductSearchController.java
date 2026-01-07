package com.cju.shoppingmall.product.controller;

import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.service.ProductSearchService;
import lombok.RequiredArgsConstructor;
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
    public String productList(@RequestParam(required = false) String keyword, Model model) {
        List<Product> products = productSearchService.searchByName(keyword);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);
        return "screens/product_list";
    }

}
