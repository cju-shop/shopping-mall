package com.cju.shoppingmall.product.service;

import java.util.List;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cju.shoppingmall.product.repository.AffiliateAdRepository;

@Service
@Transactional(readOnly = true)
public class AffiliateAdService {

    private final AffiliateAdRepository affiliateAdRepository;

    public AffiliateAdService(AffiliateAdRepository affiliateAdRepository) {
        this.affiliateAdRepository = affiliateAdRepository;
    }

    // 현재 날짜 기준으로 노출해야 하는 활성 광고 목록 조회
    public List<com.cju.shoppingmall.product.entity.AffiliateAd> getActiveAds() {
        LocalDate now = LocalDate.now();
        return affiliateAdRepository.findAll().stream()
                .filter(ad -> !now.isBefore(ad.getStartDate()) && !now.isAfter(ad.getEndDate()))
                .toList();
    }


}
