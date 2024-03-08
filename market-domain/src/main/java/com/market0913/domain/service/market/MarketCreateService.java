package com.market0913.domain.service.market;

import com.market0913.domain.model.market.Market;
import com.market0913.domain.model.market.MarketCreator;
import com.market0913.domain.model.market.MarketDto;
import com.market0913.domain.model.product.ProductCreator;
import com.market0913.domain.model.product.ProductDto;
import com.market0913.domain.repository.MarketRepository;
import com.market0913.domain.service.product.ProductCreateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MarketCreateService {

    private final MarketRepository marketRepository;

    private final ProductCreateService productCreateService;

    public MarketDto createMarket(MarketCreator marketCreator) {
        ProductCreator productCreator = new ProductCreator(marketCreator);
        ProductDto productDto = productCreateService.createProduct(productCreator);
        Market market = MarketCreator.createMarket(marketCreator, productDto);
        return MarketDto.from(marketRepository.save(market));
    }
}
