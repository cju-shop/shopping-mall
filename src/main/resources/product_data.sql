-- product 테이블 샘플 데이터
INSERT INTO product (
    id, name, description, thumbnail, base_price, discount_rate,
    category_id, created_at, created_by, updated_at, updated_by
) VALUES
      (1, '라이트 니트 카디건 #1', '포근하고 가벼운 니트 카디건입니다. 부드러운 소재로 일상복이나 데일리룩에 잘 어울립니다.', 'https://picsum.photos/seed/1/800/600', 123440, NULL,
       (SELECT id FROM category WHERE name='cardigan'), '2025-11-18 12:00:00', 1, '2025-11-18 12:00:00', 1),
      (2, '클래식 데님 청바지', '편안하고 스타일리시한 기본 청바지입니다.', 'https://picsum.photos/seed/2/800/600', 45000, 0.05,
       (SELECT id FROM category WHERE name='jeans'), '2025-11-17 10:00:00', 2, '2025-11-17 10:00:00', 2);

-- product_variant 테이블 샘플 데이터 (외래키 무결성을 위한 추가)
INSERT INTO product_variant (
    id, product_id, price, discount_rate, stock_qty, is_active, fingerprint,
    created_at, created_by, updated_at, updated_by
) VALUES
      (1001, 1, 120000, NULL, 50, TRUE, 'fp1', '2025-11-18 10:00:00', 1, '2025-11-18 10:00:00', 1),
      (2003, 2, 46000, 0.03, 30, TRUE, 'fp2', '2025-11-18 11:00:00', 2, '2025-11-18 11:00:00', 2);

-- product_qna 테이블 샘플 데이터
INSERT INTO product_qna (
    id, member_id, product_id, question, question_detail, answer, created_at, updated_at
) VALUES
      (1, 1, 1, '질문 제목 입니다.', '질문 내용입니다.', '답변 내용입니다.', '2025-11-19 08:00:00', '2025-11-19 09:00:00'),
      (2, 1, 1, '질문 제목 입니다22.', NULL, NULL, '2025-11-19 15:00:00', '2025-11-19 16:00:00'),
      (3, 2, 2, '질문 제목 입니다33.', NULL, NULL, '2025-11-19 10:00:00', '2025-11-19 10:00:00');

-- product_review 테이블 샘플 데이터
INSERT INTO product_review (
    id, member_id, product_id, product_variant_id, rating, content, created_at, updated_at
) VALUES
      (1, 1, 1, 1001, 4.7, '리뷰 테스트 데이터입니다.', '2025-11-19 12:00:00', '2025-11-19 12:00:00'),
      (2, 3, 2, 2003, 3.9, '핏이 좋지만 조금 타이트해요. 사이즈 한 단계 업 추천합니다.', '2025-11-18 14:30:00', '2025-11-18 14:30:00');
