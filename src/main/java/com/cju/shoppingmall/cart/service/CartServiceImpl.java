package com.cju.shoppingmall.cart.service;

import com.cju.shoppingmall.cart.dto.CartItemResponse;
import com.cju.shoppingmall.cart.dto.CartResponse;
import com.cju.shoppingmall.cart.entity.Cart;
import com.cju.shoppingmall.cart.entity.CartDetail;
import com.cju.shoppingmall.cart.repository.CartDetailRepository;
import com.cju.shoppingmall.cart.repository.CartRepository;
import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.member.repository.MemberRepository;
import com.cju.shoppingmall.product.entity.Product;
import com.cju.shoppingmall.product.entity.ProductVariant;
import com.cju.shoppingmall.product.entity.ProductVariantOption;
import com.cju.shoppingmall.product.repository.ProductVariantOptionRepository;
import com.cju.shoppingmall.product.repository.ProductVariantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ProductVariantOptionRepository productVariantOptionRepository;
    private final MemberRepository memberRepository;

    public CartServiceImpl(
            CartRepository cartRepository,
            CartDetailRepository cartDetailRepository,
            ProductVariantRepository productVariantRepository,
            ProductVariantOptionRepository productVariantOptionRepository,
            MemberRepository memberRepository
    ) {
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.productVariantRepository = productVariantRepository;
        this.productVariantOptionRepository = productVariantOptionRepository;
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

        addToCart(variantId, qty, memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Cart cart = cartRepository.findByMember(member).orElse(null);

        if (cart == null) {
            return new CartResponse(null, 0L, 0L, 0L, 0L, Collections.emptyList());
        }

        List<CartDetail> details = cartDetailRepository.findByCartWithProduct(cart);
        List<CartItemResponse> items = new ArrayList<>();

        for (CartDetail detail : details) {
            ProductVariant variant = detail.getProductVariant();
            Product product = variant.getProduct();

            String optionText = buildOptionText(variant);

            items.add(new CartItemResponse(
                    detail.getId(),
                    product.getId(),
                    product.getName(),
                    product.getThumbnail(),
                    variant.getId(),
                    optionText,
                    variant.getPrice(),
                    detail.getQty()
            ));
        }

        return new CartResponse(
                cart.getId(),
                cart.getTotalCount(),
                cart.getSubtotalAmount(),
                cart.getDiscountAmount(),
                cart.getTotalAmount(),
                items
        );
    }

    private String buildOptionText(ProductVariant variant) {
        List<ProductVariantOption> options = productVariantOptionRepository
                .findByProductVariantWithOptionValue(variant);

        return options.stream()
                .map(pvo -> pvo.getOptionValue().getOptionType().getDisplayName()
                        + ": " + pvo.getOptionValue().getValue())
                .collect(Collectors.joining(" / "));
    }

    @Override
    public void updateQuantity(Long cartDetailId, Long qty, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Cart cart = cartRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

        CartDetail detail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목 없음"));

        if (!detail.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("권한 없음");
        }

        if (qty <= 0) {
            cartDetailRepository.delete(detail);
        } else {
            detail.updateQty(qty);
        }

        recalculateCart(cart);
    }

    @Override
    public void removeItem(Long cartDetailId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Cart cart = cartRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

        CartDetail detail = cartDetailRepository.findById(cartDetailId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목 없음"));

        if (!detail.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("권한 없음");
        }

        cartDetailRepository.delete(detail);
        recalculateCart(cart);
    }

    @Override
    public void removeItems(List<Long> cartDetailIds, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원 없음"));

        Cart cart = cartRepository.findByMember(member)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 없음"));

        for (Long cartDetailId : cartDetailIds) {
            CartDetail detail = cartDetailRepository.findById(cartDetailId).orElse(null);
            if (detail != null && detail.getCart().getId().equals(cart.getId())) {
                cartDetailRepository.delete(detail);
            }
        }

        recalculateCart(cart);
    }

}
