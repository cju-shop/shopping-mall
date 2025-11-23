package com.cju.shoppingmall.product.service;

import java.util.List;

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

    public Long register(ProductRegisterForm form, Member createdBy){
        String childName = form.getChildrenCategory() == null ? null : form.getChildrenCategory().trim();
        Category target;
        if (childName != null && !childName.isEmpty()) {
            target = categoryRepository.findByName(childName)
                    .orElseThrow(() ->
                            new IllegalArgumentException("해당 하위 카테고리를 찾을 수 없습니다: " + childName));
        } else {
            throw new IllegalArgumentException("카테고리 정보가 없습니다.");
        }

        Product product = new Product(
                form.getName(),
                "test.jpg",
                null,
                form.getPrice(),
                target,
                createdBy
        );

        Product saved = repository.save(product);

        if (form.getOptionTypes() != null) {
            for (ProductRegisterForm.OptionTypeForm typeForm : form.getOptionTypes()) {
                String name = typeForm.getName();  // 예: "color" 또는 "size"

                OptionType optionType = optionTypeRepository
                        .findByName(name)
                        .orElseGet(() -> {
                            OptionType t = new OptionType();
                            t.setName(name);
                            t.setDisplayName(name);
                            return optionTypeRepository.save(t);
                        });

                // 2️⃣ 옵션 값 등록
                if (typeForm.getValues() != null) {
                    for (ProductRegisterForm.OptionValueForm vf : typeForm.getValues()) {
                        String val = vf.getValue();
                        if (val == null || val.isBlank()) continue;

                        optionValueRepository
                                .findByOptionTypeIdAndValue(optionType.getId(), val)
                                .orElseGet(() -> {
                                    OptionValue ov = new OptionValue();
                                    ov.setOptionType(optionType);
                                    ov.setValue(val);
                                    return optionValueRepository.save(ov);
                                });
                    }
                }

                boolean exists = productOptionRepository
                        .existsByProductIdAndOptionTypeId(saved.getId(), optionType.getId());
                if (!exists) {
                    ProductOption po = new ProductOption();
                    po.setProduct(saved);
                    po.setOptionType(optionType);
                    productOptionRepository.save(po);
                }
            }
        }
        return product.getId();
    }
}