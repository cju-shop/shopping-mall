package com.cju.shoppingmall.cart.service;

import com.cju.shoppingmall.cart.entity.Cart;
import com.cju.shoppingmall.cart.entity.CartDetail;
import com.cju.shoppingmall.cart.repository.CartDetailRepository;
import com.cju.shoppingmall.cart.repository.CartRepository;
import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.member.repository.MemberRepository;
import com.cju.shoppingmall.product.entity.ProductVariant;
import com.cju.shoppingmall.product.repository.ProductVariantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductVariantRepository productVariantRepository;
    private final MemberRepository memberRepository;

    public CartServiceImpl(
            CartRepository cartRepository,
            CartDetailRepository cartDetailRepository,
            ProductVariantRepository productVariantRepository,
            MemberRepository memberRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.productVariantRepository = productVariantRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void addToCart(Long productVariantId, Long qty, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Cart cart = cartRepository.findByMember(member)
                .orElseGet(() -> cartRepository.save(
                        new Cart(member, 0L, 0L, 0L, 0L)
                ));

        ProductVariant variant = productVariantRepository.findById(productVariantId)
                .orElseThrow(() -> new IllegalArgumentException("상품 옵션 없음"));

        CartDetail detail = cartDetailRepository
                .findByCartAndProductVariant(cart, variant)
                .orElse(null);

        if (detail == null) {
            detail = new CartDetail(cart, variant, qty);
            cartDetailRepository.save(detail);
        } else {
            detail.increaseQty(qty);
        }

        recalculateCart(cart);
    }


    private void recalculateCart(Cart cart) {
        List<CartDetail> details = cartDetailRepository.findByCart(cart);

        long totalCount = 0;
        long subtotal = 0L;

        for (CartDetail d : details) {
            totalCount += d.getQty();
            subtotal += d.getProductVariant().getPrice() * d.getQty();
        }

        cart.updateAmounts(totalCount, subtotal, 0L);
    }

    @Override
    public void addToCartByOptions(Long productId, List<Long> optionValueIds, Long qty, Long memberId) {

        Long variantId = productVariantRepository
                .findVariantIdByProductAndOptionValues(productId, optionValueIds, optionValueIds.size())
                .orElseThrow(() -> new IllegalArgumentException("해당 옵션 조합 없음"));

        addToCart(variantId, qty, memberId); // ✅ 기존 로직 재사용
    }

}
