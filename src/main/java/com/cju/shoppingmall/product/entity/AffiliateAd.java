package com.cju.shoppingmall.product.entity;

import java.time.LocalDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
public class AffiliateAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;  // 광고 이름

    @Column(nullable = false)
    private String img;   // 이미지 URL

    @Column(nullable = false)
    private String href;  // 클릭시 이동 링크

    @Column(nullable = false)
    private LocalDate startDate;  // 게시 시작일

    @Column(nullable = false)
    private LocalDate endDate;    // 게시 종료일

    @Column(nullable = false)
    private String alt; // 광고 설명


    public AffiliateAd(String name, String img, String href, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.img = img;
        this.href = href;
        this.startDate = startDate;
        this.endDate = endDate;
        this.alt = alt;
    }

    protected AffiliateAd() {
        // JPA용 기본 생성자
    }
}
