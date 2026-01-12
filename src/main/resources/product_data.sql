-- product 테이블 샘플 데이터
INSERT INTO product (
    id, name, description, thumbnail, base_price, discount_rate,
    rating, category_id, created_at, created_by, updated_at, updated_by
) VALUES
-- 1
(1, '라이트 니트 카디건 #1',
 '포근하고 가벼운 니트 카디건입니다. 부드러운 소재로 일상복이나 데일리룩에 잘 어울립니다.',
 'https://picsum.photos/seed/1/800/600',
 123440, NULL,
 4.7,
 (SELECT id FROM category WHERE name='cardigan'),
 '2025-11-18 12:00:00', 1, '2025-11-18 12:00:00', 1),

-- 2
(2, '클래식 데님 청바지',
 '편안하고 스타일리시한 기본 청바지입니다.',
 'https://picsum.photos/seed/2/800/600',
 45000, 0.05,
 3.9,
 (SELECT id FROM category WHERE name='jeans'),
 '2025-11-17 10:00:00', 2, '2025-11-17 10:00:00', 2),

-- 3
(3, '클래식 코튼 셔츠',
 '사계절 활용 가능한 깔끔한 셔츠입니다.',
 'https://picsum.photos/seed/3/800/600',
 52000, 0.10,
 4.2,
 (SELECT id FROM category WHERE name='shirts'),
 '2025-11-18 11:00:00', 2, '2025-11-18 11:00:00', 2),

-- 4
(4, '클래식 테일러드 자켓',
 '단정한 핏의 데일리 자켓입니다.',
 'https://picsum.photos/seed/4/800/600',
 68000, 0.15,
 4.6,
 (SELECT id FROM category WHERE name='jacket'),
 '2025-11-19 12:00:00', 2, '2025-11-19 12:00:00', 2),

-- 5
(5, '클래식 라운드 니트',
 '부드러운 촉감의 기본 니트입니다.',
 'https://picsum.photos/seed/5/800/600',
 39000, 0.00,
 3.8,
 (SELECT id FROM category WHERE name='knit'),
 '2025-11-20 13:00:00', 2, '2025-11-20 13:00:00', 2),

-- 6
(6, '클래식 슬랙스 팬츠',
 '포멀한 룩에 잘 어울리는 슬랙스입니다.',
 'https://picsum.photos/seed/6/800/600',
 74000, 0.20,
 4.7,
 (SELECT id FROM category WHERE name='pants'),
 '2025-11-21 14:00:00', 2, '2025-11-21 14:00:00', 2),

-- 7
(7, '클래식 울 코트',
 '유행을 타지 않는 디자인의 코트입니다.',
 'https://picsum.photos/seed/7/800/600',
 88000, 0.10,
 4.9,
 (SELECT id FROM category WHERE name='coat'),
 '2025-11-22 15:00:00', 2, '2025-11-22 15:00:00', 2),

-- 8
(8, '클래식 베이직 티셔츠',
 '데일리로 입기 좋은 베이직 티셔츠입니다.',
 'https://picsum.photos/seed/8/800/600',
 32000, 0.00,
 3.5,
 (SELECT id FROM category WHERE name='tshirt'),
 '2025-11-23 16:00:00', 2, '2025-11-23 16:00:00', 2),

-- 9
(9, '클래식 데님 스커트',
 '단정한 실루엣의 데님 스커트입니다.',
 'https://picsum.photos/seed/9/800/600',
 56000, 0.05,
 4.0,
 (SELECT id FROM category WHERE name='skirt'),
 '2025-11-24 17:00:00', 2, '2025-11-24 17:00:00', 2),

-- 10
(10, '클래식 가죽 로퍼',
 '포멀한 착장에 잘 어울리는 로퍼입니다.',
 'https://picsum.photos/seed/10/800/600',
 61000, 0.10,
 4.3,
 (SELECT id FROM category WHERE name='shoes'),
 '2025-11-25 10:00:00', 2, '2025-11-25 10:00:00', 2),

-- 11
(11, '클래식 니트 가디건',
 '캐주얼하게 매치하기 좋은 가디건입니다.',
 'https://picsum.photos/seed/11/800/600',
 43000, 0.00,
 3.7,
 (SELECT id FROM category WHERE name='cardigan'),
 '2025-11-26 11:00:00', 2, '2025-11-26 11:00:00', 2),

-- 12
(12, '클래식 더블 자켓',
 '격식 있는 자리에도 어울리는 재킷입니다.',
 'https://picsum.photos/seed/12/800/600',
 98000, 0.15,
 4.8,
 (SELECT id FROM category WHERE name='jacket'),
 '2025-11-27 12:00:00', 2, '2025-11-27 12:00:00', 2),

-- 13
(13, '클래식 베이직 셔츠',
 '가볍게 입기 좋은 기본 셔츠입니다.',
 'https://picsum.photos/seed/13/800/600',
 36000, 0.00,
 3.6,
 (SELECT id FROM category WHERE name='shirts'),
 '2025-11-28 13:00:00', 2, '2025-11-28 13:00:00', 2),

-- 14
(14, '클래식 코튼 팬츠',
 '정갈한 핏의 면 팬츠입니다.',
 'https://picsum.photos/seed/14/800/600',
 67000, 0.10,
 4.1,
 (SELECT id FROM category WHERE name='pants'),
 '2025-11-29 14:00:00', 2, '2025-11-29 14:00:00', 2),

-- 15
(15, '클래식 미디 스커트',
 '단정한 디자인의 미디 스커트입니다.',
 'https://picsum.photos/seed/15/800/600',
 54000, 0.05,
 3.9,
 (SELECT id FROM category WHERE name='skirt'),
 '2025-11-30 15:00:00', 2, '2025-11-30 15:00:00', 2),

-- 16
(16, '클래식 터틀넥 니트',
 '겨울철 데일리로 활용하기 좋은 니트입니다.',
 'https://picsum.photos/seed/16/800/600',
 72000, 0.10,
 4.4,
 (SELECT id FROM category WHERE name='knit'),
 '2025-12-01 16:00:00', 2, '2025-12-01 16:00:00', 2),

-- 17
(17, '클래식 울 팬츠',
 '고급스러운 소재의 울 팬츠입니다.',
 'https://picsum.photos/seed/17/800/600',
 85000, 0.15,
 4.6,
 (SELECT id FROM category WHERE name='pants'),
 '2025-12-02 17:00:00', 2, '2025-12-02 17:00:00', 2),

-- 18
(18, '클래식 싱글 코트',
 '격식과 캐주얼 모두 어울리는 코트입니다.',
 'https://picsum.photos/seed/18/800/600',
 99000, 0.20,
 4.8,
 (SELECT id FROM category WHERE name='coat'),
 '2025-12-03 18:00:00', 2, '2025-12-03 18:00:00', 2);


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
