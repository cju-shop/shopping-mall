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
        JOIN product_variant_option pvo ON pvo.product_variant_id = pv.id
        WHERE pv.product_id = :productId
          AND pvo.option_value_id IN (:optionValueIds)
        GROUP BY pv.id
        HAVING COUNT(DISTINCT pvo.option_value_id) = :cnt
        """, nativeQuery = true)
    Optional<Long> findVariantIdByProductAndOptionValues(
            @Param("productId") Long productId,
            @Param("optionValueIds") List<Long> optionValueIds,
            @Param("cnt") int cnt
    );
}
