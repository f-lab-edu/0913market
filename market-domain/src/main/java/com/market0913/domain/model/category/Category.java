package com.market0913.domain.model.category;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, columnDefinition = "varchar(20) COMMENT '카테고리명'")
    @Enumerated(EnumType.STRING)
    private CategoryType name;

    @Builder
    public Category(CategoryType categoryType) {
        this.name = categoryType;
    }
}
