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

        model.addAttribute("product", product);
        model.addAttribute("reviews", java.util.List.of(review1, review2));

        return "screens/product_detail"; // templates/screens/product_detail.html
    }
}
