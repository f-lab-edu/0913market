package com.market0913.api.controller.order;

import com.market0913.domain.service.order.OrderCreateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderCreateService orderCreateService;

    @PostMapping
    public OrderResponse createOrder(@RequestHeader("USER_ID") String userId, @Valid @RequestBody OrderRequest orderRequest) {
        return OrderResponse.from(orderCreateService.createOrder(orderRequest.toCreator(userId)));
    }
}
