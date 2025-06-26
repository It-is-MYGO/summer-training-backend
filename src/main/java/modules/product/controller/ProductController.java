package modules.product.controller;

import common.util.Result;
import modules.product.dto.*;
import modules.product.service.ProductService;
import modules.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    // 获取热门商品
    @GetMapping("/hot")
    public Result<List<ProductDTO>> getHotProducts() {
        try {
            List<ProductDTO> products = productService.getHotProducts();
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(500, "获取热门商品失败: " + e.getMessage());
        }
    }
    
    // 获取降价商品
    @GetMapping("/discount")
    public Result<List<ProductDTO>> getDiscountProducts() {
        try {
            List<ProductDTO> products = productService.getDiscountProducts();
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(500, "获取降价商品失败: " + e.getMessage());
        }
    }
    
    // 获取推荐商品
    @GetMapping("/recommend")
    public Result<List<ProductDTO>> getRecommendProducts() {
        try {
            List<ProductDTO> products = productService.getRecommendProducts();
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(500, "获取推荐商品失败: " + e.getMessage());
        }
    }
    
    // 获取商品详情
    @GetMapping("/{id}")
    public Result<ProductDTO> getProductDetail(@PathVariable("id") Long productId) {
        try {
            ProductDTO product = productService.getProductDetail(productId);
            return Result.success(product);
        } catch (Exception e) {
            return Result.error(500, "获取商品详情失败: " + e.getMessage());
        }
    }
    
    // 搜索商品
    @PostMapping("/search")
    public Result<Page<ProductDTO>> searchProducts(@RequestBody ProductQuery query) {
        try {
            Page<ProductDTO> products = productService.searchProducts(query);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(500, "搜索商品失败: " + e.getMessage());
        }
    }
    
    // 获取价格历史
    @GetMapping("/{id}/price-history")
    public Result<List<PriceHistoryDTO>> getPriceHistory(@PathVariable("id") Long productId) {
        try {
            List<PriceHistoryDTO> priceHistory = productService.getPriceHistory(productId);
            return Result.success(priceHistory);
        } catch (Exception e) {
            return Result.error(500, "获取价格历史失败: " + e.getMessage());
        }
    }
    
    // 获取价格趋势图表数据
    @GetMapping("/{id}/price-trend")
    public Result<PriceTrendChartDTO> getPriceTrendChartData(@PathVariable("id") Long productId) {
        try {
            PriceTrendChartDTO chartData = productService.getPriceTrendChartData(productId);
            return Result.success(chartData);
        } catch (Exception e) {
            return Result.error(500, "获取价格趋势数据失败: " + e.getMessage());
        }
    }
    
    // 根据分类获取商品
    @GetMapping("/category/{category}")
    public Result<Page<ProductDTO>> getProductsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Page<ProductDTO> products = productService.getProductsByCategory(category, page, size);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(500, "获取分类商品失败: " + e.getMessage());
        }
    }
    
    // 根据品牌获取商品
    @GetMapping("/brand/{brand}")
    public Result<Page<ProductDTO>> getProductsByBrand(
            @PathVariable String brand,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            Page<ProductDTO> products = productService.getProductsByBrand(brand, page, size);
            return Result.success(products);
        } catch (Exception e) {
            return Result.error(500, "获取品牌商品失败: " + e.getMessage());
        }
    }
}
