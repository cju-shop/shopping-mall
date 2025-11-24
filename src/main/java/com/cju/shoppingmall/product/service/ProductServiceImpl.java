package com.cju.shoppingmall.product.service;

import java.util.List;

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

    public ProductServiceImpl(ProductRepository repository,
                              CategoryRepository categoryRepository,
                              OptionTypeRepository optionTypeRepository,
                              OptionValueRepository optionValueRepository,
                              ProductOptionRepository productOptionRepository
                              ) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.optionTypeRepository = optionTypeRepository;
        this.optionValueRepository = optionValueRepository;
        this.productOptionRepository = productOptionRepository;
    }

    @Override
    public Product create(Product product) {
        Product save = repository.save(product);
        return save;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }


    @Override
    public List<Product> getNewProducts() {
        return repository.findTop4ByOrderByCreatedAtDesc();
    }

    public Long register(ProductRegisterForm form, Member createdBy) {

        Long childId = form.getChildCategoryId();
        if (childId == null) {
            throw new IllegalArgumentException("하위 카테고리가 선택되지 않았습니다.");
        }

        Category category = categoryRepository.findById(childId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 하위 카테고리를 찾을 수 없습니다. id=" + childId));

        Product product = new Product(
                form.getName(),
                "test.jpg",
                null,  // thumbnail
                form.getPrice(),
                category,
                createdBy
        );

        Product savedProduct = repository.save(product);

        if (form.getOptionTypes() != null) {

            for (OptionTypeForm typeForm : form.getOptionTypes()) {
                String name = typeForm.getName();

                OptionType optionType = optionTypeRepository
                        .findByName(name)
                        .orElseGet(() -> {
                            OptionType type = new OptionType(name,name);
                            return optionTypeRepository.save(type);
                        });


                boolean productHasOptionType  = productOptionRepository
                        .existsByProductIdAndOptionTypeId(savedProduct.getId(), optionType.getId());

                if (!productHasOptionType ) {
                    ProductOption productOption = new ProductOption(savedProduct,optionType);
                    productOptionRepository.save(productOption);
                }

                if (typeForm.getValues() != null) {
                    for (OptionValueForm valueForm : typeForm.getValues()) {
                        String value = valueForm.getValue();

                        optionValueRepository
                                .findByOptionTypeIdAndValue(optionType.getId(), value)
                                .orElseGet(() -> {
                                    OptionValue ov = new OptionValue(optionType,value);
                                    return optionValueRepository.save(ov);
                                });
                    }
                }
            }
        }

        return savedProduct.getId();
    }

}