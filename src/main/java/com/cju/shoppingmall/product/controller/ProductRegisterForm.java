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

    // 옵션 타입 리스트 (예: 사이즈, 색상 ...)
    private List<OptionTypeForm> optionTypes = new ArrayList<>();

    // 옵션 조합(Variant) 리스트 (예: Free × Green, Free × Beige ...)
    private List<VariantForm> variants = new ArrayList<>();

    // 🔥 스프링 폼 바인딩용 기본 생성자 (필수)
    public ProductRegisterForm() {
    }

    // 선택: 직접 new 해서 테스트할 때 쓰고 싶으면 유지
    public ProductRegisterForm(
            Long parentCategoryId,
            Long childCategoryId,
            String name,
            Long price,
            MultipartFile image,
            List<OptionTypeForm> optionTypes,
            List<VariantForm> variants
    ) {
        this.parentCategoryId = parentCategoryId;
        this.childCategoryId = childCategoryId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.optionTypes = (optionTypes != null) ? optionTypes : new ArrayList<>();
        this.variants = (variants != null) ? variants : new ArrayList<>();
    }

    // ================== OptionTypeForm ==================
    @Getter
    public static class OptionTypeForm {
        // 예: "사이즈", "색상"
        private String name;

        // 예: ["Free", "L"], ["Green", "Beige"]
        private List<OptionValueForm> values = new ArrayList<>();

        public OptionTypeForm() {
        }

        public OptionTypeForm(String name, List<OptionValueForm> values) {
            this.name = name;
            this.values = (values != null) ? values : new ArrayList<>();
        }
    }

    // ================== OptionValueForm ==================
    @Getter
    public static class OptionValueForm {
        // 예: "Free", "Green"
        private String value;

        public OptionValueForm() {
        }

        public OptionValueForm(String value) {
            this.value = value;
        }
    }

    // ================== VariantForm ==================
    @Getter
    public static class VariantForm {
        // 예: "Free × Green"
        private String label;

        // 옵션별 추가 금액
        private Long extraPrice;

        // 옵션별 재고
        private Long stockQty;

        // 체크박스 (사용 여부)
        private Boolean active;

        public VariantForm() {
        }

        public VariantForm(String label, Long extraPrice, Long stockQty, Boolean active) {
            this.label = label;
            this.extraPrice = extraPrice;
            this.stockQty = stockQty;
            this.active = active;
        }
    }
}
