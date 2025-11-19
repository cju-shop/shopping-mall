package com.cju.shoppingmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductController {

    @GetMapping("/product/detail")
    public String productDetail(@RequestParam("id") int id, Model model) {

        // === 더미 상품 데이터 ===
        Map<String, Object> product = new HashMap<>();
        product.put("id", id);
        product.put("name", "라이트 니트 카디건 #" + id);
        product.put("price", 123440);
        product.put("img", "https://picsum.photos/seed/" + id + "/800/600");
        product.put("description", "포근하고 가벼운 니트 카디건입니다. 부드러운 소재로 일상복이나 데일리룩에 잘 어울립니다.");
        product.put("category", "카디건 / 니트");
        product.put("stock", 24);
        product.put("delivery", "CJ대한통운 / 2~3일 내 배송");

        // === 예시 리뷰 데이터 ===
        Map<String, String> review1 = Map.of(
                "user", "yujin***",
                "content", "생각보다 재질이 너무 좋아요! 사이즈도 딱 맞습니다 :)"
        );
        Map<String, String> review2 = Map.of(
                "user", "junho**",
                "content", "색감이 사진이랑 거의 똑같고 배송 빨랐어요!"
        );

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
        model.addAttribute("reviews", java.util.List.of(review1, review2));
        model.addAttribute("qnaList", java.util.List.of(qna1, qna2, qna3));

        return "screens/product_detail"; // templates/screens/product_detail.html
    }
}
