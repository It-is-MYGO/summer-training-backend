package com.example.demo.modules.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import modules.product.dto.ProductQuery;
import modules.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(modules.product.controller.ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private modules.product.service.UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void testGetHotProducts() throws Exception {
        // 准备测试数据
        modules.product.dto.ProductDTO product = new modules.product.dto.ProductDTO();
        product.setId(1L);
        product.setTitle("测试商品");
        product.setPrice("¥3299.0");
        
        List<modules.product.dto.ProductDTO> products = Arrays.asList(product);
        when(productService.getHotProducts()).thenReturn(products);

        // 执行测试
        mockMvc.perform(get("/products/hot"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].title").value("测试商品"))
                .andExpect(jsonPath("$.data[0].price").value("¥3299.0"));
    }

    @Test
    @WithMockUser
    void testGetDiscountProducts() throws Exception {
        // 准备测试数据
        modules.product.dto.ProductDTO product = new modules.product.dto.ProductDTO();
        product.setId(1L);
        product.setTitle("降价商品");
        product.setPrice("¥2999.0");
        
        List<modules.product.dto.ProductDTO> products = Arrays.asList(product);
        when(productService.getDiscountProducts()).thenReturn(products);

        // 执行测试
        mockMvc.perform(get("/products/discount"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].title").value("降价商品"));
    }

    @Test
    @WithMockUser
    void testGetRecommendProducts() throws Exception {
        // 准备测试数据
        modules.product.dto.ProductDTO product = new modules.product.dto.ProductDTO();
        product.setId(1L);
        product.setTitle("推荐商品");
        product.setPrice("¥3599.0");
        
        List<modules.product.dto.ProductDTO> products = Arrays.asList(product);
        when(productService.getRecommendProducts()).thenReturn(products);

        // 执行测试
        mockMvc.perform(get("/products/recommend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].title").value("推荐商品"));
    }

    @Test
    @WithMockUser
    void testGetProductDetail() throws Exception {
        // 准备测试数据
        modules.product.dto.ProductDTO product = new modules.product.dto.ProductDTO();
        product.setId(1L);
        product.setTitle("商品详情");
        product.setPrice("¥3299.0");
        product.setDesc("商品描述");
        
        when(productService.getProductDetail(1L)).thenReturn(product);

        // 执行测试
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.title").value("商品详情"))
                .andExpect(jsonPath("$.data.price").value("¥3299.0"))
                .andExpect(jsonPath("$.data.desc").value("商品描述"));
    }

    @Test
    @WithMockUser
    void testGetProductDetail_NotFound() throws Exception {
        // 准备测试数据
        when(productService.getProductDetail(999L)).thenThrow(new RuntimeException("商品不存在"));

        // 执行测试
        mockMvc.perform(get("/products/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(500))
                .andExpect(jsonPath("$.message").value("获取商品详情失败: 商品不存在"));
    }

    @Test
    @WithMockUser
    void testSearchProducts() throws Exception {
        // 准备测试数据
        ProductQuery query = new ProductQuery();
        query.setKeyword("手机");
        query.setPage(1);
        query.setSize(10);
        
        modules.product.dto.ProductDTO product = new modules.product.dto.ProductDTO();
        product.setId(1L);
        product.setTitle("手机商品");
        product.setPrice("¥3299.0");
        
        Page<modules.product.dto.ProductDTO> productPage = new PageImpl<>(Arrays.asList(product));
        when(productService.searchProducts(any(ProductQuery.class))).thenReturn(productPage);

        // 执行测试
        mockMvc.perform(post("/products/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(query)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].title").value("手机商品"));
    }

    @Test
    @WithMockUser
    void testGetPriceHistory() throws Exception {
        // 准备测试数据
        modules.product.dto.PriceHistoryDTO priceHistory = new modules.product.dto.PriceHistoryDTO();
        priceHistory.setDate(java.time.LocalDate.now());
        priceHistory.setPrice(BigDecimal.valueOf(3299.0));
        priceHistory.setPlatform("京东");
        
        List<modules.product.dto.PriceHistoryDTO> priceHistoryList = Arrays.asList(priceHistory);
        when(productService.getPriceHistory(1L)).thenReturn(priceHistoryList);

        // 执行测试
        mockMvc.perform(get("/products/1/price-history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].platform").value("京东"));
    }

    @Test
    @WithMockUser
    void testGetPriceTrendChartData() throws Exception {
        // 准备测试数据
        modules.product.dto.PriceTrendChartDTO chartData = new modules.product.dto.PriceTrendChartDTO();
        chartData.setProductName("测试商品");
        chartData.setLabels(Arrays.asList("1日", "5日", "10日"));
        
        when(productService.getPriceTrendChartData(1L)).thenReturn(chartData);

        // 执行测试
        mockMvc.perform(get("/products/1/price-trend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.productName").value("测试商品"))
                .andExpect(jsonPath("$.data.labels").isArray());
    }

    @Test
    @WithMockUser
    void testGetProductsByCategory() throws Exception {
        // 准备测试数据
        modules.product.dto.ProductDTO product = new modules.product.dto.ProductDTO();
        product.setId(1L);
        product.setTitle("分类商品");
        product.setPrice("¥3299.0");
        
        Page<modules.product.dto.ProductDTO> productPage = new PageImpl<>(Arrays.asList(product));
        when(productService.getProductsByCategory("手机", 1, 20)).thenReturn(productPage);

        // 执行测试
        mockMvc.perform(get("/products/category/手机")
                .param("page", "1")
                .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].title").value("分类商品"));
    }

    @Test
    @WithMockUser
    void testGetProductsByBrand() throws Exception {
        // 准备测试数据
        modules.product.dto.ProductDTO product = new modules.product.dto.ProductDTO();
        product.setId(1L);
        product.setTitle("品牌商品");
        product.setPrice("¥3299.0");
        
        Page<modules.product.dto.ProductDTO> productPage = new PageImpl<>(Arrays.asList(product));
        when(productService.getProductsByBrand("测试品牌", 1, 20)).thenReturn(productPage);

        // 执行测试
        mockMvc.perform(get("/products/brand/测试品牌")
                .param("page", "1")
                .param("size", "20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].title").value("品牌商品"));
    }
} 