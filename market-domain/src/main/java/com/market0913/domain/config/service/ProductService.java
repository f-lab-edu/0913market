package com.market0913.domain.config.service;

import com.market0913.domain.config.model.product.Product;
import com.market0913.domain.config.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Optional<Product> findProduct(Long id) {
        return productRepository.findById(id);
    }
}
