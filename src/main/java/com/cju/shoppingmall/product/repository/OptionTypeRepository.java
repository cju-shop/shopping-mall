package com.cju.shoppingmall.product.repository;

import com.cju.shoppingmall.product.entity.Category;
import com.cju.shoppingmall.product.entity.OptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionTypeRepository extends JpaRepository<OptionType, Long> {
    Optional<OptionType> findByName(String name);
}
