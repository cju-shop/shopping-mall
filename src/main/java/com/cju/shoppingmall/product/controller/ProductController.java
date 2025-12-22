package com.cju.shoppingmall.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cju.shoppingmall.product.service.ProductService;
import com.cju.shoppingmall.product.service.ProductQnAService;
import com.cju.shoppingmall.product.service.ProductReviewService;
import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.entity.ProductQnA;
import com.cju.shoppingmall.product.entity.ProductReview;
import java.util.List;

@Controller
public class ProductController {

    private final ProductQnAService productQnAService;
    private final ProductReviewService productReviewService;
    private final ProductService productService;

    public ProductController(ProductQnAService productQnAService,
                             ProductReviewService productReviewService,
                             ProductService productService) {
        this.productQnAService = productQnAService;
        this.productReviewService = productReviewService;
        this.productService = productService;
    }

    @GetMapping("/product/detail")
    public String productDetail(@RequestParam("id") int id, Model model) {

        List<Product> products = productService.getNewProducts();

        // 상품을 DB에서 조회, 없으면 예외 발생
        Product product = products.stream()
                .filter(p -> p.getId() != null && p.getId().equals((long) id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: id=" + id));

        // === 예시 리뷰 데이터 ===
        List<ProductReview> reviewList = productReviewService.getReviewsByProduct(product);
        // === 예시 Q&A 데이터 ===
        List<ProductQnA> qnaList = productQnAService.getQnAListByProduct(product);


        model.addAttribute("product", product);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("qnaList", qnaList);

        return "screens/product_detail"; // templates/screens/product_detail.html
    }
}
