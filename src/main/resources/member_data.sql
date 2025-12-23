SET FOREIGN_KEY_CHECKS=0;

TRUNCATE TABLE affiliate_ad;
TRUNCATE TABLE product_review;
TRUNCATE TABLE product_qna;
TRUNCATE TABLE product_variant_option;
TRUNCATE TABLE product_variant;
TRUNCATE TABLE product;
TRUNCATE TABLE category;
TRUNCATE TABLE member;

SET FOREIGN_KEY_CHECKS=1;

INSERT INTO member (id, username, nickname, name, password, phone, email, role, created_at) VALUES
                                                                                                (1, 'user1', '닉네임1', '사용자1', 'password1', '010-1234-5678', 'user1@example.com', 'CONSUMER', NOW()),
                                                                                                (2, 'user2', '닉네임2', '사용자2', 'password2', '010-2345-6789', 'user2@example.com', 'SELLER', NOW()),
                                                                                                (3, 'user3', '닉네임3', '사용자3', 'password3', '010-3456-7890', 'user3@example.com', 'ADMIN', NOW());
