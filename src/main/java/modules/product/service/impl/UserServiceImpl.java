package modules.product.service.impl;

import modules.product.dto.UserFavoriteDTO;
import modules.product.entity.Product;
import modules.product.entity.UserFavorite;
import modules.product.repository.ProductRepository;
import modules.product.repository.UserFavoriteRepository;
import modules.product.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserFavoriteRepository userFavoriteRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Override
    public List<UserFavoriteDTO> getFavoritesList(Long userId) {
        List<UserFavorite> favorites = userFavoriteRepository.findByUserIdOrderByCreateTimeDesc(userId);
        
        return favorites.stream().map(favorite -> {
            UserFavoriteDTO dto = new UserFavoriteDTO();
            BeanUtils.copyProperties(favorite, dto);
            
            // 获取商品信息
            Optional<Product> productOpt = productRepository.findById(favorite.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                dto.setTitle(product.getName());
                dto.setPrice("¥" + product.getPrice());
                dto.setImg(product.getImageUrl());
                // 模拟价格变化
                dto.setPriceChange(BigDecimal.valueOf(Math.random() * 20 - 10));
            }
            
            return dto;
        }).collect(Collectors.toList());
    }
    
    @Override
    public void addToFavorites(Long userId, Long productId) {
        // 检查是否已收藏
        Optional<UserFavorite> existingFavorite = userFavoriteRepository.findByUserIdAndProductId(userId, productId);
        if (existingFavorite.isPresent()) {
            throw new RuntimeException("商品已在收藏夹中");
        }
        
        // 检查商品是否存在
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("商品不存在");
        }
        
        // 创建新的收藏记录
        UserFavorite favorite = new UserFavorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        userFavoriteRepository.save(favorite);
    }
    
    @Override
    public void removeFromFavorites(Long userId, Long productId) {
        userFavoriteRepository.deleteByUserIdAndProductId(userId, productId);
    }
    
    @Override
    public void setPriceAlert(Long userId, Long productId, BigDecimal alertPrice) {
        Optional<UserFavorite> favoriteOpt = userFavoriteRepository.findByUserIdAndProductId(userId, productId);
        
        if (favoriteOpt.isPresent()) {
            // 更新现有收藏的提醒价格
            UserFavorite favorite = favoriteOpt.get();
            favorite.setAlertPrice(alertPrice);
            userFavoriteRepository.save(favorite);
        } else {
            // 创建新的收藏记录并设置提醒价格
            UserFavorite favorite = new UserFavorite();
            favorite.setUserId(userId);
            favorite.setProductId(productId);
            favorite.setAlertPrice(alertPrice);
            userFavoriteRepository.save(favorite);
        }
    }
    
    @Override
    public void updateAlertPrice(Long favoriteId, BigDecimal alertPrice) {
        Optional<UserFavorite> favoriteOpt = userFavoriteRepository.findById(favoriteId);
        if (favoriteOpt.isPresent()) {
            UserFavorite favorite = favoriteOpt.get();
            favorite.setAlertPrice(alertPrice);
            userFavoriteRepository.save(favorite);
        } else {
            throw new RuntimeException("收藏记录不存在");
        }
    }
    
    @Override
    public boolean isProductFavorited(Long userId, Long productId) {
        return userFavoriteRepository.findByUserIdAndProductId(userId, productId).isPresent();
    }
} 