package com.cju.shoppingmall.product.repository;

import com.cju.shoppingmall.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    boolean existsByProductIdAndOptionTypeId(Long productId, Long optionTypeId);
    List<ProductOption> findByProduct_Id(Long productId);
}
