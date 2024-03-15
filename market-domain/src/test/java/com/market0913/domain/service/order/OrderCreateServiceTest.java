package com.market0913.domain.service.order;

import com.market0913.domain.config.MarketDomainConfig;
import com.market0913.domain.model.category.Category;
import com.market0913.domain.model.category.CategoryType;
import com.market0913.domain.model.market.Market;
import com.market0913.domain.model.market.MarketStatus;
import com.market0913.domain.model.member.Member;
import com.market0913.domain.model.member.MemberType;
import com.market0913.domain.model.order.OrderCreator;
import com.market0913.domain.model.product.Product;
import com.market0913.domain.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MarketDomainConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrderCreateServiceTest {

    @Autowired private MemberRepository memberRepository;
    @Autowired private MarketRepository marketRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private OrderRepository orderRepository;

    @Autowired private OrderCreateService orderCreateService;

    @BeforeAll
    public void setUp() {
        init();
    }

    @BeforeEach
    public void deleteData() {
        deleteOrderData();
        deleteMarketData();
    }

    private Category initCategory() {
        Category category = new Category(CategoryType.COSMETIC);
        return categoryRepository.save(category);
    }

    private Member initMember() {
        Member seller = new Member(1L, "spring0913", MemberType.SELLER);
        memberRepository.save(seller);
        Member buyer = new Member(2L, "happycat", MemberType.BUYER);
        memberRepository.save(buyer);
        return seller;
    }

    private void init() {
        Product product = Product.builder()
                .id(1L)
                .category(initCategory())
                .seller(initMember())
                .name("테스트 상품")
                .price(10000)
                .description("상품 설명")
                .imageUrl("http://imagetest.com")
                .build();
        productRepository.save(product);
    }

    private void deleteMarketData() {
        marketRepository.deleteAll();
    }

    private void deleteOrderData() {
        orderRepository.deleteAll();
    }

    @Test
    @DisplayName("주문 생성 시 판매 수량 증가 테스트")
    public void createOrderTest() {
        // given
        int salesQuantity = 0;
        int orderQuantity = 2;
        int expectedResult = 2;

        Market market = Market.builder()
                .product(getProduct())
                .discountPrice(1000)
                .quantity(100)
                .salesQuantity(salesQuantity)
                .minSalesQuantity(100)
                .limitQuantity(5)
                .salesStartDate(LocalDateTime.now().minusDays(1))
                .salesEndDate(LocalDateTime.now().plusDays(1))
                .marketStatus(MarketStatus.WAIT)
                .build();
        Long marketId = marketRepository.save(market).getId();

        OrderCreator orderCreator = OrderCreator.builder()
                .memberId("happycat")
                .marketId(marketId)
                .orderQuantity(orderQuantity)
                .orderAmount(orderQuantity * market.getDiscountPrice())
                .build();

        // when
        orderCreateService.createOrder(orderCreator);

        // then
        Market orderedMarket = marketRepository.findById(marketId).get();
        assertEquals(expectedResult, orderedMarket.getSalesQuantity());
    }

    private Product getProduct() {
        return productRepository.findById(1L).get();
    }

    @Test
    @DisplayName("주문 생성 후 재고 수량 없을 시 마켓 품절 상태로 업데이트 테스트")
    public void orderUpdateMarketStatusTest() {
        // given
        int salesQuantity = 95;
        int orderQuantity = 5;
        MarketStatus expectedResult = MarketStatus.SOLD_OUT;

        Market market = Market.builder()
                .product(getProduct())
                .discountPrice(1000)
                .quantity(100)
                .salesQuantity(salesQuantity)
                .minSalesQuantity(100)
                .limitQuantity(5)
                .salesStartDate(LocalDateTime.now().minusDays(1))
                .salesEndDate(LocalDateTime.now().plusDays(1))
                .marketStatus(MarketStatus.WAIT)
                .build();
        Long marketId = marketRepository.save(market).getId();

        OrderCreator orderCreator = OrderCreator.builder()
                .memberId("happycat")
                .marketId(marketId)
                .orderQuantity(orderQuantity)
                .orderAmount(orderQuantity * market.getDiscountPrice())
                .build();

        // when
        orderCreateService.createOrder(orderCreator);

        // then
        Market orderedMarket = marketRepository.findById(marketId).get();
        assertEquals(expectedResult, orderedMarket.getMarketStatus());
    }

    @Test
    @DisplayName("주문 금액이 일치하지 않을 경우 주문 실패 테스트")
    public void orderAmountFailTest() {
        int wrongOrderAmount = 1000;

        Market market = Market.builder()
                .product(getProduct())
                .discountPrice(1000)
                .quantity(100)
                .salesQuantity(75)
                .minSalesQuantity(100)
                .limitQuantity(5)
                .salesStartDate(LocalDateTime.now().minusDays(1))
                .salesEndDate(LocalDateTime.now().plusDays(1))
                .marketStatus(MarketStatus.WAIT)
                .build();
        marketRepository.save(market);

        OrderCreator orderCreator = OrderCreator.builder()
                .memberId("happycat")
                .marketId(market.getId())
                .orderQuantity(2)
                .orderAmount(wrongOrderAmount)
                .build();

        assertThrows(IllegalArgumentException.class, () -> orderCreateService.createOrder(orderCreator));
    }
}