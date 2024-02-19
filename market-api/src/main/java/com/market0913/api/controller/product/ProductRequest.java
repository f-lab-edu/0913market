package com.market0913.api.controller.product;

import com.market0913.domain.config.model.product.Product;
import lombok.Getter;

@Getter
public class ProductRequest {

    private String name;

    private int price;

    private String imageUrl;

    private String description;

    public Product newProduct() {
        return Product.builder()
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .description(description)
                .build();
    }
}
