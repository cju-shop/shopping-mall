package com.cju.shoppingmall.product.dto;
import java.util.List;

public record OptionTypeView(
        Long id,
        String name,
        String displayName,
        List<OptionValueView> values
) {}