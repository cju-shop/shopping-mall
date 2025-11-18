package com.cju.shoppingmall.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.member.entity.MemberRole;
import com.cju.shoppingmall.member.service.MemberService;
import com.cju.shoppingmall.product.entity.Category;
import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.entity.AffiliateAd;

import com.cju.shoppingmall.product.service.ProductService;
import com.cju.shoppingmall.product.service.AffiliateAdService;

@Controller
public class HomeController {

    private final ProductService productService;
    private final MemberService memberService;
    private final AffiliateAdService affiliateAdService;

    public HomeController(ProductService productService, MemberService memberService, AffiliateAdService affiliateAdService) {
        this.productService = productService;
        this.memberService = memberService;
        this.affiliateAdService = affiliateAdService;
    }


    @GetMapping("/create/home-data")
    public String prepareHomeData() {
        Member createdMember = memberService.join(
            new Member("admin", "관리자", "관리자", "noEncrypted",
                "01000000000", "admin@shopping.com", MemberRole.ADMIN));
        Category category = productService.createCategory(new Category(null, "상품", true));
        for (int i = 0; i < 10; i++) {
            String productName = "Test Item " + i;
            String description = "Test item 입니다.";
            String thumbnail = "https://picsum.photos/seed/" + i + "/600/450";
            Product generatedProduct = new Product(productName, description, thumbnail, 10000L, category,
                createdMember);
            productService.create(generatedProduct);
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Product> dailyRecommentProducts = productService.getNewProducts();
        List<Map<String, Object>> bestProducts = new ArrayList<>();
        List<Map<String, Object>> newProducts = new ArrayList<>();

        for (int i = 1; i <= 8; i++) {
            Map<String, Object> p = new HashMap<>();
            p.put("id", i);
            p.put("name", "라이트 니트 카디건 #" + i);
            p.put("basePrice", 39900 + (i % 5) * 1000);
            p.put("thumbnail", "https://picsum.photos/seed/" + i + "/600/450");
            bestProducts.add(p);
        }
        for (int i = 1; i <= 8; i++) {
            Map<String, Object> p = new HashMap<>();
            p.put("id", i);
            p.put("name", "라이트 니트 카디건 #" + i);
            p.put("basePrice", 39900 + (i % 5) * 1000);
            p.put("thumbnail", "https://picsum.photos/seed/" + i + "/600/450");
            newProducts.add(p);
        }

        List<AffiliateAd> banners = affiliateAdService.getActiveAds();

        //제품광고(ad) 리스트 (상단)
        List<Map<String, String>> ads = new ArrayList<>();
        ads.add(Map.of("img", "/img/ad1.jpg", "href", "https://example.com/productAd1", "alt", "제품 광고 1"));
        ads.add(Map.of("img", "/img/ad2.jpg", "href", "https://example.com/productAd2", "alt", "제품 광고 2"));
        ads.add(Map.of("img", "/img/ad3.jpg", "href", "https://example.com/productAd3", "alt", "제품 광고 2"));

        model.addAttribute("banners", banners);
        model.addAttribute("ads", ads);

        model.addAttribute("todayRecommendProducts", dailyRecommentProducts);
        model.addAttribute("bestProducts", bestProducts);
        model.addAttribute("newProducts", newProducts);
        return "screens/home";
    }

}
