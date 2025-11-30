package com.cju.shoppingmall.product.service;

import java.util.List;
import java.util.Optional;

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

        processOptionTypes(form.getOptionTypes(), savedProduct);

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

    private void processOptionTypes(List<OptionTypeForm> optionTypes, Product product) {
        if (optionTypes == null) {
            return;
        }

        for (OptionTypeForm typeForm : optionTypes) {
            OptionType optionType = getOrCreateOptionType(typeForm.getName());
            linkProductAndOptionType(product, optionType);
            saveOptionValues(typeForm.getValues(), optionType);
        }
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

    private void saveOptionValues(List<OptionValueForm> values, OptionType optionType) {
        if (values == null) {
            return;
        }

        for (OptionValueForm valueForm : values) {
            String valueName = valueForm.getValue();

            boolean existsOptionValue = optionValueRepository
                    .findByOptionTypeIdAndValue(optionType.getId(), valueName)
                    .isPresent();

            if (!existsOptionValue) {
                optionValueRepository.save(new OptionValue(optionType, valueName));
            }
        }
    }


}