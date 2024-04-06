package com.market0913.batch.job.step;

import com.market0913.batch.job.UpdateStatusJobConfig;
import com.market0913.domain.config.MarketDomainConfig;
import com.market0913.domain.model.category.Category;
import com.market0913.domain.model.category.CategoryType;
import com.market0913.domain.model.market.Market;
import com.market0913.domain.model.market.MarketStatus;
import com.market0913.domain.model.member.Member;
import com.market0913.domain.model.member.MemberType;
import com.market0913.domain.model.product.Product;
import com.market0913.domain.repository.CategoryRepository;
import com.market0913.domain.repository.MarketRepository;
import com.market0913.domain.repository.MemberRepository;
import com.market0913.domain.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.batch.core.*;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBatchTest
@SpringBootTest(classes = {
        MarketStatusStepTest.BatchTestConfig.class,
        UpdateStatusJobConfig.class, MarketStatusStepConfig.class,
        MarketDomainConfig.class
})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MarketStatusStepTest {

    @Autowired private JobLauncherTestUtils jobLauncherTestUtils;
    @Autowired private JpaPagingItemReader<Market> reader;

    @Autowired private MemberRepository memberRepository;
    @Autowired private MarketRepository marketRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;

    private static final LocalDateTime requestDate = LocalDateTime.of(2024, 9, 13, 0, 0);

    @BeforeAll
    public void setUp() {
        init();
    }

    @BeforeEach
    public void deleteData() {
        deleteMarketData();
    }

    @Test
    @DisplayName("상태값이 WAIT 인 마켓이 최소 판매 개수를 달성했을 경우 AVAILABLE 로 변경")
    public void marketStatusStepTest_1() {
        // given
        int minSalesQuantity = 70;
        int salesQuantity = 75;
        MarketStatus marketStatus = MarketStatus.WAIT;
        LocalDateTime requestDate = LocalDateTime.of(2024, 9, 13, 0, 0, 0);
        MarketStatus expectedResult = MarketStatus.AVAILABLE;

        Long marketId = saveMarket(1L, minSalesQuantity, salesQuantity, requestDate, marketStatus).getId();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", requestDate.toString())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep(MarketStatusStepConfig.STEP_NAME, jobParameters);

        // then
        Market updateMarket = marketRepository.findById(marketId).get();
        assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
        assertEquals(expectedResult, updateMarket.getMarketStatus());
    }

    @Test
    @DisplayName("상태값이 SOLD_OUT 인 마켓이 최소 판매 개수를 달성했을 경우 AVAILABLE 로 변경")
    public void marketStatusStepTest_2() {
        // given
        int minSalesQuantity = 70;
        int salesQuantity = 100;
        MarketStatus marketStatus = MarketStatus.WAIT;
        LocalDateTime requestDate = LocalDateTime.of(2024, 9, 13, 0, 0, 1);
        MarketStatus expectedResult = MarketStatus.AVAILABLE;

        Long marketId = saveMarket(1L, minSalesQuantity, salesQuantity, requestDate, marketStatus).getId();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", requestDate.toString())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep(MarketStatusStepConfig.STEP_NAME, jobParameters);

        // then
        Market updateMarket = marketRepository.findById(marketId).get();
        assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
        assertEquals(expectedResult, updateMarket.getMarketStatus());
    }

    @Test
    @DisplayName("최소 판매 개수를 달성하지 못했을 경우 NOT_AVAILABLE 로 변경")
    public void marketStatusStepTest_3() {
        // given
        int minSalesQuantity = 70;
        int salesQuantity = 50;
        MarketStatus marketStatus = MarketStatus.WAIT;
        LocalDateTime requestDate = LocalDateTime.of(2024, 9, 13, 0, 0, 2);
        MarketStatus expectedResult = MarketStatus.NOT_AVAILABLE;

        Long marketId = saveMarket(1L, minSalesQuantity, salesQuantity, requestDate, marketStatus).getId();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", requestDate.toString())
                .toJobParameters();

        // when
        JobExecution jobExecution = jobLauncherTestUtils.launchStep(MarketStatusStepConfig.STEP_NAME, jobParameters);

        // then
        Market updateMarket = marketRepository.findById(marketId).get();
        assertEquals(jobExecution.getStatus(), BatchStatus.COMPLETED);
        assertEquals(expectedResult, updateMarket.getMarketStatus());
    }

    @Test
    @DisplayName("marketStatusReader 에서 상태값이 WAIT 이거나 SOLD_OUT 인 데이터만 읽어오는지 테스트")
    public void marketStatusReaderTest_1() throws Exception {
        // given
        saveMarket(1L, 70, 50, requestDate, MarketStatus.WAIT);
        saveMarket(2L, 100, 100, requestDate, MarketStatus.SOLD_OUT);
        saveMarket(3L, 70, 50, requestDate, MarketStatus.AVAILABLE);
        saveMarket(4L, 100, 100, requestDate, MarketStatus.NOT_AVAILABLE);

        // when
        reader.open(new ExecutionContext());

        // then
        assertEquals(reader.read().getMarketStatus(), MarketStatus.WAIT);
        assertEquals(reader.read().getMarketStatus(), MarketStatus.SOLD_OUT);
        assertNull(reader.read());
    }

    @Test
    @DisplayName("marketStatusReader 에서 판매 종료 일자가 요청 일자(jobParameter)의 00:00 ~ 24:00 사이인 데이터만 읽어오는지 테스트")
    public void marketStatusReaderTest_2() throws Exception {
        // given
        LocalDateTime requestDate = LocalDateTime.of(2024, 9, 13, 0, 0);

        LocalDateTime salesEndDate = LocalDateTime.of(2024, 9, 13, 0, 0);
        LocalDateTime timeSalesEndDate = LocalDateTime.of(2024, 9, 13, 3, 0);
        LocalDateTime beforeSalesEndDate = LocalDateTime.of(2024, 9, 12, 0, 0);
        LocalDateTime afterSalesEndDate = LocalDateTime.of(2024, 9, 15, 0, 0);

        saveMarket(1L, 70, 75, salesEndDate, requestDate, MarketStatus.WAIT);
        saveMarket(2L, 70, 75, timeSalesEndDate, requestDate, MarketStatus.WAIT);
        saveMarket(3L, 70, 75, beforeSalesEndDate, requestDate, MarketStatus.WAIT);
        saveMarket(4L, 70, 75, afterSalesEndDate, requestDate, MarketStatus.WAIT);

        // when
        reader.open(new ExecutionContext());

        // then
        assertEquals(reader.read().getSalesEndDate(), salesEndDate);
        assertEquals(reader.read().getSalesEndDate(), timeSalesEndDate);
        assertNull(reader.read());
    }

    @Configuration
    public static class BatchTestConfig {
        @Bean
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                    .addScript("/org/springframework/batch/core/schema-h2.sql")
                    .build();
        }
    }

    private StepExecution getStepExecution() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("requestDate", requestDate.toString())
                .toJobParameters();

        return MetaDataInstanceFactory.createStepExecution(jobParameters);
    }

    private Market saveMarket(Long productId, int minSalesQuantity, int salesQuantity, LocalDateTime salesEndDate, LocalDateTime requestDate, MarketStatus marketStatus) {
        Market market = Market.builder()
                .product(getProduct(productId))
                .discountPrice(1000)
                .quantity(100)
                .salesQuantity(salesQuantity)
                .minSalesQuantity(minSalesQuantity)
                .limitQuantity(5)
                .salesStartDate(salesEndDate.minusDays(1))
                .salesEndDate(salesEndDate)
                .marketStatus(marketStatus)
                .build();
        return marketRepository.save(market);
    }
    private Market saveMarket(Long productId, int minSalesQuantity, int salesQuantity, LocalDateTime requestDate, MarketStatus marketStatus) {
        return saveMarket(productId, minSalesQuantity, salesQuantity, requestDate, requestDate, marketStatus);
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId).get();
    }

    private Category initCategory() {
        Category category = new Category(CategoryType.COSMETIC);
        return categoryRepository.save(category);
    }

    private Member initMember() {
        Member seller = new Member(1L, "happycat", MemberType.SELLER);
        return memberRepository.save(seller);
    }

    private void initProduct(Long productId, Category category, Member seller) {
        Product product = Product.builder()
                .id(productId)
                .category(category)
                .seller(seller)
                .name("테스트 상품")
                .price(10000)
                .description("상품 설명")
                .imageUrl("http://imagetest.com")
                .build();
        productRepository.save(product);
    }

    private void init() {
        Category category = initCategory();
        Member seller = initMember();

        LongStream.range(1, 5).forEach(idx -> {
            initProduct(idx, category, seller);
        });
    }

    private void deleteMarketData() {
        marketRepository.deleteAll();
    }
}