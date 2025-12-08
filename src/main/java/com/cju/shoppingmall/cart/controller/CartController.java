package com.cju.shoppingmall.cart.controller;

import com.cju.shoppingmall.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam("productId") Long productId,
                            @RequestParam("quantity") Integer quantity) {

        cartService.addToCart(productId, quantity);

        return "redirect:/product/detail?id=" + productId;
    }
}
