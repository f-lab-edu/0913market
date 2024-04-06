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

DROP TABLE IF EXISTS `market`;
CREATE TABLE `market` (
    `id`                    bigint          NOT NULL                AUTO_INCREMENT,
    `product_id`            bigint          NOT NULL                COMMENT '상품 PK',
    `discount_price`        int             NOT NULL                COMMENT '할인가',
    `quantity`              int             NOT NULL                COMMENT '등록 수량',
    `sales_quantity`        int             NOT NULL                COMMENT '현재까지 판매된 개수',
    `min_sales_quantity`    int             NOT NULL                COMMENT '최소 판매 개수',
    `limit_quantity`        int             NOT NULL                COMMENT '인당 최대 구매 가능 수',
    `sales_start_date`      datetime(6)     NOT NULL                COMMENT '판매 시작 일자',
    `sales_end_date`        datetime(6)     NOT NULL                COMMENT '판매 종료 일자',
    `status`                varchar(20)     NOT NULL DEFAULT 'WAIT' COMMENT '마켓 상태',
    `created_at`            datetime(6)     NOT NULL                COMMENT '마켓 생성 일자',
    `updated_at`            datetime(6)     NULL                    COMMENT '마켓 변경 일자',
    PRIMARY KEY(`id`),
    CONSTRAINT `fk_market_to_product` FOREIGN KEY (`product_id`) REFERENCES product (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    KEY `idx_status` (`status`)
);

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
    `id`                    bigint          NOT NULL                AUTO_INCREMENT,
    `member_id`             bigint          NOT NULL                COMMENT '회원 PK',
    `market_id`             bigint          NOT NULL                COMMENT '마켓 PK',
    `order_quantity`        int             NOT NULL DEFAULT 1      COMMENT '주문 수량',
    `order_amount`          int             NOT NULL                COMMENT '주문 금액',
    `status`                varchar(20)     NOT NULL                COMMENT '주문 상태',
    `created_at`            datetime(6)     NOT NULL                COMMENT '주문 생성 일자',
    `updated_at`            datetime(6)     NULL                    COMMENT '주문 변경 일자',
    `canceled_at`           datetime(6)     NULL                    COMMENT '주문 취소 일자',
    PRIMARY KEY(`id`),
    CONSTRAINT `fk_order_to_member` FOREIGN KEY (`member_id`) REFERENCES member (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT `fk_order_to_market` FOREIGN KEY (`market_id`) REFERENCES market (`id`) ON DELETE RESTRICT ON UPDATE CASCADE,
    KEY `idx_member_id` (`member_id`)
);
