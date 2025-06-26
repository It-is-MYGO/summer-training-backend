package modules.product.repository;

import modules.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // 根据关键词搜索商品
    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% OR p.description LIKE %:keyword% OR p.brand LIKE %:keyword%")
    Page<Product> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    // 获取热门商品
    List<Product> findByIsHotTrueOrderBySalesCountDesc();
    
    // 获取推荐商品
    List<Product> findByIsRecommendTrueOrderByRatingDesc();
    
    // 获取降价商品（价格变化为负）
    @Query("SELECT p FROM Product p WHERE p.price < p.price * (1 + p.priceChange/100) ORDER BY p.priceChange ASC")
    List<Product> findDiscountProducts();
    
    // 根据分类获取商品
    Page<Product> findByCategory(String category, Pageable pageable);
    
    // 根据品牌获取商品
    Page<Product> findByBrand(String brand, Pageable pageable);
    
    // 根据平台获取商品
    Page<Product> findByPlatform(String platform, Pageable pageable);
    
    // 价格范围查询
    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                   @Param("maxPrice") BigDecimal maxPrice, 
                                   Pageable pageable);
    
    // 复合查询
    @Query("SELECT p FROM Product p WHERE " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:brand IS NULL OR p.brand = :brand) AND " +
           "(:platform IS NULL OR p.platform = :platform) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:isHot IS NULL OR p.isHot = :isHot) AND " +
           "(:isRecommend IS NULL OR p.isRecommend = :isRecommend)")
    Page<Product> findByConditions(@Param("category") String category,
                                   @Param("brand") String brand,
                                   @Param("platform") String platform,
                                   @Param("minPrice") BigDecimal minPrice,
                                   @Param("maxPrice") BigDecimal maxPrice,
                                   @Param("isHot") Boolean isHot,
                                   @Param("isRecommend") Boolean isRecommend,
                                   Pageable pageable);
} 