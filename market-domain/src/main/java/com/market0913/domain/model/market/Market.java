package com.market0913.domain.model.market;

import com.market0913.domain.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "market", indexes = {
        @Index(name = "idx_seller_id", columnList = "seller_id"),
        @Index(name = "idx_status", columnList = "status")
})
public class Market extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "product_id", nullable = false, columnDefinition = "bigint COMMENT '상품 PK'")
    private Long productId;

    @JoinColumn(name = "seller_id", nullable = false, columnDefinition = "bigint COMMENT '회원 PK'")
    private Long sellerId;

    @Column(name = "discount_price", nullable = false, columnDefinition = "int COMMENT '할인가'")
    private int discountPrice;

    @Column(name = "quantity", nullable = false, columnDefinition = "int COMMENT '등록 수량'")
    private Integer quantity;

    @Column(name = "sales_quantity", nullable = false, columnDefinition = "int COMMENT '현재까지 판매된 개수'")
    private Integer salesQuantity;

    @Column(name = "min_sales_quantity", nullable = false, columnDefinition = "int COMMENT '최소 판매 개수'")
    private Integer minSalesQuantity;

    @Column(name = "limit_quantity", nullable = false, columnDefinition = "int COMMENT '인당 최대 구매 가능 수'")
    private Integer limitQuantity;

    @Column(name = "sales_start_date", nullable = false, columnDefinition = "datetime(6) COMMENT '판매 시작 일자'")
    private LocalDateTime salesStartDate;

    @Column(name = "sales_end_date", nullable = false, columnDefinition = "datetime(6) COMMENT '판매 종료 일자")
    private LocalDateTime salesEndDate;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(20) default 'WAIT'")
    private MarketStatus marketStatus = MarketStatus.WAIT;
}
