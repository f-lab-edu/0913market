package com.market0913.domain.service.product;

import com.market0913.domain.model.member.Member;
import com.market0913.domain.model.member.MemberType;
import com.market0913.domain.model.product.Product;
import com.market0913.domain.repository.MemberRepository;
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

    private final MemberRepository memberRepository;

    public Product createProduct(String id, Product product) {
        Member seller = memberRepository.findByMemberIdAndType(id, MemberType.SELLER)
                .orElseThrow(() -> new NoSuchElementException("판매자 정보를 찾을 수 없습니다."));
        product.setSeller(seller);
        return productRepository.save(product);
    }
}
