package com.cju.shoppingmall.product.controller;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductRegisterForm {

    private final Long parentCategoryId;
    private final Long childCategoryId;

    private final String name;
    private final Long price;
    private final MultipartFile image;

    private final List<OptionTypeForm> optionTypes;

    public ProductRegisterForm(
            Long parentCategoryId,
            Long childCategoryId,
            String name,
            Long price,
            MultipartFile image,
            List<OptionTypeForm> optionTypes) {
        this.parentCategoryId = parentCategoryId;
        this.childCategoryId = childCategoryId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.optionTypes = (optionTypes != null) ? optionTypes : new ArrayList<>();
    }

    @Getter
    public static class OptionTypeForm {

        private final String name;
        private final List<OptionValueForm> values;

        public OptionTypeForm(String name, List<OptionValueForm> values) {
            this.name = name;
            this.values = (values != null) ? values : new ArrayList<>();
        }
    }
    @Getter
    public static class OptionValueForm {

        private final String value;

        public OptionValueForm(String value) {
            this.value = value;
        }
    }
}
