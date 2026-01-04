package com.cju.shoppingmall.product.controller;

import com.cju.shoppingmall.product.dto.VariantPriceRequest;
import com.cju.shoppingmall.product.dto.VariantPriceResponse;
import com.cju.shoppingmall.product.entity.ProductVariant;
import com.cju.shoppingmall.product.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductVariantApiController {

    private final ProductService productService;

    public ProductVariantApiController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product/variant/price")
    public VariantPriceResponse getVariantPrice(@RequestBody VariantPriceRequest req) {
        ProductVariant variant = productService.findVariantByOptions(
                req.getProductId(),
                req.getOptionValueIds()
        );
        return new VariantPriceResponse(variant.getId(), variant.getPrice());
    }
}
