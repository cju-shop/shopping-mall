package com.cju.shoppingmall.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cju.shoppingmall.member.repository.MemberRepository;
import com.cju.shoppingmall.product.controller.ProductRegisterForm.OptionTypeForm;
import com.cju.shoppingmall.product.controller.ProductRegisterForm.OptionValueForm;
import com.cju.shoppingmall.product.controller.ProductRegisterForm;
import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.product.entity.*;
import com.cju.shoppingmall.product.repository.*;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final OptionTypeRepository optionTypeRepository;
    private final OptionValueRepository optionValueRepository;
    private final ProductOptionRepository productOptionRepository;
    private final MemberRepository memberRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantOptionRepository productVariantOptionRepository;

    public ProductServiceImpl(ProductRepository repository,
                              CategoryRepository categoryRepository,
                              OptionTypeRepository optionTypeRepository,
                              OptionValueRepository optionValueRepository,
                              ProductOptionRepository productOptionRepository,
                              MemberRepository memberRepository,
                              ProductVariantRepository productVariantRepository,
                              ProductVariantOptionRepository productVariantOptionRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.optionTypeRepository = optionTypeRepository;
        this.optionValueRepository = optionValueRepository;
        this.productOptionRepository = productOptionRepository;
        this.memberRepository = memberRepository;
        this.productVariantRepository = productVariantRepository;
        this.productVariantOptionRepository = productVariantOptionRepository;
    }

    @Override
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }


    @Override
    public List<Product> getNewProducts() {
        return repository.findTop4ByOrderByCreatedAtDesc();
    }

    public Long register(ProductRegisterForm form) {
        Member createdBy = findAdminMember();
        Category category = findCategory(form.getChildCategoryId());

        Product savedProduct = saveProduct(form, category, createdBy);

        List<List<OptionValue>> optionValueMatrix =
                processOptionTypesAndReturnOptionValues(form.getOptionTypes(), savedProduct);

        System.out.println("==== [DEBUG] register ====");
        System.out.println("childId          = " + form.getChildCategoryId());
        System.out.println("optionTypes size = " + (form.getOptionTypes() == null ? "null" : form.getOptionTypes().size()));
        System.out.println("variants size    = " + (form.getVariants() == null ? "null" : form.getVariants().size()));
        System.out.println("matrix size      = " + optionValueMatrix.size());

        createVariantsFromForm(optionValueMatrix, form.getVariants(), savedProduct, createdBy);

        return savedProduct.getId();
    }


    private Member findAdminMember() {
        return memberRepository.findByUsername("admin")
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음"));
    }

    private Category findCategory(Long childId) {
        if (childId == null) {
            throw new IllegalArgumentException("하위 카테고리가 선택되지 않았습니다.");
        }

        return categoryRepository.findById(childId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 하위 카테고리를 찾을 수 없습니다. id=" + childId));
    }

    private Product saveProduct(ProductRegisterForm form, Category category, Member createdBy) {
        Product product = new Product(
                form.getName(),
                "test.jpg",
                null,  // thumbnail
                form.getPrice(),
                category,
                createdBy
        );
        return repository.save(product);
    }

    private List<List<OptionValue>> processOptionTypesAndReturnOptionValues(
            List<OptionTypeForm> optionTypes, Product product
    ) {
        List<List<OptionValue>> allOptionValues = new ArrayList<>();

        if (optionTypes == null) {
            System.out.println(">>> optionTypes == null");
            return allOptionValues;
        }

        for (OptionTypeForm typeForm : optionTypes) {
            System.out.println(">>> OptionType name=" + typeForm.getName()
                    + ", values size=" + (typeForm.getValues() == null ? "null" : typeForm.getValues().size()));

            OptionType optionType = getOrCreateOptionType(typeForm.getName());
            linkProductAndOptionType(product, optionType);

            List<OptionValue> valueList = saveOptionValuesAndReturn(typeForm.getValues(), optionType);
            allOptionValues.add(valueList);
        }

        System.out.println(">>> allOptionValues size=" + allOptionValues.size());
        return allOptionValues;
    }


    private OptionType getOrCreateOptionType(String typeName) {
        Optional<OptionType> existingOptionType = optionTypeRepository.findByName(typeName);

        return existingOptionType.orElseGet(() -> optionTypeRepository.save(new OptionType(typeName, typeName)));
    }

    private void linkProductAndOptionType(Product product, OptionType optionType) {
        boolean productHasOptionType = productOptionRepository
                .existsByProductIdAndOptionTypeId(product.getId(), optionType.getId());

        if (!productHasOptionType) {
            productOptionRepository.save(new ProductOption(product, optionType));
        }
    }

    private List<OptionValue> saveOptionValuesAndReturn(
            List<OptionValueForm> values, OptionType optionType
    ) {
        List<OptionValue> result = new ArrayList<>();

        if (values == null) {
            return result;
        }

        for (OptionValueForm valueForm : values) {
            String valueName = valueForm.getValue();

            Optional<OptionValue> existing = optionValueRepository
                    .findByOptionTypeIdAndValue(optionType.getId(), valueName);

            OptionValue saved = existing.orElseGet(
                    () -> optionValueRepository.save(new OptionValue(optionType, valueName))
            );

            result.add(saved);
        }

        return result;
    }

    private void buildCombinations(List<List<OptionValue>> matrix,
                                   int depth,
                                   List<OptionValue> current,
                                   List<List<OptionValue>> result) {
        if (depth == matrix.size()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (OptionValue v : matrix.get(depth)) {
            current.add(v);
            buildCombinations(matrix, depth + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    private void createVariantsFromForm(
            List<List<OptionValue>> optionValueMatrix,
            List<ProductRegisterForm.VariantForm> variants,
            Product product,
            Member createdBy
    ) {
        System.out.println("==== [DEBUG] createVariantsFromForm ====");
        System.out.println("matrix empty?   = " + optionValueMatrix.isEmpty());
        System.out.println("variants null?  = " + (variants == null));
        System.out.println("variants empty? = " + (variants != null && variants.isEmpty()));

        if (optionValueMatrix.isEmpty() || variants == null || variants.isEmpty()) {
            System.out.println(">>> early return: 조건 만족 안 해서 Variant 생성하지 않음");
            return;
        }

        List<List<OptionValue>> combinations = new ArrayList<>();
        buildCombinations(optionValueMatrix, 0, new ArrayList<>(), combinations);
        System.out.println("combinations size = " + combinations.size());

        for (int i = 0; i < combinations.size(); i++) {
            if (i >= variants.size() || variants.get(i) == null) {
                System.out.println(">>> i=" + i + " : variants가 없어서 스킵");
                continue;
            }

            ProductRegisterForm.VariantForm vf = variants.get(i);
            System.out.println(">>> i=" + i + " label=" + vf.getLabel()
                    + ", extraPrice=" + vf.getExtraPrice()
                    + ", stock=" + vf.getStockQty()
                    + ", active=" + vf.getActive());

            if (Boolean.FALSE.equals(vf.getActive())) {
                System.out.println(">>> i=" + i + " : active=false라 스킵");
                continue;
            }

            List<OptionValue> combo = combinations.get(i);
            String fingerprint = combo.stream()
                    .map(v -> v.getId().toString())
                    .sorted()
                    .collect(Collectors.joining("-"));

            Long basePrice = product.getBasePrice();
            Long finalPrice = basePrice + (vf.getExtraPrice() != null ? vf.getExtraPrice() : 0L);
            Long stockQty = vf.getStockQty() != null ? vf.getStockQty() : 0L;

            ProductVariant savedVariant = productVariantRepository.save(
                    new ProductVariant(
                            product,
                            finalPrice,
                            null,
                            stockQty,
                            true,
                            fingerprint,
                            createdBy
                    )
            );
            System.out.println(">>> savedVariant id = " + savedVariant.getId());

            for (OptionValue ov : combo) {
                productVariantOptionRepository.save(new ProductVariantOption(savedVariant, ov));
            }
        }
    }






}