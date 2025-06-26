package modules.product.service;

import modules.product.dto.UserFavoriteDTO;
import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    
    // 获取用户收藏列表
    List<UserFavoriteDTO> getFavoritesList(Long userId);
    
    // 添加商品到收藏
    void addToFavorites(Long userId, Long productId);
    
    // 从收藏夹删除商品
    void removeFromFavorites(Long userId, Long productId);
    
    // 设置价格提醒
    void setPriceAlert(Long userId, Long productId, BigDecimal alertPrice);
    
    // 更新提醒价格
    void updateAlertPrice(Long favoriteId, BigDecimal alertPrice);
    
    // 检查用户是否已收藏某商品
    boolean isProductFavorited(Long userId, Long productId);
} 