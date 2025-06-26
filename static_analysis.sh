#!/bin/bash

echo "=== 商品比价模块静态代码分析 ==="
echo ""

# 检查编译错误
echo "1. 检查编译错误:"
if mvn compile -q; then
    echo "✓ 编译成功"
else
    echo "✗ 编译失败，请检查代码"
    mvn compile
fi
echo ""

# 检查代码风格
echo "2. 检查代码风格:"
if command -v checkstyle &> /dev/null; then
    checkstyle -c google_checks.xml src/main/java/modules/product/
else
    echo "Checkstyle未安装，跳过代码风格检查"
fi
echo ""

# 检查依赖关系
echo "3. 检查依赖关系:"
mvn dependency:tree | grep -E "(product|spring|jpa)" | head -20
echo ""

# 检查重复代码
echo "4. 检查重复代码:"
find src/main/java/modules/product -name "*.java" -exec grep -l "convertToDTO\|BeanUtils.copyProperties" {} \;
echo ""

# 检查方法复杂度
echo "5. 检查方法复杂度:"
echo "ProductServiceImpl中的方法行数:"
grep -n "public.*{" src/main/java/modules/product/service/impl/ProductServiceImpl.java | head -10
echo ""

# 检查异常处理模式
echo "6. 检查异常处理模式:"
grep -A 5 -B 5 "catch\|throws" src/main/java/modules/product/service/impl/ProductServiceImpl.java
echo ""

# 检查API文档
echo "7. 检查API文档:"
grep -r "@Api\|@ApiOperation\|@ApiParam" src/main/java/modules/product/controller/ || echo "没有发现Swagger注解"
echo ""

# 检查安全注解
echo "8. 检查安全注解:"
grep -r "@PreAuthorize\|@Secured\|@RolesAllowed" src/main/java/modules/product/ || echo "没有发现安全注解"
echo ""

# 检查验证注解
echo "9. 检查验证注解:"
grep -r "@Valid\|@NotNull\|@Size\|@Min\|@Max" src/main/java/modules/product/ || echo "没有发现验证注解"
echo ""

# 检查缓存注解
echo "10. 检查缓存注解:"
grep -r "@Cacheable\|@CacheEvict\|@CachePut" src/main/java/modules/product/ || echo "没有发现缓存注解"
echo ""

echo "=== 静态分析完成 ===" 