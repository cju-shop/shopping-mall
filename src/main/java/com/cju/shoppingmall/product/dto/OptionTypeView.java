package com.cju.shoppingmall.product.dto;

import com.cju.shoppingmall.product.entity.OptionValue;
import java.util.List;

public record OptionTypeView(
        Long id,
        String name,
        String displayName,
        List<String> values
) {}