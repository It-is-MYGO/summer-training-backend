package modules.product.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String title;
    private String desc;
    private String price;
    private String oldPrice;
    private BigDecimal priceChange;
    private String img;
    private List<String> platforms;
    private String brand;
    private String category;
    private BigDecimal rating;
    private Integer reviewCount;
    private Integer salesCount;
    private Boolean isHot;
    private Boolean isRecommend;
    private LocalDateTime createTime;
} 