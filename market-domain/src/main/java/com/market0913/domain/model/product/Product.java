package com.market0913.domain.model.product;

import com.market0913.domain.model.BaseTimeEntity;
import com.market0913.domain.model.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "product", indexes = {
        @Index(name = "idx_seller_id", columnList = "seller_id"),
        @Index(name = "idx_created_at", columnList = "created_at")
})
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false, columnDefinition = "bigint COMMENT '회원 PK'")
    private Member seller;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(50) COMMENT '상품 이름'")
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "varchar(50) COMMENT '정가'")
    private int price;

    @Column(name = "image_url", columnDefinition = "varchar(200) COMMENT '상품의 대표 이미지'")
    private String imageUrl;

    @Column(name = "description", columnDefinition = "text COMMENT '상품 상세 설명'")
    private String description;

    @Builder
    public Product(Member seller, String name, int price, String imageUrl, String description) {
        this.seller = seller;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
