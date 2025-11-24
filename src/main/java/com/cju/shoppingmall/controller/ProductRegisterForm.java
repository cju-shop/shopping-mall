package com.cju.shoppingmall.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ProductRegisterForm {
    private String category;
    private String childrenCategory;
    private String name;
    private Long price;
    private MultipartFile image;
    private List<OptionTypeForm> optionTypes = new ArrayList<>();

    @Getter @Setter
    public static class OptionTypeForm {
        private String name;
        private List<OptionValueForm> values = new ArrayList<>();
    }

    @Getter @Setter
    public static class OptionValueForm {
        private String value;
    }
}
