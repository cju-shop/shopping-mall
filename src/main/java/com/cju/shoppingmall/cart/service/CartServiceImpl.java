package com.cju.shoppingmall.cart.service;

import com.cju.shoppingmall.cart.entity.Cart;
import com.cju.shoppingmall.cart.repository.CartRepository;
import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.member.repository.MemberRepository;
import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, MemberRepository memberRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public void addToCart(Long productId, Integer quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품 없음: id=" + productId));

        long subtotal = product.getBasePrice() * quantity;

        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("멤버 없음"));

        Cart cart = new Cart(
                member,
                quantity,
                subtotal,
                0L,
                subtotal
        );

        cartRepository.save(cart);
    }
}
