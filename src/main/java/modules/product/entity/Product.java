package modules.product.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(length = 100)
    private String brand;
    
    @Column(length = 100)
    private String category;
    
    @Column(length = 500)
    private String imageUrl;
    
    @Column(length = 200)
    private String sourceUrl;
    
    @Column(length = 100)
    private String platform; // 电商平台：淘宝、京东、拼多多等
    
    @Column(precision = 3, scale = 1)
    private BigDecimal rating; // 评分 0-5
    
    @Column
    private Integer reviewCount; // 评论数量
    
    @Column
    private Integer salesCount; // 销量
    
    @Column
    private Boolean isHot = false; // 是否热门商品
    
    @Column
    private Boolean isRecommend = false; // 是否推荐商品
    
    @Column
    private LocalDateTime createTime;
    
    @Column
    private LocalDateTime updateTime;
    
    @PrePersist
    protected void onCreate() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updateTime = LocalDateTime.now();
    }
}
