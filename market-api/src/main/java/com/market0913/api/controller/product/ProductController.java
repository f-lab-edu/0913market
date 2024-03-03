package com.market0913.api.controller.product;

import com.market0913.domain.service.product.ProductCreateService;
import com.market0913.domain.service.product.ProductReadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductReadService productReadService;
    private final ProductCreateService productCreateService;

    @PostMapping
    public ProductResponse createProduct(@RequestHeader("ID") String id, @Valid @RequestBody ProductRequest request) {
        return ProductResponse.from(productCreateService.createProduct(id, request.newProduct()));
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable("id") Long id) {
        return ProductResponse.from(productReadService.findProduct(id));
    }
}
