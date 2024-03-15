package com.market0913.domain.service.order;

import com.market0913.domain.model.market.Market;
import com.market0913.domain.model.member.Member;
import com.market0913.domain.model.member.MemberType;
import com.market0913.domain.model.order.Order;
import com.market0913.domain.model.order.OrderCreator;
import com.market0913.domain.model.order.OrderDto;
import com.market0913.domain.model.order.OrderValidator;
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

    private final OrderValidator orderValidator;

    public OrderDto createOrder(OrderCreator orderCreator) {
        Member member = memberRepository.findByMemberIdAndType(orderCreator.getMemberId(), MemberType.BUYER)
                .orElseThrow(() -> new NoSuchElementException("구매자 정보를 찾을 수 없습니다."));

        Market market = marketRepository.findByIdWithLock(orderCreator.getMarketId())
                .orElseThrow(() -> new NoSuchElementException("마켓 정보를 찾을 수 없습니다."));

        Order order = OrderCreator.createOrder(member, market, orderCreator);
        order.placeOrder(orderValidator);

        market.addSalesQuantity(order.getOrderQuantity());

        return OrderDto.from(orderRepository.save(order));
    }
}
