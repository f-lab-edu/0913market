package com.market0913.domain.model.product;

import com.market0913.domain.model.category.Category;
import com.market0913.domain.model.category.CategoryType;
import com.market0913.domain.model.market.MarketCreator;
import com.market0913.domain.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ProductCreator {

    private String sellerId;

    private CategoryType category;

    private String name;

    private int price;

    private String imageUrl;

    private String description;

    public static Product createProduct(Member seller, Category category, ProductCreator creator) {
        return Product.builder()
                .seller(seller)
                .category(category)
                .name(creator.getName())
                .price(creator.getPrice())
                .imageUrl(creator.getImageUrl())
                .description(creator.getDescription())
                .build();
    }

    public ProductCreator(MarketCreator creator) {
        this.sellerId = creator.getSellerId();
        this.category = creator.getCategory();
        this.name = creator.getName();
        this.price = creator.getPrice();
        this.imageUrl = creator.getImageUrl();
        this.description = creator.getDescription();
    }
}
