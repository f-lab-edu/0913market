CREATE DATABASE IF NOT EXISTS `market0913`;
use `market0913`;

DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
	`member_id`     bigint          NOT NULL    AUTO_INCREMENT,
	`id`	        varchar(30)     NOT NULL    COMMENT '회원 아이디',
	`type`	        varchar(20)     NOT NULL    COMMENT '회원 타입',
	PRIMARY KEY (`member_id`),
	CONSTRAINT `unq_id` UNIQUE (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
	`id`            bigint          NOT NULL    AUTO_INCREMENT,
	`member_id`     bigint          NOT NULL    COMMENT '판매 회원 PK',
	`name`	        varchar(50)     NOT NULL    COMMENT '상품 이름',
	`price`	        int             NOT NULL    COMMENT '정가',
	`image_url`     varchar(200)	NULL        COMMENT '상품의 대표 이미지',
	`description`   text            NULL        COMMENT '상품 상세 설명',
	`created_at`    datetime(6)     NOT NULL    COMMENT '판매자가 상품을 등록한 일자',
	`updated_at`    datetime(6)     NULL        COMMENT '판매자가 상품 정보를 변경한 일자',
	PRIMARY KEY (id),
	CONSTRAINT `fk_product_to_member` FOREIGN KEY (`member_id`) REFERENCES member (`member_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
	KEY `idx_id` (`member_id`),
	KEY `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

