CREATE DATABASE IF NOT EXISTS `market0913`;
use `market0913`;

DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
	`id`            bigint          NOT NULL    AUTO_INCREMENT,
	`member_id`     varchar(30)     NOT NULL    COMMENT '회원 아이디',
	`type`	        varchar(20)     NOT NULL    COMMENT '회원 타입',
	PRIMARY KEY (`id`),
	CONSTRAINT `unq_id` UNIQUE (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `category` (
	`id`            bigint          NOT NULL    AUTO_INCREMENT,
	`name`	        varchar(20)     NOT NULL    COMMENT '카테고리명',
	PRIMARY KEY (`id`),
	CONSTRAINT `unq_category_id` UNIQUE (`name`)
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
	`id`            bigint          NOT NULL    AUTO_INCREMENT,
	`seller_id`     bigint          NOT NULL    COMMENT '판매 회원 PK',
	`category_id`   bigint          NOT NULL    COMMENT '카테고리 PK',
	`name`	        varchar(50)     NOT NULL    COMMENT '상품 이름',
	`price`	        int             NOT NULL    COMMENT '정가',
	`image_url`     varchar(200)	NULL        COMMENT '상품의 대표 이미지',
	`description`   text            NULL        COMMENT '상품 상세 설명',
	`created_at`    datetime(6)     NOT NULL    COMMENT '판매자가 상품을 등록한 일자',
	`updated_at`    datetime(6)     NULL        COMMENT '판매자가 상품 정보를 변경한 일자',
	PRIMARY KEY (id),
	CONSTRAINT `fk_product_to_member` FOREIGN KEY (`seller_id`) REFERENCES member (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
	CONSTRAINT `fk_product_to_category` FOREIGN KEY (`category_id`) REFERENCES category (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
	KEY `idx_category_id_created_at` (`category_id`, `created_at`),
	KEY `idx_seller_id` (`seller_id`),
	KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
