package com.cju.shoppingmall.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductRegisterController {

    @GetMapping("/product/register")
    public  String productRegister() {

        return  "screens/product_register";
    }

}
