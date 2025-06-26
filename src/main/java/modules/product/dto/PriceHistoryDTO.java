package modules.product.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PriceHistoryDTO {
    private LocalDate date;
    private BigDecimal price;
    private String platform;
} 