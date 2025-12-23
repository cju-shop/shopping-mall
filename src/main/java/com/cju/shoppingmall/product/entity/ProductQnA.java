package com.cju.shoppingmall.product.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.cju.shoppingmall.member.entity.Member;
import jakarta.persistence.*;

import lombok.Getter;

@Entity
@Getter
public class ProductQnA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;               // 질문 작성자

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;             // 관련 상품

    @Column(nullable = false)
    private String question;             // 질문 내용

    private String questionDetail;       // 질문 상세 (선택사항)

    private String answer;               // 답변 내용 (null이면 미답변 상태)

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public boolean hasAnswer() {
        return answer != null && !answer.isBlank();
    }
}
