package com.cju.shoppingmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        List<Map<String, Object>> todayRecommendProducts = new ArrayList<>();
        List<Map<String, Object>> bestProducts = new ArrayList<>();
        List<Map<String, Object>> newProducts = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Map<String, Object> p = new HashMap<>();
            p.put("id", i);
            p.put("name", "라이트 니트 카디건 #" + i);
            p.put("price", 39900 + (i % 5) * 1000);
            p.put("img", "https://picsum.photos/seed/" + i + "/600/450");
            todayRecommendProducts.add(p);
        }
        for (int i = 1; i <= 8; i++) {
            Map<String, Object> p = new HashMap<>();
            p.put("id", i);
            p.put("name", "라이트 니트 카디건 #" + i);
            p.put("price", 39900 + (i % 5) * 1000);
            p.put("img", "https://picsum.photos/seed/" + i + "/600/450");
            bestProducts.add(p);
        }
        for (int i = 1; i <= 8; i++) {
            Map<String, Object> p = new HashMap<>();
            p.put("id", i);
            p.put("name", "라이트 니트 카디건 #" + i);
            p.put("price", 39900 + (i % 5) * 1000);
            p.put("img", "https://picsum.photos/seed/" + i + "/600/450");
            newProducts.add(p);
        }

        List<Map<String, String>> banners = new ArrayList<>();
        banners.add(Map.of(
                "img", "/img/ad1.jpg",
                "href", "https://example.com/ad1",
                "alt", "제휴사 광고 1"
        ));
        banners.add(Map.of(
                "img", "/img/ad2.jpg",
                "href", "https://example.com/ad2",
                "alt", "제휴사 광고 2"
        ));
        banners.add(Map.of(
                "img", "/img/ad3.jpg",
                "href", "https://example.com/ad3",
                "alt", "제휴사 광고 3"
        ));

        //제품광고(ad) 리스트
        List<Map<String, String>> ads = new ArrayList<>();
        ads.add(Map.of(
                "img", "/img/ad1.jpg",
                "href", "https://example.com/productAd1",
                "alt", "제품 광고 1"));
        ads.add(Map.of(
                "img", "/img/ad2.jpg",
                "href", "https://example.com/productAd2",
                "alt", "제품 광고 2"));
        ads.add(Map.of(
                "img", "/img/ad3.jpg",
                "href", "https://example.com/productAd3",
                "alt", "제품 광고 2"));

        model.addAttribute("banners", banners);
        model.addAttribute("ads", ads);

        model.addAttribute("todayRecommendProducts", todayRecommendProducts);
        model.addAttribute("bestProducts", bestProducts);
        model.addAttribute("newProducts", newProducts);
        return "screens/home";
    }

}
