package com.cju.shoppingmall.product.controller;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductRegisterForm {

    private Long parentCategoryId;
    private Long childCategoryId;

    private String name;
    private Long price;
    private MultipartFile image;

    private List<OptionTypeForm> optionTypes = new ArrayList<>();
    private List<VariantForm> variants = new ArrayList<>();


    @Getter
    public static class OptionTypeForm {
        private String name;
        private List<OptionValueForm> values = new ArrayList<>();

    }

    @Getter
    public static class OptionValueForm {
        private String value;

    }

    @Getter
    public static class VariantForm {
        private String label;
        private Long extraPrice;
        private Long stockQty;
        private Boolean active;

    }
}

