package modules.product.repository;

import modules.product.entity.UserFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {
    
    // 根据用户ID获取收藏列表
    List<UserFavorite> findByUserIdOrderByCreateTimeDesc(Long userId);
    
    // 检查用户是否已收藏某商品
    Optional<UserFavorite> findByUserIdAndProductId(Long userId, Long productId);
    
    // 根据用户ID和商品ID删除收藏
    void deleteByUserIdAndProductId(Long userId, Long productId);
    
    // 获取用户的收藏数量
    @Query("SELECT COUNT(uf) FROM UserFavorite uf WHERE uf.userId = :userId")
    Long countByUserId(@Param("userId") Long userId);
} 