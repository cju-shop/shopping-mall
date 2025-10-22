package com.cju.shoppingmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class MypageController {

    @GetMapping("/mypage")
    public String mypage(
            @RequestParam(value = "status", required = false, defaultValue = "all") String status,
            Model model) {

        //로그인한 사용자의 이름
        model.addAttribute("username", "김준호");

        // 전체 주문 목록 생성
        List<Map<String, Object>> allOrders = createSampleOrders();

        // 상태에 따라 필터링
        List<Map<String, Object>> filteredOrders = filterOrdersByStatus(allOrders, status);

        model.addAttribute("orders", filteredOrders);
        model.addAttribute("currentStatus", status); // 현재 선택된 탭 정보

        return "screens/mypage";
    }

    // 샘플 주문 데이터 생성
    private List<Map<String, Object>> createSampleOrders() {
        List<Map<String, Object>> orders = new ArrayList<>();

        // 배송 완료 주문 2개
        for (int i = 0; i < 2; i++) {
            Map<String, Object> order = new HashMap<>();
            order.put("date", "25.03.25(화)");
            order.put("status", "배송 완료");
            order.put("productImage", "https://picsum.photos/seed/" + (100+i) + "/120/80");
            order.put("productName", "라운델 4인 가족 소파 [스툴 포함]");
            order.put("productOption", "화이트 / 1개");
            order.put("price", "431,230원");
            orders.add(order);
        }

        // 배송 중 주문 1개
        Map<String, Object> shippingOrder = new HashMap<>();
        shippingOrder.put("date", "25.03.24(월)");
        shippingOrder.put("status", "배송 중");
        shippingOrder.put("productImage", "https://picsum.photos/seed/102/120/80");
        shippingOrder.put("productName", "모던 원목 테이블 세트");
        shippingOrder.put("productOption", "브라운 / 1개");
        shippingOrder.put("price", "289,000원");
        orders.add(shippingOrder);

        return orders;
    }

    // 상태별 필터링 메서드
    private List<Map<String, Object>> filterOrdersByStatus(List<Map<String, Object>> orders, String status) {
        if ("all".equals(status)) {
            return orders;
        }

        List<Map<String, Object>> filtered = new ArrayList<>();
        for (Map<String, Object> order : orders) {
            String orderStatus = (String) order.get("status");
            if ("complete".equals(status) && "배송 완료".equals(orderStatus)) {
                filtered.add(order);
            } else if ("shipping".equals(status) && "배송 중".equals(orderStatus)) {
                filtered.add(order);
            }
        }
        return filtered;
    }
}
