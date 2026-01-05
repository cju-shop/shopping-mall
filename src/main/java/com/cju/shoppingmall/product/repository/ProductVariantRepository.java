package com.cju.shoppingmall.product.repository;

import com.cju.shoppingmall.product.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    @Query(value = """
    SELECT pv.id
    FROM product_variant pv
    WHERE pv.product_id = :productId
      AND (
        SELECT COUNT(DISTINCT pvo.option_value_id)
        FROM product_variant_option pvo
        WHERE pvo.product_variant_id = pv.id
          AND pvo.option_value_id IN (:optionValueIds)
      ) = :cnt
    """, nativeQuery = true)
    Optional<Long> findVariantIdByProductAndOptionValues(
            @Param("productId") Long productId,
            @Param("optionValueIds") List<Long> optionValueIds,
            @Param("cnt") int cnt
    );
}
