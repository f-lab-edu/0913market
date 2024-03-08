package com.market0913.domain.service.market;

import com.market0913.domain.model.market.MarketDto;
import com.market0913.domain.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MarketReadService {

    private final MarketRepository marketRepository;

    public MarketDto findMarket(Long id) {
        return MarketDto.from(marketRepository.findByIdWithProduct(id)
                .orElseThrow(() -> new NoSuchElementException("마켓 정보를 찾을 수 없습니다.")));
    }
}
