-- 初始化角色数据
INSERT INTO roles (name) VALUES ('ROLE_USER') ON DUPLICATE KEY UPDATE name = name;
INSERT INTO roles (name) VALUES ('ROLE_ADMIN') ON DUPLICATE KEY UPDATE name = name;

-- 插入商品测试数据
INSERT INTO products (name, description, price, brand, category, image_url, source_url, platform, rating, review_count, sales_count, is_hot, is_recommend, create_time, update_time) VALUES
('iPhone 15 Pro', '苹果最新旗舰手机，搭载A17 Pro芯片', 7999.00, 'Apple', '手机', 'https://example.com/iphone15.jpg', 'https://www.apple.com', '京东', 4.8, 1250, 5000, true, true, NOW(), NOW()),
('华为 Mate 60 Pro', '华为旗舰手机，搭载麒麟芯片', 6999.00, 'Huawei', '手机', 'https://example.com/mate60.jpg', 'https://www.huawei.com', '天猫', 4.9, 980, 3500, true, true, NOW(), NOW()),
('小米 14 Ultra', '小米影像旗舰，徕卡光学', 6499.00, 'Xiaomi', '手机', 'https://example.com/mi14.jpg', 'https://www.mi.com', '拼多多', 4.7, 850, 2800, true, false, NOW(), NOW()),
('MacBook Pro 14', '苹果专业级笔记本电脑', 14999.00, 'Apple', '电脑', 'https://example.com/macbook.jpg', 'https://www.apple.com', '京东', 4.9, 650, 1200, false, true, NOW(), NOW()),
('联想 ThinkPad X1', '商务轻薄本，性能强劲', 12999.00, 'Lenovo', '电脑', 'https://example.com/thinkpad.jpg', 'https://www.lenovo.com', '苏宁', 4.6, 420, 800, false, false, NOW(), NOW()),
('戴尔 XPS 13', '超薄笔记本，屏幕出色', 9999.00, 'Dell', '电脑', 'https://example.com/xps13.jpg', 'https://www.dell.com', '天猫', 4.5, 380, 600, false, true, NOW(), NOW()),
('AirPods Pro 2', '主动降噪无线耳机', 1899.00, 'Apple', '耳机', 'https://example.com/airpods.jpg', 'https://www.apple.com', '京东', 4.8, 2100, 8000, true, true, NOW(), NOW()),
('索尼 WH-1000XM5', '顶级降噪耳机', 2899.00, 'Sony', '耳机', 'https://example.com/sony.jpg', 'https://www.sony.com', '拼多多', 4.7, 680, 1500, false, true, NOW(), NOW());