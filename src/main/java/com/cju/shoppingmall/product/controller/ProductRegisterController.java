package com.cju.shoppingmall.product.controller;


import com.cju.shoppingmall.member.entity.Member;
import com.cju.shoppingmall.member.entity.MemberRole;
import com.cju.shoppingmall.member.repository.MemberRepository;
import com.cju.shoppingmall.member.service.MemberService;
import com.cju.shoppingmall.product.entity.Category;
import com.cju.shoppingmall.product.entity.CategoryDto;
import com.cju.shoppingmall.product.repository.CategoryRepository;
import com.cju.shoppingmall.product.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductRegisterController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public ProductRegisterController(ProductService productService,MemberRepository memberRepository, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/product/register")
    public  String productRegister(Model model) {
        List<Category> parentCategories = categoryRepository.findByParentIsNullAndIsActiveTrue();
        model.addAttribute("parentCategories", parentCategories);

        return  "screens/product_register";
    }

    @GetMapping("/categories/{parentId}/children")
    @ResponseBody
    public List<CategoryDto> getChildCategories(@PathVariable Long parentId) {
        return categoryRepository.findByParentIdAndIsActiveTrue(parentId)
                .stream()
                .map(c -> new CategoryDto(c.getId(), c.getName()))
                .toList();
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
