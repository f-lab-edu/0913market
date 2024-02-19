package com.market0913.domain.config.repository;

import com.market0913.domain.config.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
