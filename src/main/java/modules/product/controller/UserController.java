package modules.product.controller;

import common.util.Result;
import modules.product.dto.UserFavoriteDTO;
import modules.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    // 获取用户收藏列表
    @GetMapping("/favorites")
    public Result<List<UserFavoriteDTO>> getFavoritesList(@RequestParam(defaultValue = "1") Long userId) {
        try {
            List<UserFavoriteDTO> favorites = userService.getFavoritesList(userId);
            return Result.success(favorites);
        } catch (Exception e) {
            return Result.error(500, "获取收藏列表失败: " + e.getMessage());
        }
    }
    
    // 添加商品到收藏
    @PostMapping("/favorites")
    public Result<Void> addToFavorites(@RequestParam Long userId, @RequestParam Long productId) {
        try {
            userService.addToFavorites(userId, productId);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error(500, "添加收藏失败: " + e.getMessage());
        }
    }
    
    // 从收藏夹删除商品
    @DeleteMapping("/favorites")
    public Result<Void> removeFromFavorites(@RequestParam Long userId, @RequestParam Long productId) {
        try {
            userService.removeFromFavorites(userId, productId);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error(500, "删除收藏失败: " + e.getMessage());
        }
    }
    
    // 设置价格提醒
    @PostMapping("/price-alert")
    public Result<Void> setPriceAlert(@RequestParam Long userId, 
                                     @RequestParam Long productId, 
                                     @RequestParam BigDecimal alertPrice) {
        try {
            userService.setPriceAlert(userId, productId, alertPrice);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error(500, "设置价格提醒失败: " + e.getMessage());
        }
    }
    
    // 更新提醒价格
    @PutMapping("/favorites/{favoriteId}/alert-price")
    public Result<Void> updateAlertPrice(@PathVariable Long favoriteId, 
                                        @RequestParam BigDecimal alertPrice) {
        try {
            userService.updateAlertPrice(favoriteId, alertPrice);
            return Result.success(null);
        } catch (Exception e) {
            return Result.error(500, "更新提醒价格失败: " + e.getMessage());
        }
    }
    
    // 检查用户是否已收藏某商品
    @GetMapping("/favorites/check")
    public Result<Boolean> isProductFavorited(@RequestParam Long userId, @RequestParam Long productId) {
        try {
            boolean isFavorited = userService.isProductFavorited(userId, productId);
            return Result.success(isFavorited);
        } catch (Exception e) {
            return Result.error(500, "检查收藏状态失败: " + e.getMessage());
        }
    }
} 