package com.cju.shoppingmall.product.repository;

import com.cju.shoppingmall.product.entity.ProductVariant;
import com.cju.shoppingmall.product.entity.ProductVariantOption;
import com.cju.shoppingmall.product.entity.ProductVariantOptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductVariantOptionRepository
        extends JpaRepository<ProductVariantOption, ProductVariantOptionId> {

    @Query("SELECT pvo FROM ProductVariantOption pvo " +
           "JOIN FETCH pvo.optionValue ov " +
           "JOIN FETCH ov.optionType " +
           "WHERE pvo.productVariant = :variant")
    List<ProductVariantOption> findByProductVariantWithOptionValue(@Param("variant") ProductVariant variant);
}