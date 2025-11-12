package com.cju.shoppingmall.product.repository;

import com.cju.shoppingmall.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    boolean existsByProductIdAndOptionTypeId(Long productId, Long optionTypeId);
}
