package com.example.demo.modules.product;

import modules.product.dto.*;
import modules.product.service.ProductService;
import modules.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private modules.product.repository.ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private modules.product.entity.Product mockProduct;

    @BeforeEach
    void setUp() {
        // 创建模拟商品数据
        mockProduct = new modules.product.entity.Product();
        mockProduct.setId(1L);
        mockProduct.setName("测试商品");
        mockProduct.setDescription("这是一个测试商品");
        mockProduct.setPrice(BigDecimal.valueOf(3299.00));
        mockProduct.setImageUrl("http://example.com/image.jpg");
        mockProduct.setPlatform("京东");
        mockProduct.setCategory("手机");
        mockProduct.setBrand("测试品牌");
        mockProduct.setIsHot(true);
        mockProduct.setIsRecommend(true);
        mockProduct.setSalesCount(1000);
        mockProduct.setRating(BigDecimal.valueOf(4.5));  // 修改这里
    }

    @Test
    void testGetHotProducts() {
        // 准备测试数据
        List<modules.product.entity.Product> hotProducts = Arrays.asList(mockProduct);
        when(productRepository.findByIsHotTrueOrderBySalesCountDesc()).thenReturn(hotProducts);

        // 执行测试
        List<ProductDTO> result = productService.getHotProducts();

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("测试商品", result.get(0).getTitle());
        assertEquals("¥3299.0", result.get(0).getPrice());
        
        // 验证方法调用
        verify(productRepository).findByIsHotTrueOrderBySalesCountDesc();
    }

    @Test
    void testGetDiscountProducts() {
        // 准备测试数据
        List<modules.product.entity.Product> discountProducts = Arrays.asList(mockProduct);
        when(productRepository.findDiscountProducts()).thenReturn(discountProducts);

        // 执行测试
        List<ProductDTO> result = productService.getDiscountProducts();

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        
        // 验证方法调用
        verify(productRepository).findDiscountProducts();
    }

    @Test
    void testGetRecommendProducts() {
        // 准备测试数据
        List<modules.product.entity.Product> recommendProducts = Arrays.asList(mockProduct);
        when(productRepository.findByIsRecommendTrueOrderByRatingDesc()).thenReturn(recommendProducts);

        // 执行测试
        List<ProductDTO> result = productService.getRecommendProducts();

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        
        // 验证方法调用
        verify(productRepository).findByIsRecommendTrueOrderByRatingDesc();
    }

    @Test
    void testGetProductDetail_Success() {
        // 准备测试数据
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        // 执行测试
        ProductDTO result = productService.getProductDetail(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals("测试商品", result.getTitle());
        assertEquals("¥3299.0", result.getPrice());
        
        // 验证方法调用
        verify(productRepository).findById(1L);
    }

    @Test
    void testGetProductDetail_NotFound() {
        // 准备测试数据
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        // 执行测试并验证异常
        assertThrows(RuntimeException.class, () -> {
            productService.getProductDetail(999L);
        });
        
        // 验证方法调用
        verify(productRepository).findById(999L);
    }

    @Test
    void testGetPriceHistory() {
        // 执行测试
        List<PriceHistoryDTO> result = productService.getPriceHistory(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals(30, result.size()); // 应该返回30天的价格历史
        
        // 验证第一天的数据
        PriceHistoryDTO firstDay = result.get(0);
        assertNotNull(firstDay.getDate());
        assertNotNull(firstDay.getPrice());
        assertEquals("京东", firstDay.getPlatform());
        // BigDecimal 两位小数断言
        assertEquals(0, firstDay.getPrice().setScale(2, java.math.RoundingMode.HALF_UP).compareTo(java.math.BigDecimal.valueOf(3299.00).setScale(2, java.math.RoundingMode.HALF_UP)));
    }

    @Test
    void testGetPriceTrendChartData() {
        // 执行测试
        PriceTrendChartDTO result = productService.getPriceTrendChartData(1L);

        // 验证结果
        assertNotNull(result);
        assertEquals("某品牌旗舰手机", result.getProductName());
        assertNotNull(result.getLabels());
        assertEquals(7, result.getLabels().size());
        
        // 验证对比数据
        assertNotNull(result.getComparisonData());
        assertEquals(3, result.getComparisonData().size()); // 京东、天猫、拼多多
        
        // 验证波动数据
        assertNotNull(result.getFluctuationLabels());
        assertEquals(12, result.getFluctuationLabels().size());
        assertNotNull(result.getFluctuationValues());
        assertEquals(12, result.getFluctuationValues().size());
    }

    @Test
    void testSearchProducts_WithKeyword() {
        // 准备测试数据
        ProductQuery query = new ProductQuery();
        query.setKeyword("手机");
        query.setPage(1);
        query.setSize(10);
        query.setSortBy("price");
        query.setSortOrder("ASC");

        Page<modules.product.entity.Product> productPage = new PageImpl<>(Arrays.asList(mockProduct));
        when(productRepository.findByKeyword(eq("手机"), any(Pageable.class))).thenReturn(productPage);

        // 执行测试
        Page<ProductDTO> result = productService.searchProducts(query);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        
        // 验证方法调用
        verify(productRepository).findByKeyword(eq("手机"), any(Pageable.class));
    }

    @Test
    void testSearchProducts_WithConditions() {
        // 准备测试数据
        ProductQuery query = new ProductQuery();
        query.setCategory("手机");
        query.setBrand("测试品牌");
        query.setMinPrice(BigDecimal.valueOf(3000));
        query.setMaxPrice(BigDecimal.valueOf(4000));
        query.setPage(1);
        query.setSize(10);
        query.setSortBy("price");
        query.setSortOrder("ASC");

        Page<modules.product.entity.Product> productPage = new PageImpl<>(Arrays.asList(mockProduct));
        when(productRepository.findByConditions(eq("手机"), eq("测试品牌"), any(), any(), any(), any(), any(), any(Pageable.class))).thenReturn(productPage);

        // 执行测试
        Page<ProductDTO> result = productService.searchProducts(query);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        
        // 验证方法调用
        verify(productRepository).findByConditions(eq("手机"), eq("测试品牌"), any(), any(), any(), any(), any(), any(Pageable.class));
    }

    @Test
    void testGetProductsByCategory() {
        // 准备测试数据
        Page<modules.product.entity.Product> productPage = new PageImpl<>(Arrays.asList(mockProduct));
        when(productRepository.findByCategory(eq("手机"), any(Pageable.class))).thenReturn(productPage);

        // 执行测试
        Page<ProductDTO> result = productService.getProductsByCategory("手机", 1, 10);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        
        // 验证方法调用
        verify(productRepository).findByCategory(eq("手机"), any(Pageable.class));
    }

    @Test
    void testGetProductsByBrand() {
        // 准备测试数据
        Page<modules.product.entity.Product> productPage = new PageImpl<>(Arrays.asList(mockProduct));
        when(productRepository.findByBrand(eq("测试品牌"), any(Pageable.class))).thenReturn(productPage);

        // 执行测试
        Page<ProductDTO> result = productService.getProductsByBrand("测试品牌", 1, 10);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        
        // 验证方法调用
        verify(productRepository).findByBrand(eq("测试品牌"), any(Pageable.class));
    }
} 