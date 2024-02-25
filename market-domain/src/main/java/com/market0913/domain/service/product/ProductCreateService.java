package com.market0913.domain.service.product;

import com.market0913.domain.model.category.Category;
import com.market0913.domain.model.category.CategoryType;
import com.market0913.domain.model.product.Product;
import com.market0913.domain.repository.CategoryRepository;
import com.market0913.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductCreateService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Product createProduct(Product product, CategoryType categoryType) {
        Category category = categoryRepository.findByName(categoryType);
        Product newProduct = new Product().builder()
                                .category(category)
                                .name(product.getName())
                                .price(product.getPrice())
                                .imageUrl(product.getImageUrl())
                                .description(product.getDescription())
                                .build();
        return productRepository.save(newProduct);
    }
}
