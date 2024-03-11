package com.market0913.domain.model.market;

import com.market0913.domain.model.BaseTimeEntity;
import com.market0913.domain.model.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "market", indexes = {
        @Index(name = "idx_status", columnList = "status")
})
public class Market extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, columnDefinition = "bigint COMMENT '상품 PK'")
    private Product product;

    @Column(name = "discount_price", nullable = false, columnDefinition = "int COMMENT '할인가'")
    private int discountPrice;

    @Column(name = "quantity", nullable = false, columnDefinition = "int COMMENT '등록 수량'")
    private int quantity;

    @Column(name = "sales_quantity", nullable = false, columnDefinition = "int COMMENT '현재까지 판매된 개수'")
    private int salesQuantity;

    @Column(name = "min_sales_quantity", nullable = false, columnDefinition = "int COMMENT '최소 판매 개수'")
    private int minSalesQuantity;

    @Column(name = "limit_quantity", nullable = false, columnDefinition = "int COMMENT '인당 최대 구매 가능 수'")
    private int limitQuantity;

    @Column(name = "sales_start_date", nullable = false, columnDefinition = "datetime(6) COMMENT '판매 시작 일자'")
    private LocalDateTime salesStartDate;

    @Column(name = "sales_end_date", nullable = false, columnDefinition = "datetime(6) COMMENT '판매 종료 일자")
    private LocalDateTime salesEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(20) default 'WAIT'")
    private MarketStatus marketStatus;

    @Builder
    public Market(Product product, int discountPrice, int quantity, int salesQuantity, int minSalesQuantity, int limitQuantity, LocalDateTime salesStartDate, LocalDateTime salesEndDate, MarketStatus marketStatus) {
        this.product = product;
        this.discountPrice = discountPrice;
        this.quantity = quantity;
        this.salesQuantity = salesQuantity;
        this.minSalesQuantity = minSalesQuantity;
        this.limitQuantity = limitQuantity;
        this.salesStartDate = salesStartDate;
        this.salesEndDate = salesEndDate;
        this.marketStatus = marketStatus;
    }

    // 마켓 판매 수량 증가
    public Market addSalesQuantity(final int orderQuantity) {
        this.salesQuantity += orderQuantity;
        if(this.quantity == this.salesQuantity) {
            soldOut();
        }
        return this;
    }

    // 마켓 품절 상태로 변경
    public Market soldOut() {
        if(this.quantity != this.salesQuantity) {
            throw new IllegalArgumentException("판매 가능 수량이 남아있습니다.");
        }
        this.marketStatus = MarketStatus.SOLD_OUT;
        return this;
    }

    // 마켓 구매 확정 상태로 변경
    private Market available() {
        this.marketStatus = MarketStatus.AVAILABLE;
        return this;
    }

    // 마켓 구매 불가 상태로 변경
    public Market notAvailable() {
        this.marketStatus = MarketStatus.NOT_AVAILABLE;
        return this;
    }
}
