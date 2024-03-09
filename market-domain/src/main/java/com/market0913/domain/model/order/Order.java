package com.market0913.domain.model.order;

import com.market0913.domain.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "`order`", indexes = {
        @Index(name = "idx_member_id", columnList = "member_id")
})
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false, columnDefinition = "bigint COMMENT '회원 PK'")
    private Long memberId;

    @Column(name = "market_id", nullable = false, columnDefinition = "bigint COMMENT '마켓 PK'")
    private Long marketId;

    @Column(name = "order_quantity", nullable = false, columnDefinition = "int COMMENT '주문 수량'")
    private int orderQuantity;

    @Column(name = "order_amount", nullable = false, columnDefinition = "int COMMENT '주문 금액'")
    private int orderAmount;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(20) default 'PU_WAIT' COMMENT '주문 상태'")
    private OrderStatus orderStatus;

    @Column(name = "canceled_at", columnDefinition = "varchar(20) default 'PU_WAIT' COMMENT '주문 상태'")
    private LocalDateTime canceledAt;

    @Builder
    public Order(Long id, Long memberId, Long marketId, int orderQuantity, int orderAmount, OrderStatus orderStatus, LocalDateTime canceledAt) {
        this.id = id;
        this.memberId = memberId;
        this.marketId = marketId;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.canceledAt = canceledAt;
    }
}
