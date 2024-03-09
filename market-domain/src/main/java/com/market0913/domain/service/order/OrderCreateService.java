package com.market0913.domain.service.order;

import com.market0913.domain.model.market.Market;
import com.market0913.domain.model.member.Member;
import com.market0913.domain.model.member.MemberType;
import com.market0913.domain.model.order.Order;
import com.market0913.domain.model.order.OrderCreator;
import com.market0913.domain.model.order.OrderDto;
import com.market0913.domain.repository.MarketRepository;
import com.market0913.domain.repository.MemberRepository;
import com.market0913.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional
public class OrderCreateService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final MarketRepository marketRepository;

    public OrderDto createOrder(OrderCreator orderCreator) {
        Member member = memberRepository.findByMemberIdAndType(orderCreator.getMemberId(), MemberType.BUYER)
                .orElseThrow(() -> new NoSuchElementException("구매자 정보를 찾을 수 없습니다."));

        Market market = marketRepository.findById(orderCreator.getMarketId())
                .orElseThrow(() -> new NoSuchElementException("마켓 정보를 찾을 수 없습니다."));

        // 주문 금액 검증
        int orderAmount = market.getDiscountPrice() * orderCreator.getOrderQuantity();
        if(orderAmount != orderCreator.getOrderAmount()) {
            throw new IllegalArgumentException("구매 금액이 일치하지 않습니다.");
        }

        // 마켓 상태 검증

        // 재고 수량 검증

        // 인당 최대 구매 가능 수량 검증

        // 판매 기간 검증

        // 마켓 판매 수량 업데이트
        // TODO : 재고 수량 동시성 제어

        // 품절 시 마켓 상태 업데이트

        Order order = OrderCreator.createOrder(member, market, orderCreator);
        return OrderDto.from(orderRepository.save(order));
    }
}
