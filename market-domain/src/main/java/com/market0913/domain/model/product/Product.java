package com.market0913.domain.model.product;

import com.market0913.domain.model.BaseTimeEntity;
import com.market0913.domain.model.category.Category;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.google.common.base.Preconditions.checkArgument;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "product")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(50) NOT NULL COMMENT '상품 이름'")
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "varchar(50) NOT NULL COMMENT '정가'")
    private int price;

    @Column(name = "image_url", columnDefinition = "varchar(200) NULL COMMENT '상품의 대표 이미지'")
    private String imageUrl;

    @Column(name = "description", columnDefinition = "text NULL COMMENT '상품 상세 설명'")
    private String description;

    @Builder
    public Product(Category category, String name, int price, String imageUrl, String description) {
        checkArgument(name != null, "상품 이름은 필수 값 입니다.");
        checkArgument(price >= 0, "상품 가격은 0 이상이어야 합니다.");

        this.category = category;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
