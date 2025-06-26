package modules.product.service.impl;

import modules.product.dto.*;
import modules.product.entity.Product;
import modules.product.repository.ProductRepository;
import modules.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public List<ProductDTO> getHotProducts() {
        List<Product> products = productRepository.findByIsHotTrueOrderBySalesCountDesc();
        return products.stream()
                .limit(8) // 限制返回8个热门商品
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductDTO> getDiscountProducts() {
        List<Product> products = productRepository.findDiscountProducts();
        return products.stream()
                .limit(8) // 限制返回8个降价商品
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ProductDTO> getRecommendProducts() {
        List<Product> products = productRepository.findByIsRecommendTrueOrderByRatingDesc();
        return products.stream()
                .limit(8) // 限制返回8个推荐商品
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ProductDTO getProductDetail(Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            return convertToDTO(productOpt.get());
        }
        throw new RuntimeException("商品不存在");
    }
    
    @Override
    public Page<ProductDTO> searchProducts(ProductQuery query) {
        // 创建分页和排序
        Sort sort = Sort.by(Sort.Direction.fromString(query.getSortOrder()), query.getSortBy());
        Pageable pageable = PageRequest.of(query.getPage() - 1, query.getSize(), sort);
        
        Page<Product> products;
        
        if (query.getKeyword() != null && !query.getKeyword().trim().isEmpty()) {
            // 关键词搜索
            products = productRepository.findByKeyword(query.getKeyword(), pageable);
        } else {
            // 条件查询
            products = productRepository.findByConditions(
                query.getCategory(),
                query.getBrand(),
                query.getPlatform(),
                query.getMinPrice(),
                query.getMaxPrice(),
                query.getIsHot(),
                query.getIsRecommend(),
                pageable
            );
        }
        
        return products.map(this::convertToDTO);
    }
    
    @Override
    public List<PriceHistoryDTO> getPriceHistory(Long productId) {
        // 模拟价格历史数据
        List<PriceHistoryDTO> priceHistory = new ArrayList<>();
        LocalDate startDate = LocalDate.now().minusDays(30);
        
        for (int i = 0; i < 30; i++) {
            PriceHistoryDTO dto = new PriceHistoryDTO();
            dto.setDate(startDate.plusDays(i));
            // 模拟价格波动
            double basePrice = 3299.0;
            double variation = Math.sin(i * 0.2) * 100;
            dto.setPrice(BigDecimal.valueOf(basePrice + variation));
            dto.setPlatform("京东");
            priceHistory.add(dto);
        }
        
        return priceHistory;
    }
    
    @Override
    public PriceTrendChartDTO getPriceTrendChartData(Long productId) {
        PriceTrendChartDTO chartData = new PriceTrendChartDTO();
        chartData.setProductName("某品牌旗舰手机");
        
        // 设置标签
        List<String> labels = Arrays.asList("1日", "5日", "10日", "15日", "20日", "25日", "30日");
        chartData.setLabels(labels);
        
        // 设置多平台价格对比数据
        List<PriceTrendChartDTO.ChartDataset> datasets = new ArrayList<>();
        
        // 京东数据
        PriceTrendChartDTO.ChartDataset jdDataset = new PriceTrendChartDTO.ChartDataset();
        jdDataset.setLabel("京东");
        jdDataset.setData(Arrays.asList(
            BigDecimal.valueOf(3499), BigDecimal.valueOf(3399), BigDecimal.valueOf(3349),
            BigDecimal.valueOf(3299), BigDecimal.valueOf(3399), BigDecimal.valueOf(3299), BigDecimal.valueOf(3299)
        ));
        jdDataset.setBorderColor("#e74c3c");
        jdDataset.setBorderWidth(3);
        jdDataset.setTension(0.3);
        datasets.add(jdDataset);
        
        // 天猫数据
        PriceTrendChartDTO.ChartDataset tmDataset = new PriceTrendChartDTO.ChartDataset();
        tmDataset.setLabel("天猫");
        tmDataset.setData(Arrays.asList(
            BigDecimal.valueOf(3599), BigDecimal.valueOf(3499), BigDecimal.valueOf(3399),
            BigDecimal.valueOf(3399), BigDecimal.valueOf(3499), BigDecimal.valueOf(3399), BigDecimal.valueOf(3399)
        ));
        tmDataset.setBorderColor("#f39c12");
        tmDataset.setBorderWidth(3);
        tmDataset.setTension(0.3);
        datasets.add(tmDataset);
        
        // 拼多多数据
        PriceTrendChartDTO.ChartDataset pddDataset = new PriceTrendChartDTO.ChartDataset();
        pddDataset.setLabel("拼多多");
        pddDataset.setData(Arrays.asList(
            BigDecimal.valueOf(3299), BigDecimal.valueOf(3199), BigDecimal.valueOf(3099),
            BigDecimal.valueOf(3199), BigDecimal.valueOf(3099), BigDecimal.valueOf(3199), BigDecimal.valueOf(3199)
        ));
        pddDataset.setBorderColor("#2ecc71");
        pddDataset.setBorderWidth(3);
        pddDataset.setTension(0.3);
        datasets.add(pddDataset);
        
        chartData.setComparisonData(datasets);
        
        // 设置历史价格波动数据
        List<String> fluctuationLabels = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
        chartData.setFluctuationLabels(fluctuationLabels);
        
        List<BigDecimal> fluctuationValues = Arrays.asList(
            BigDecimal.valueOf(3899), BigDecimal.valueOf(3799), BigDecimal.valueOf(3699),
            BigDecimal.valueOf(3599), BigDecimal.valueOf(3499), BigDecimal.valueOf(3399),
            BigDecimal.valueOf(3499), BigDecimal.valueOf(3399), BigDecimal.valueOf(3299),
            BigDecimal.valueOf(3199), BigDecimal.valueOf(3299), BigDecimal.valueOf(3299)
        );
        chartData.setFluctuationValues(fluctuationValues);
        
        return chartData;
    }
    
    @Override
    public Page<ProductDTO> getProductsByCategory(String category, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> products = productRepository.findByCategory(category, pageable);
        return products.map(this::convertToDTO);
    }
    
    @Override
    public Page<ProductDTO> getProductsByBrand(String brand, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Product> products = productRepository.findByBrand(brand, pageable);
        return products.map(this::convertToDTO);
    }
    
    // 将实体转换为DTO
    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        BeanUtils.copyProperties(product, dto);
        
        // 设置前端需要的字段
        dto.setTitle(product.getName());
        dto.setDesc(product.getDescription());
        dto.setPrice("¥" + product.getPrice());
        dto.setImg(product.getImageUrl());
        
        // 模拟平台信息
        List<String> platforms = Arrays.asList(product.getPlatform());
        dto.setPlatforms(platforms);
        
        // 模拟价格变化
        dto.setPriceChange(BigDecimal.valueOf(Math.random() * 20 - 10)); // -10 到 10 之间的随机数
        
        return dto;
    }
}
