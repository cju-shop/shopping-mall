package com.cju.shoppingmall.cart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cju.shoppingmall.cart.dto.CartResponse;
import com.cju.shoppingmall.cart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/info")
    public ResponseEntity<CartResponse> getCart() {
        Long memberId = 1L; // 임시(로그인 전까지)
        CartResponse response = cartService.getCart(memberId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
        @RequestParam("productVariantId") Long productVariantId,
        @RequestParam("quantity") Long quantity
    ) {
        Long memberId = 1L; // 임시(로그인 전까지)
        cartService.addToCart(productVariantId, quantity, memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/add-items")
    public ResponseEntity<?> addToCartByOptions(@RequestBody AddItemsRequest req) {
        Long memberId = 1L; // 임시

        for (AddItemsRequest.Item item : req.items()) {
            cartService.addToCart(item.productVariantId(), item.quantity(), memberId);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateQuantity(@RequestBody UpdateQuantityRequest req) {
        Long memberId = 1L; // 임시
        cartService.updateQuantity(req.cartDetailId(), req.qty(), memberId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove/{cartDetailId}")
    public ResponseEntity<?> removeItem(@PathVariable Long cartDetailId) {
        Long memberId = 1L; // 임시
        cartService.removeItem(cartDetailId, memberId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> removeItems(@RequestBody RemoveItemsRequest req) {
        Long memberId = 1L; // 임시
        cartService.removeItems(req.cartDetailIds(), memberId);
        return ResponseEntity.ok().build();
    }

    public record AddItemsRequest(List<Item> items) {
        public record Item(Long productVariantId, Long quantity) {
        }
    }

    public record AddToCartByOptionsRequest(
        Long productId,
        List<Long> optionValueIds,
        Long quantity
    ) {
    }

    public record UpdateQuantityRequest(Long cartDetailId, Long qty) {
    }

    public record RemoveItemsRequest(List<Long> cartDetailIds) {
    }
}
