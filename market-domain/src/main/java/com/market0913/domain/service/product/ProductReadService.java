package com.market0913.domain.service.product;

import com.market0913.domain.model.product.Product;
import com.market0913.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductReadService {

    private final ProductRepository productRepository;

    public Optional<Product> findProduct(Long id) {
        return productRepository.findById(id);
    }
}
