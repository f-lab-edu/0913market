package com.market0913.api.controller.product;

import com.market0913.domain.model.product.ProductCreator;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@NoArgsConstructor
@Getter
public class ProductRequest {

    @NotEmpty
    private String name;

    @Range(min = 1)
    private int price;

    private String imageUrl;

    private String description;

    public ProductCreator toCreator(final String sellerId) {
        return ProductCreator.builder()
                .sellerId(sellerId)
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .description(description)
                .build();
    }
}
