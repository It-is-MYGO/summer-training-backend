package modules.product.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserFavoriteDTO {
    private Long id;
    private Long productId;
    private String title;
    private String price;
    private String img;
    private BigDecimal priceChange;
    private BigDecimal alertPrice;
    private LocalDateTime createTime;
} 