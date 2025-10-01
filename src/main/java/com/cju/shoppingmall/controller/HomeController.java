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
        List<Map<String, Object>> products = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            Map<String, Object> p = new HashMap<>();
            p.put("id", i);
            p.put("name", "라이트 니트 카디건 #" + i);
            p.put("price", 39900 + (i % 5) * 1000);
            p.put("img", "https://picsum.photos/seed/" + i + "/600/450");
            products.add(p);
        }

        model.addAttribute("products", products);
        return "screens/home";
    }

}
