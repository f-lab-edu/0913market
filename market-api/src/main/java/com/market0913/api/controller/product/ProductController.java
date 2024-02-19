package com.market0913.api.controller.product;

import com.market0913.domain.config.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductRequest request) {
        return ProductDto.from(productService.saveProduct(request.newProduct()));
    }

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable("id") Long id) {
        return productService.findProduct(id)
                .map(ProductDto::from)
                .orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."));
    }
}
