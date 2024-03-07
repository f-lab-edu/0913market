package com.market0913.domain.service.product;

import com.market0913.domain.model.member.Member;
import com.market0913.domain.model.member.MemberType;
import com.market0913.domain.model.product.Product;
import com.market0913.domain.model.product.ProductCreator;
import com.market0913.domain.model.product.ProductDto;
import com.market0913.domain.repository.MemberRepository;
import com.market0913.domain.model.category.Category;
import com.market0913.domain.repository.CategoryRepository;
import com.market0913.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductCreateService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public ProductDto createProduct(ProductCreator productCreator) {
        Member seller = memberRepository.findByMemberIdAndType(productCreator.getSellerId(), MemberType.SELLER)
                .orElseThrow(() -> new NoSuchElementException("판매자 정보를 찾을 수 없습니다."));

        Category category = categoryRepository.findByName(productCreator.getCategory());
        Product product = ProductCreator.createProduct(seller, category, productCreator);

        return ProductDto.from(productRepository.save(product));
    }
}
