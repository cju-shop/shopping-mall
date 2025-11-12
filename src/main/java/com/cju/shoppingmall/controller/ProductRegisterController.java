package com.cju.shoppingmall.controller;


import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.member.entity.MemberRole;
import com.cju.shoppingmall.member.repository.MemberRepository;
import com.cju.shoppingmall.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductRegisterController {

    private final ProductService productService;
    private final MemberRepository memberRepository;

    public ProductRegisterController(ProductService productService,MemberRepository memberRepository) {
        this.productService = productService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/product/register")
    public  String productRegister() {

        return  "screens/product_register";
    }

    @PostMapping("/register")
    public String registerProduct(@ModelAttribute ProductRegisterForm form) {
        Member member = memberRepository.findByUsername("testID")
                .orElseGet(() -> memberRepository.save(
                        new Member("testID", "nick", "이유진", "testPW",
                                "010-1234-5678", "testEmail", MemberRole.CONSUMER)));
        Long productId = productService.register(form, member); // 작성자 고정값

        return "redirect:/product/register";
    }

}
