CREATE DATABASE IF NOT EXISTS `market0913`;
use `market0913`;

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
	`id`	INT	NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`name`	VARCHAR(50)	NOT NULL	COMMENT '상품 이름',
	`price`	INT	NOT NULL	DEFAULT 0	COMMENT '정가',
	`image_url`	VARCHAR(200)	NULL	COMMENT '상품의 대표 이미지',
	`description`	TEXT	NULL	COMMENT '상품 상세 설명',
	`created_at`	DATETIME	NOT NULL	COMMENT '판매자가 상품을 등록한 일자',
	`updated_at`	DATETIME	NULL	COMMENT '판매자가 상품 정보를 변경한 일자'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;