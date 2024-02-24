package com.market0913.api.controller.product;

import com.market0913.domain.service.product.ProductCreateService;
import com.market0913.domain.service.product.ProductReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductReadService productReadService;
    private final ProductCreateService productCreateService;

    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest request) {
        return ProductResponse.from(productCreateService.createProduct(request.newProduct()));
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable("id") Long id) {
        return productReadService.findProduct(id)
                .map(ProductResponse::from)
                .orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));
    }
}
