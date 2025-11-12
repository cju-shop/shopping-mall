package com.cju.shoppingmall.controller;


import com.cju.shoppingmall.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductRegisterController {

    private final ProductService productService;

    public ProductRegisterController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/register")
    public  String productRegister() {

        return  "screens/product_register";
    }

    @PostMapping("/register")
    public String registerProduct(@ModelAttribute ProductRegisterForm form) {
        Long productId = productService.register(form, "system"); // 작성자 고정값

        return "redirect:/product/register";
    }

}
