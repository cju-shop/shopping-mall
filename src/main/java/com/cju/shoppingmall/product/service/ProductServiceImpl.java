package com.cju.shoppingmall.product.service;

import java.util.List;
import java.util.Optional;

import com.cju.shoppingmall.product.controller.ProductRegisterForm;
import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.product.dto.OptionTypeView;
import com.cju.shoppingmall.product.dto.OptionValueView;
import com.cju.shoppingmall.product.entity.*;
import com.cju.shoppingmall.product.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Override
    @Transactional(readOnly = true)
    public List<OptionTypeView> getOptionViewsByProduct(Long productId) {
        List<ProductOption> productOptions = productOptionRepository.findByProduct_Id(productId);

        List<OptionType> optionTypes = productOptions.stream()
                .map(ProductOption::getOptionType)
                .distinct()
                .toList();

        return optionTypes.stream()
                .map(optionType -> {

                    List<OptionValueView> values = optionValueRepository
                            .findByOptionType_Id(optionType.getId())
                            .stream()
                            .map(ov -> new OptionValueView(ov.getId(), ov.getValue()))
                            .toList();

                    return new OptionTypeView(
                            optionType.getId(),
                            optionType.getName(),
                            optionType.getDisplayName(),
                            values
                    );
                })
                .toList();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }
}