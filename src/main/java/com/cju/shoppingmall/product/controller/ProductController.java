package com.cju.shoppingmall.product.controller;

import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.entity.ProductQnA;
import com.cju.shoppingmall.product.entity.ProductReview;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cju.shoppingmall.product.service.ProductService;
import com.cju.shoppingmall.product.service.ProductQnAService;
import com.cju.shoppingmall.product.service.ProductReviewService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String productDetail(@RequestParam("id") long id, Model model) {

        Product product = productService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: id=" + id));
        model.addAttribute("optionTypes", productService.getOptionViewsByProduct(product.getId()));
        // === 예시 리뷰 데이터 ===
        List<ProductReview> reviewList = productReviewService.getReviewsByProduct(product);
        // === 예시 Q&A 데이터 ===
        List<ProductQnA> qnaList = productQnAService.getQnAListByProduct(product);


        // === 예시 Q&A 데이터 ===
        Map<String, Object> qna1 = new HashMap<>();
        qna1.put("question", "질문 제목입니다.");
        qna1.put("questionDetail", "질문 내용입니다.");
        qna1.put("answer", "답변입니다.");
        qna1.put("hasAnswer", true);  // 답변이 있는지 여부

        Map<String, Object> qna2 = new HashMap<>();
        qna2.put("question", "교환/환불 가능한가요?");
        qna2.put("questionDetail", "제곧내");
        qna2.put("answer", "아뇨, 교환/환불은 불가능합니다.");
        qna2.put("hasAnswer", true);

        Map<String, Object> qna3 = new HashMap<>();
        qna3.put("question", "일주일째 배송이 오지 않아요");
        qna3.put("questionDetail", "주문한지 일주일이 지났는데, 배송은 커녕 송장번호도 받지 못했어요. 혹시 언제쯤 오나요?");
        qna3.put("answer", null);  // 답변 대기 중
        qna3.put("hasAnswer", false);


        model.addAttribute("product", product);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("qnaList", qnaList);

        return "screens/product_detail"; // templates/screens/product_detail.html
    }
}
