package com.market0913.domain.model.product;

import com.market0913.domain.model.category.Category;
import com.market0913.domain.model.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private Member seller;

    private Category category;

    private String name;

    private int price;

    private String imageUrl;

    private String description;

    public static ProductDto from(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .seller(Hibernate.isInitialized(product.getSeller()) ? product.getSeller() : null)
                .category(Hibernate.isInitialized(product.getCategory()) ? product.getCategory() : null)
                .name(product.getName())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .description(product.getDescription())
                .build();
    }
}
