package com.market0913.domain.model.product;

import com.market0913.domain.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "product")
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(50) NOT NULL COMMENT '상품 이름'")
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "varchar(50) NOT NULL COMMENT '정가'")
    private int price;

    @Column(name = "image_url", columnDefinition = "varchar(200) NULL COMMENT '상품의 대표 이미지'")
    private String imageUrl;

    @Column(name = "description", columnDefinition = "text NULL COMMENT '상품 상세 설명'")
    private String description;

    @Builder
    public Product(String name, int price, String imageUrl, String description) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
