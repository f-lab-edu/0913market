package com.market0913.domain.model.order;

import com.market0913.domain.model.BaseTimeEntity;
import com.market0913.domain.model.market.Market;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id", nullable = false, columnDefinition = "bigint COMMENT '마켓 PK'")
    private Market market;

    @Column(name = "order_quantity", nullable = false, columnDefinition = "int COMMENT '주문 수량'")
    private int orderQuantity;

    @Column(name = "order_amount", nullable = false, columnDefinition = "int COMMENT '주문 금액'")
    private int orderAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "varchar(20) default 'PU_WAIT' COMMENT '주문 상태'")
    private OrderStatus orderStatus;

    @Column(name = "canceled_at", columnDefinition = "varchar(20) default 'PU_WAIT' COMMENT '주문 상태'")
    private LocalDateTime canceledAt;

    private LocalDateTime createdAt;

    @Builder
    public Order(Long id, Long memberId, Market market, int orderQuantity, int orderAmount, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime canceledAt) {
        this.id = id;
        this.memberId = memberId;
        this.market = market;
        this.orderQuantity = orderQuantity;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.canceledAt = canceledAt;
    }

    public void placeOrder(OrderValidator orderValidator) {
        orderValidator.validate(this);
        orderStatus = OrderStatus.PU_WAIT;
    }
}
