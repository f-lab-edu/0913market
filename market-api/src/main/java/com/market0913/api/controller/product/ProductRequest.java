package com.market0913.api.controller.product;

import com.market0913.domain.model.category.CategoryType;
import com.market0913.domain.model.product.ProductCreator;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private CategoryType category;

    private String imageUrl;

    private String description;

    public ProductCreator toCreator(final String sellerId) {
        return ProductCreator.builder()
                .sellerId(sellerId)
                .category(category)
                .name(name)
                .price(price)
                .imageUrl(imageUrl)
                .description(description)
                .build();
    }
}
