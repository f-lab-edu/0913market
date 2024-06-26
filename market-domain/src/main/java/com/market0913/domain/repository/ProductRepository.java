package com.market0913.domain.repository;

import com.market0913.domain.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id);

    @Query("select p from Product p join fetch p.seller join fetch p.category where p.id=:id")
    Optional<Product> findByIdWithSellerAndCategory(Long id);
}
