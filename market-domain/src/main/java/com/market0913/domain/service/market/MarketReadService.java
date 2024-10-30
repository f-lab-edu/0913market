package com.market0913.domain.service.market;

import com.market0913.domain.model.market.MarketDto;
import com.market0913.domain.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MarketReadService {

    private final MarketRepository marketRepository;

    @Cacheable(cacheNames = "market")
    public MarketDto findMarket(Long marketId) {
        return MarketDto.from(marketRepository.findByMarketIdWithProduct(marketId)
                .orElseThrow(() -> new NoSuchElementException("마켓 정보를 찾을 수 없습니다.")));
    }

    public List<MarketDto> findMarket(String sellerId) {
        return marketRepository.findBySellerIdWithProduct(sellerId).stream()
                .map(MarketDto::from)
                .collect(Collectors.toList());
    }
}
