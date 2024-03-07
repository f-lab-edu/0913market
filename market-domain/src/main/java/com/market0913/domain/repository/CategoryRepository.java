package com.market0913.domain.repository;

import com.market0913.domain.model.category.Category;
import com.market0913.domain.model.category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(CategoryType name);
}
