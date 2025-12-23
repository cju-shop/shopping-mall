package com.cju.shoppingmall.product.repository;
import com.cju.shoppingmall.product.entity.OptionValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionValueRepository extends JpaRepository<OptionValue, Long> {
    Optional<OptionValue> findByOptionTypeIdAndValue(Long optionTypeId, String value);


}
