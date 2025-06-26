package modules.product.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductQuery {
    private String keyword;
    private String category;
    private String brand;
    private String platform;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean isHot;
    private Boolean isRecommend;
    private Integer page = 1;
    private Integer size = 20;
    private String sortBy = "createTime";
    private String sortOrder = "desc";
} 