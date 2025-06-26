package modules.product.service;

import modules.product.dto.ProductDTO;
import modules.product.dto.ProductQuery;
import modules.product.dto.PriceHistoryDTO;
import modules.product.dto.PriceTrendChartDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    
    // 获取热门商品
    List<ProductDTO> getHotProducts();
    
    // 获取降价商品
    List<ProductDTO> getDiscountProducts();
    
    // 获取推荐商品
    List<ProductDTO> getRecommendProducts();
    
    // 获取商品详情
    ProductDTO getProductDetail(Long productId);
    
    // 搜索商品
    Page<ProductDTO> searchProducts(ProductQuery query);
    
    // 获取价格历史
    List<PriceHistoryDTO> getPriceHistory(Long productId);
    
    // 获取价格趋势图表数据
    PriceTrendChartDTO getPriceTrendChartData(Long productId);
    
    // 根据分类获取商品
    Page<ProductDTO> getProductsByCategory(String category, int page, int size);
    
    // 根据品牌获取商品
    Page<ProductDTO> getProductsByBrand(String brand, int page, int size);
}
