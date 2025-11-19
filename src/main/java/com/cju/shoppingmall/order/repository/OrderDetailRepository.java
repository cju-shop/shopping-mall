package com.cju.shoppingmall.order.repository;

import com.cju.shoppingmall.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("""
        SELECT od.productVariant.product.id, SUM(od.quantity)
        FROM OrderDetail od
        WHERE od.order.status = com.cju.shoppingmall.order.entity.OrderStatus.COMPLETED
            AND od.order.createdAt >= :startDate
        GROUP BY od.productVariant.product.id
        ORDER BY SUM(od.quantity) DESC
    """)
    List<Object[]> findProductSalesLast7Days(LocalDateTime startDate);
}