#!/bin/bash

echo "=== 商品比价模块代码质量检查 ==="
echo ""

# 检查Java文件数量
echo "1. 检查Java文件数量:"
find src/main/java/modules/product -name "*.java" | wc -l
echo ""

# 检查代码行数
echo "2. 检查代码行数:"
echo "Controller层:"
find src/main/java/modules/product/controller -name "*.java" -exec wc -l {} + | tail -1
echo "Service层:"
find src/main/java/modules/product/service -name "*.java" -exec wc -l {} + | tail -1
echo "DTO层:"
find src/main/java/modules/product/dto -name "*.java" -exec wc -l {} + | tail -1
echo "Entity层:"
find src/main/java/modules/product/entity -name "*.java" -exec wc -l {} + | tail -1
echo ""

# 检查是否有TODO注释
echo "3. 检查TODO注释:"
grep -r "TODO" src/main/java/modules/product/ || echo "没有发现TODO注释"
echo ""

# 检查是否有FIXME注释
echo "4. 检查FIXME注释:"
grep -r "FIXME" src/main/java/modules/product/ || echo "没有发现FIXME注释"
echo ""

# 检查是否有硬编码的字符串
echo "5. 检查硬编码字符串:"
grep -r "\"[A-Za-z0-9 ]*\"" src/main/java/modules/product/ | head -10
echo ""

# 检查异常处理
echo "6. 检查异常处理:"
grep -r "catch\|throws" src/main/java/modules/product/ | wc -l
echo ""

# 检查日志记录
echo "7. 检查日志记录:"
grep -r "log\|logger" src/main/java/modules/product/ | wc -l
echo ""

# 检查API端点
echo "8. 检查API端点:"
grep -r "@GetMapping\|@PostMapping\|@PutMapping\|@DeleteMapping" src/main/java/modules/product/controller/
echo ""

# 检查数据库查询方法
echo "9. 检查数据库查询方法:"
grep -r "findBy\|findAll\|save\|delete" src/main/java/modules/product/ | wc -l
echo ""

# 检查DTO转换
echo "10. 检查DTO转换:"
grep -r "convertToDTO\|BeanUtils.copyProperties" src/main/java/modules/product/
echo ""

echo "=== 检查完成 ===" 