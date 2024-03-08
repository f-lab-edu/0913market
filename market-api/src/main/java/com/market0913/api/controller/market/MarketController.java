package com.market0913.api.controller.market;

import com.market0913.domain.service.market.MarketCreateService;
import com.market0913.domain.service.market.MarketReadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {

    private final MarketCreateService marketCreateService;
    private final MarketReadService marketReadService;

    @PostMapping
    public MarketResponse createMarket(@RequestHeader("USER_ID") String userId, @Valid @RequestBody MarketRequest marketRequest) {
        return MarketResponse.from(marketCreateService.createMarket(marketRequest.toCreator(userId)));
    }

    @GetMapping("/{productId}")
    public MarketResponse getMarket(@PathVariable("productId") Long id) {
        return MarketResponse.from(marketReadService.findMarket(id));
    }
}
