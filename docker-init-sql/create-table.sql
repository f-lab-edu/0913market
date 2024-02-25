CREATE DATABASE IF NOT EXISTS `market0913`;
use `market0913`;

CREATE TABLE `category` (
	`id`            bigint          NOT NULL    AUTO_INCREMENT,
	`name`	        varchar(20)     NOT NULL    COMMENT '카테고리명',
	PRIMARY KEY (`id`),
	CONSTRAINT `unq_category_id` UNIQUE (`name`)
);

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
	`id`            bigint          NOT NULL    AUTO_INCREMENT,
	`category_id`   bigint          NOT NULL    COMMENT '카테고리 PK',
	`name`	        varchar(50)     NOT NULL    COMMENT '상품 이름',
	`price`	        bigint          NOT NULL    COMMENT '정가',
	`image_url`     varchar(200)	NULL        COMMENT '상품의 대표 이미지',
	`description`   text            NULL        COMMENT '상품 상세 설명',
	`created_at`    datetime(6)     NOT NULL    COMMENT '판매자가 상품을 등록한 일자',
	`updated_at`    datetime(6)     NULL        COMMENT '판매자가 상품 정보를 변경한 일자',
	PRIMARY KEY (id),
	KEY `index_id` (`id`),
	KEY `index_name` (`name`),
	KEY `index_created_at` (`created_at`),
	CONSTRAINT `fk_product_to_category` FOREIGN KEY (`category_id`) REFERENCES category (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;