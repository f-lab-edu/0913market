package com.market0913.domain.service.product;

import com.market0913.domain.model.product.ProductDto;
import com.market0913.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ProductReadService {

    private final ProductRepository productRepository;

    public ProductDto findProduct(Long id) {
        return ProductDto.from(productRepository.findByIdWithSellerAndCategory(id)
                .orElseThrow(() -> new NoSuchElementException("상품 정보를 찾을 수 없습니다."))
        );
    }
}
