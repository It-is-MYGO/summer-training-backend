package modules.product.dto;

import lombok.Data;
import java.util.List;
import java.math.BigDecimal;

@Data
public class PriceTrendChartDTO {
    private String productName;
    private List<String> labels;
    private List<ChartDataset> comparisonData;
    private List<String> fluctuationLabels;
    private List<BigDecimal> fluctuationValues;
    
    @Data
    public static class ChartDataset {
        private String label;
        private List<BigDecimal> data;
        private String borderColor;
        private Integer borderWidth;
        private Double tension;
    }
} 