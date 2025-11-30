package com.cju.shoppingmall.product.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.member.entity.MemberRole;
import com.cju.shoppingmall.product.entity.Category;
import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void 상품_생성_성공() {
        // Given
        Category category = new Category(null, "Outerwear", true);
        Member creator = new Member("user", "nickname", "name", "password", "01012341234",
            "user@example.com", MemberRole.CONSUMER);
        Product product = new Product("Coat", "Warm winter coat", "thumb.jpg", 10000L, category, creator);

        when(repository.save(product)).thenReturn(product);

        // when
        Product result = productService.create(product);

        // then
        assertThat(result).isEqualTo(product);
    }

    @Test
    void 상품_생성_실패_테스트() {
        Category category = new Category(null, "Outerwear", true);
        Member creator = new Member("user", "nickname", "name", "password", "01012341234",
            "user@example.com", MemberRole.CONSUMER);
        Product product = new Product("Coat", "Warm winter coat", "thumb.jpg", 10000L, category, creator);

        when(repository.save(product)).thenThrow(new IllegalStateException("save failed"));

        assertThrows(IllegalStateException.class, () -> productService.create(product));
    }
}
