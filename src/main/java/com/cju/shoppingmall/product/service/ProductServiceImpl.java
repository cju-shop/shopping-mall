package com.cju.shoppingmall.product.service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.cju.shoppingmall.member.repository.MemberRepository;
import com.cju.shoppingmall.order.dto.ProductSalesSummaryDto;
import com.cju.shoppingmall.order.repository.OrderDetailRepository;
import com.cju.shoppingmall.product.controller.ProductRegisterForm.OptionTypeForm;
import com.cju.shoppingmall.product.controller.ProductRegisterForm.OptionValueForm;
import com.cju.shoppingmall.product.controller.ProductRegisterForm;
import com.cju.shoppingmall.member.entity.Member;
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
    private final MemberRepository memberRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantOptionRepository productVariantOptionRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductServiceImpl(ProductRepository repository,
                              CategoryRepository categoryRepository,
                              OptionTypeRepository optionTypeRepository,
                              OptionValueRepository optionValueRepository,
                              ProductOptionRepository productOptionRepository,
                              MemberRepository memberRepository,
                              ProductVariantRepository productVariantRepository,
                              ProductVariantOptionRepository productVariantOptionRepository, OrderDetailRepository orderDetailRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.optionTypeRepository = optionTypeRepository;
        this.optionValueRepository = optionValueRepository;
        this.productOptionRepository = productOptionRepository;
        this.memberRepository = memberRepository;
        this.productVariantRepository = productVariantRepository;
        this.productVariantOptionRepository = productVariantOptionRepository;
        this.orderDetailRepository = orderDetailRepository;
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
    public List<Product> getDailyRecommentProducts() {
        return repository.findTop4ByOrderByCreatedAtDesc();
    }
    public List<Product> getNewProducts() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        List<Product> newProducts =
                repository.findTop8ByCreatedAtAfterOrderByCreatedAtDesc(sevenDaysAgo);

        if (newProducts.isEmpty()) {
            return repository.findTop8ByOrderByCreatedAtDesc();
        }

        return newProducts;
    }

    @Override
    public List<Product> getBestProductsLast7Days(int limit) {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        List<ProductSalesSummaryDto> summaries = orderDetailRepository.findProductSalesLast7Days(sevenDaysAgo);
        if (summaries.isEmpty()) {
            return repository.findTop8ByOrderByCreatedAtDesc();
        }

        List<Long> productIds = new ArrayList<>();
        int count = 0;
        for (ProductSalesSummaryDto summary : summaries) {
            if (count >= limit) break;
            productIds.add(summary.getProductId());
            count++;
        }

        List<Product> products = repository.findByIdIn(productIds);

        // id 순서를 유지하기 위해 map으로 정렬
        Map<Long, Product> productMap = new HashMap<>();
        for (Product p : products) {
            productMap.put(p.getId(), p);
        }

        List<Product> bestProducts = new ArrayList<>();
        for (Long id : productIds) {
            Product p = productMap.get(id);
            if (p != null) {
                bestProducts.add(p);
            }
        }
        return bestProducts;
    }

    @Transactional
    public Long register(ProductRegisterForm form) {
        Member createdBy = findAdminMember();
        Category category = findCategory(form.getChildCategoryId());

        Product savedProduct = saveProduct(form, category, createdBy);

        List<List<OptionValue>> optionValueMatrix =
                processOptionTypesAndReturnOptionValues(form.getOptionTypes(), savedProduct);

        createVariantsFromForm(optionValueMatrix, form.getVariants(), savedProduct, createdBy);

        return savedProduct.getId();
    }


    private Member findAdminMember() {
        return memberRepository.findByUsername("user2")
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
            List<OptionTypeForm> optionTypeForms, Product product
    ) {
        List<List<OptionValue>> optionValueMatrix = new ArrayList<>();

        if (optionTypeForms == null) {
            return optionValueMatrix ;
        }

        for (OptionTypeForm typeForm : optionTypeForms) {

            OptionType optionType = getOrCreateOptionType(typeForm.getName());
            linkProductAndOptionType(product, optionType); // product_option 테이블 저장

            List<OptionValue> valueList = saveOptionValues(typeForm.getValues(), optionType);
            optionValueMatrix.add(valueList);
        }

        return optionValueMatrix ;
    }


    private OptionType getOrCreateOptionType(String typeName) {
        Optional<OptionType> existingOptionType = optionTypeRepository.findByName(typeName);

        return existingOptionType.orElseGet(() -> optionTypeRepository.save(new OptionType(typeName, typeName)));
    }

    private void linkProductAndOptionType(Product product, OptionType optionType) {
        if (!productOptionRepository.existsByProductIdAndOptionTypeId(product.getId(), optionType.getId())) {
            productOptionRepository.save(new ProductOption(product, optionType));
        }
    }

    private List<OptionValue> saveOptionValues(
            List<OptionValueForm> valueForms, OptionType optionType
    ) {
        List<OptionValue> savedOptionValues = new ArrayList<>();

        if (valueForms == null) {
            return savedOptionValues;
        }

        for (OptionValueForm valueForm : valueForms) {
            String valueName = valueForm.getValue();

            OptionValue optionValue = optionValueRepository
                    .findByOptionTypeIdAndValue(optionType.getId(), valueName)
                    .orElseGet(() -> optionValueRepository.save(
                            new OptionValue(optionType, valueName)
                    ));

            savedOptionValues.add(optionValue);
        }

        return savedOptionValues ;
    }

    private void buildCombinations(
            List<List<OptionValue>> optionValueMatrix,
            int depth,
            List<OptionValue> currentCombination,
            List<List<OptionValue>> variantCombinations
    ) {
        if (depth == optionValueMatrix.size()) {
            variantCombinations.add(new ArrayList<>(currentCombination));
            return;
        }

        for (OptionValue optionValue : optionValueMatrix.get(depth)) {
            currentCombination.add(optionValue);
            buildCombinations(optionValueMatrix, depth + 1, currentCombination, variantCombinations);
            currentCombination.remove(currentCombination.size() - 1);
        }
    }

    private void createVariantsFromForm(
            List<List<OptionValue>> optionValueMatrix,
            List<ProductRegisterForm.VariantForm> variantForms,
            Product product,
            Member createdBy
    ) {
        if (optionValueMatrix.isEmpty() || variantForms == null || variantForms.isEmpty()) {
            return;
        }

        List<List<OptionValue>> variantCombinations = new ArrayList<>();
        buildCombinations(optionValueMatrix, 0, new ArrayList<>(), variantCombinations);

        for (int idx = 0; idx < variantCombinations.size(); idx++) {

            if (idx >= variantForms.size() || variantForms.get(idx) == null) {
                continue;
            }

            ProductRegisterForm.VariantForm variantForm = variantForms.get(idx);

            List<OptionValue> optionValueCombination = variantCombinations.get(idx);

            String fingerprint = optionValueCombination.stream()
                    .map(v -> v.getId().toString())
                    .sorted()
                    .collect(Collectors.joining("-"));

            Long basePrice = product.getBasePrice();
            Long finalPrice = basePrice +
                    (variantForm.getExtraPrice() != null ? variantForm.getExtraPrice() : 0L);
            Long stockQty = variantForm.getStockQty() != null ? variantForm.getStockQty() : 0L;

            boolean isActive = Boolean.TRUE.equals(variantForm.getActive());

            ProductVariant savedVariant = productVariantRepository.save(
                    new ProductVariant(
                            product,
                            finalPrice,
                            null,
                            stockQty,
                            isActive,
                            fingerprint,
                            createdBy
                    )
            );

            for (OptionValue optionValue : optionValueCombination) {
                productVariantOptionRepository.save(new ProductVariantOption(savedVariant, optionValue));
            }
        }
    }
}
