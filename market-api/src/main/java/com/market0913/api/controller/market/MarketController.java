package com.market0913.api.controller.market;

import com.market0913.domain.service.market.MarketCreateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {

    private final MarketCreateService marketCreateService;

    @PostMapping
    public MarketResponse createMarket(@RequestHeader("ID") String id, @Valid @RequestBody MarketRequest marketRequest) {
        return MarketResponse.from(marketCreateService.createMarket(marketRequest.toCreator(id)));
    }
}
