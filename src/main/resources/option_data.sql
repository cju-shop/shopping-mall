-- ===============================
-- 개발용 초기화 (FK 순서 중요)
-- ===============================
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM product_variant_option;
DELETE FROM product_variant;
DELETE FROM product_option;
DELETE FROM option_value;
DELETE FROM option_type;

SET FOREIGN_KEY_CHECKS = 1;

-- ===============================
-- 옵션 타입
-- ===============================
INSERT INTO option_type (id, name, display_name)
VALUES
    (1, 'size',  '사이즈'),
    (2, 'color', '색상');

-- ===============================
-- 옵션 값
-- ===============================
INSERT INTO option_value (id, option_type_id, value)
VALUES
    (1, 1, 'Free'),
    (2, 1, 'L'),
    (3, 2, 'Green'),
    (4, 2, 'Beige');

-- ===============================
-- 상품 ↔ 옵션 타입 매핑
-- (이거 없으면 옵션 안 뜨는 구조 많음)
-- ===============================
INSERT INTO product_option (product_id, option_type_id)
VALUES
    (1, 1),
    (1, 2);

-- ===============================
-- 상품 변형 (Variant)
-- ===============================
INSERT INTO product_variant
(id, product_id, price, discount_rate, stock_qty, is_active, fingerprint, created_at, created_by)
VALUES
    (1001, 1, 120000, NULL, 10, true, '1-3', NOW(), 1), -- Free + Green
    (2003, 1,  46000, 0.03, 5,  true, '2-4', NOW(), 1), -- L + Beige
    (3001, 1, 120000, NULL, 10, true, '1-4', NOW(), 1), -- Free + Beige
    (3002, 1, 120000, NULL, 10, true, '2-3', NOW(), 1);
-- ===============================
-- 상품 변형 ↔ 옵션 값 매핑 (핵심)
-- ===============================
INSERT INTO product_variant_option
(product_variant_id, option_value_id)
VALUES
    (1001, 1), -- Free
    (1001, 3), -- Green

    (2003, 2), -- L
    (2003, 4), -- Beige

    (3001, 1), -- Free
    (3001, 4), -- Beige

    (3002, 2), -- L
    (3002, 3); -- Green
