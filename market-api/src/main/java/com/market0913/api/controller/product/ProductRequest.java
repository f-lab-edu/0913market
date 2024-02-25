package com.market0913.api.controller.product;

import com.market0913.domain.model.category.Category;
import com.market0913.domain.model.category.CategoryType;
import com.market0913.domain.model.product.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.google.common.base.Preconditions.checkArgument;

@NoArgsConstructor
@Getter
public class ProductRequest {

    private String name;

    private CategoryType category;

    private int price;

    private String imageUrl;

    private String description;

    public Product newProduct() {
        checkArgument(name != null, "상품 이름은 필수 값 입니다.");
        checkArgument(price >= 0, "상품 가격은 0 이상이어야 합니다.");

        return Product.builder()
                .name(name)
                .category(new Category(category))
                .price(price)
                .imageUrl(imageUrl)
                .description(description)
                .build();
    }
}
