-- 奶茶店订单制作与原料库存管理系统核心业务表创建SQL
-- 创建时间：2024-01-01

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ====================================================
-- 产品相关表
-- ====================================================

-- 1. 产品分类表
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `parent_id` bigint DEFAULT 0 COMMENT '父分类ID',
  `order_num` int DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint DEFAULT 1 COMMENT '状态(1:启用,0:禁用)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uk_category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品分类表';

-- 2. 产品信息表
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` bigint NOT NULL AUTO_INCREMENT COMMENT '产品ID',
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `description` varchar(500) DEFAULT NULL COMMENT '产品描述',
  `image_url` varchar(255) DEFAULT NULL COMMENT '产品图片',
  `is_hot` tinyint DEFAULT 0 COMMENT '是否热销(1:是,0:否)',
  `is_new` tinyint DEFAULT 0 COMMENT '是否新品(1:是,0:否)',
  `status` tinyint DEFAULT 1 COMMENT '状态(1:上架,0:下架)',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `uk_product_name` (`product_name`),
  KEY `idx_category_id` (`category_id`),
  CONSTRAINT `fk_product_category` FOREIGN KEY (`category_id`) REFERENCES `product_category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品信息表';

-- 3. 产品配方表
DROP TABLE IF EXISTS `product_formula`;
CREATE TABLE `product_formula` (
  `formula_id` bigint NOT NULL AUTO_INCREMENT COMMENT '配方ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `material_id` bigint NOT NULL COMMENT '原料ID',
  `quantity` decimal(10,4) NOT NULL COMMENT '用量',
  `unit` varchar(20) NOT NULL COMMENT '单位',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`formula_id`),
  KEY `idx_product_id` (`product_id`),
  KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品配方表';

-- 4. 产品属性表
DROP TABLE IF EXISTS `product_attribute`;
CREATE TABLE `product_attribute` (
  `attribute_id` bigint NOT NULL AUTO_INCREMENT COMMENT '属性ID',
  `attribute_name` varchar(50) NOT NULL COMMENT '属性名称',
  `attribute_value` varchar(255) NOT NULL COMMENT '属性值(JSON格式)',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`attribute_id`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `fk_attribute_product` FOREIGN KEY (`product_id`) REFERENCES `product_info` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='产品属性表';

-- ====================================================
-- 原料库存相关表
-- ====================================================

-- 5. 原料分类表
DROP TABLE IF EXISTS `material_category`;
CREATE TABLE `material_category` (
  `category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_name` varchar(50) NOT NULL COMMENT '分类名称',
  `parent_id` bigint DEFAULT 0 COMMENT '父分类ID',
  `order_num` int DEFAULT 0 COMMENT '显示顺序',
  `status` tinyint DEFAULT 1 COMMENT '状态(1:启用,0:禁用)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uk_category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='原料分类表';

-- 6. 供应商表
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier` (
  `supplier_id` bigint NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `supplier_name` varchar(100) NOT NULL COMMENT '供应商名称',
  `contact_person` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `status` tinyint DEFAULT 1 COMMENT '状态(1:启用,0:禁用)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='供应商表';

-- 7. 原料信息表
DROP TABLE IF EXISTS `material_info`;
CREATE TABLE `material_info` (
  `material_id` bigint NOT NULL AUTO_INCREMENT COMMENT '原料ID',
  `material_name` varchar(100) NOT NULL COMMENT '原料名称',
  `category_id` bigint NOT NULL COMMENT '分类ID',
  `specification` varchar(100) DEFAULT NULL COMMENT '规格',
  `unit` varchar(20) NOT NULL COMMENT '单位',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `purchase_price` decimal(10,4) DEFAULT NULL COMMENT '采购单价',
  `shelf_life` int DEFAULT NULL COMMENT '保质期(天)',
  `warning_days` int DEFAULT 7 COMMENT '预警天数',
  `status` tinyint DEFAULT 1 COMMENT '状态(1:启用,0:禁用)',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`material_id`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  CONSTRAINT `fk_material_category` FOREIGN KEY (`category_id`) REFERENCES `material_category` (`category_id`),
  CONSTRAINT `fk_material_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='原料信息表';

-- 8. 仓库表
DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse` (
  `warehouse_id` bigint NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
  `warehouse_name` varchar(100) NOT NULL COMMENT '仓库名称',
  `address` varchar(255) DEFAULT NULL COMMENT '仓库地址',
  `manager_id` bigint DEFAULT NULL COMMENT '负责人ID',
  `status` tinyint DEFAULT 1 COMMENT '状态(1:启用,0:禁用)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`warehouse_id`),
  UNIQUE KEY `uk_warehouse_name` (`warehouse_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='仓库表';

-- 9. 库存信息表
DROP TABLE IF EXISTS `stock_info`;
CREATE TABLE `stock_info` (
  `stock_id` bigint NOT NULL AUTO_INCREMENT COMMENT '库存ID',
  `material_id` bigint NOT NULL COMMENT '原料ID',
  `quantity` decimal(15,4) NOT NULL DEFAULT 0 COMMENT '库存数量',
  `alert_threshold` decimal(15,4) NOT NULL DEFAULT 0 COMMENT '预警阈值',
  `warehouse_id` bigint NOT NULL COMMENT '仓库ID',
  `location` varchar(100) DEFAULT NULL COMMENT '存储位置',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`stock_id`),
  UNIQUE KEY `uk_material_warehouse` (`material_id`,`warehouse_id`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  CONSTRAINT `fk_stock_material` FOREIGN KEY (`material_id`) REFERENCES `material_info` (`material_id`),
  CONSTRAINT `fk_stock_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存信息表';

-- 10. 库存变动记录表
DROP TABLE IF EXISTS `stock_record`;
CREATE TABLE `stock_record` (
  `record_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `material_id` bigint NOT NULL COMMENT '原料ID',
  `type` tinyint NOT NULL COMMENT '变动类型(1:入库,2:出库,3:盘点,4:报损)',
  `quantity` decimal(15,4) NOT NULL COMMENT '变动数量',
  `before_quantity` decimal(15,4) NOT NULL COMMENT '变动前数量',
  `after_quantity` decimal(15,4) NOT NULL COMMENT '变动后数量',
  `order_id` bigint DEFAULT NULL COMMENT '关联订单ID',
  `reference_no` varchar(50) DEFAULT NULL COMMENT '参考单号',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`),
  KEY `idx_material_id` (`material_id`),
  KEY `idx_create_time` (`create_time`),
  CONSTRAINT `fk_record_material` FOREIGN KEY (`material_id`) REFERENCES `material_info` (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='库存变动记录表';

-- ====================================================
-- 订单相关表
-- ====================================================

-- 11. 顾客表
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT COMMENT '顾客ID',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '顾客姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `member_level` tinyint DEFAULT 1 COMMENT '会员等级',
  `points` int DEFAULT 0 COMMENT '积分',
  `total_consume` decimal(10,2) DEFAULT 0 COMMENT '总消费金额',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `uk_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='顾客表';

-- 12. 订单主表
DROP TABLE IF EXISTS `order_main`;
CREATE TABLE `order_main` (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单编号',
  `customer_id` bigint DEFAULT NULL COMMENT '顾客ID',
  `customer_name` varchar(50) NOT NULL COMMENT '顾客姓名',
  `customer_phone` varchar(20) NOT NULL COMMENT '顾客电话',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `actual_amount` decimal(10,2) NOT NULL COMMENT '实际支付金额',
  `discount_amount` decimal(10,2) DEFAULT 0 COMMENT '优惠金额',
  `order_type` tinyint NOT NULL COMMENT '订单类型(1:堂食,2:外卖,3:自提)',
  `status` tinyint NOT NULL COMMENT '订单状态(1:待支付,2:待制作,3:制作中,4:待配送,5:配送中,6:已完成,7:已取消)',
  `payment_status` tinyint NOT NULL COMMENT '支付状态(0:未支付,1:已支付,2:支付失败,3:退款中,4:已退款)',
  `payment_type` tinyint DEFAULT NULL COMMENT '支付方式(1:微信,2:支付宝,3:现金)',
  `table_no` varchar(20) DEFAULT NULL COMMENT '桌号(堂食)',
  `delivery_address` varchar(500) DEFAULT NULL COMMENT '配送地址',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
  `completion_time` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_status` (`status`),
  CONSTRAINT `fk_order_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单主表';

-- 13. 订单明细表
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` bigint NOT NULL AUTO_INCREMENT COMMENT '详情ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `product_id` bigint NOT NULL COMMENT '产品ID',
  `product_name` varchar(100) NOT NULL COMMENT '产品名称',
  `quantity` int NOT NULL COMMENT '数量',
  `price` decimal(10,2) NOT NULL COMMENT '单价',
  `subtotal` decimal(10,2) NOT NULL COMMENT '小计',
  `customization` varchar(500) DEFAULT NULL COMMENT '定制要求',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_product_id` (`product_id`),
  CONSTRAINT `fk_detail_order` FOREIGN KEY (`order_id`) REFERENCES `order_main` (`order_id`),
  CONSTRAINT `fk_detail_product` FOREIGN KEY (`product_id`) REFERENCES `product_info` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单明细表';

-- 14. 订单状态历史表
DROP TABLE IF EXISTS `order_status_history`;
CREATE TABLE `order_status_history` (
  `history_id` bigint NOT NULL AUTO_INCREMENT COMMENT '历史ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `old_status` tinyint NOT NULL COMMENT '原状态',
  `new_status` tinyint NOT NULL COMMENT '新状态',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`history_id`),
  KEY `idx_order_id` (`order_id`),
  CONSTRAINT `fk_history_order` FOREIGN KEY (`order_id`) REFERENCES `order_main` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单状态历史表';

-- ====================================================
-- 采购相关表
-- ====================================================

-- 15. 采购订单表
DROP TABLE IF EXISTS `purchase_order`;
CREATE TABLE `purchase_order` (
  `purchase_id` bigint NOT NULL AUTO_INCREMENT COMMENT '采购订单ID',
  `purchase_no` varchar(50) NOT NULL COMMENT '采购单号',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID',
  `total_amount` decimal(10,2) NOT NULL COMMENT '总金额',
  `status` tinyint NOT NULL COMMENT '状态(1:待审核,2:已审核,3:采购中,4:已完成,5:已取消)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator_id` bigint DEFAULT NULL COMMENT '创建人ID',
  `auditor_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `completion_time` datetime DEFAULT NULL COMMENT '完成时间',
  PRIMARY KEY (`purchase_id`),
  UNIQUE KEY `uk_purchase_no` (`purchase_no`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_create_time` (`create_time`),
  KEY `idx_status` (`status`),
  CONSTRAINT `fk_purchase_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购订单表';

-- 16. 采购明细表
DROP TABLE IF EXISTS `purchase_detail`;
CREATE TABLE `purchase_detail` (
  `detail_id` bigint NOT NULL AUTO_INCREMENT COMMENT '详情ID',
  `purchase_id` bigint NOT NULL COMMENT '采购订单ID',
  `material_id` bigint NOT NULL COMMENT '原料ID',
  `material_name` varchar(100) NOT NULL COMMENT '原料名称',
  `specification` varchar(100) DEFAULT NULL COMMENT '规格',
  `unit` varchar(20) NOT NULL COMMENT '单位',
  `quantity` decimal(15,4) NOT NULL COMMENT '采购数量',
  `unit_price` decimal(10,4) NOT NULL COMMENT '单价',
  `amount` decimal(10,2) NOT NULL COMMENT '金额',
  `actual_quantity` decimal(15,4) DEFAULT NULL COMMENT '实际到货数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_purchase_id` (`purchase_id`),
  KEY `idx_material_id` (`material_id`),
  CONSTRAINT `fk_purchase_detail_order` FOREIGN KEY (`purchase_id`) REFERENCES `purchase_order` (`purchase_id`),
  CONSTRAINT `fk_purchase_detail_material` FOREIGN KEY (`material_id`) REFERENCES `material_info` (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='采购明细表';

-- ====================================================
-- 插入示例数据
-- ====================================================

-- 1. 产品分类数据
INSERT INTO `product_category` (`category_id`, `category_name`, `parent_id`, `order_num`, `status`, `create_time`, `update_time`)
VALUES
(1001, '经典奶茶', 0, 1, 1, NOW(), NOW()),
(1002, '果茶系列', 0, 2, 1, NOW(), NOW()),
(1003, '奶盖茶', 0, 3, 1, NOW(), NOW()),
(1004, '冷泡茶', 0, 4, 1, NOW(), NOW()),
(1005, '季节限定', 0, 5, 1, NOW(), NOW()),
(1006, '咖啡系列', 0, 6, 1, NOW(), NOW()),
(1007, '热饮系列', 0, 7, 1, NOW(), NOW()),
(1008, '小吃系列', 0, 8, 1, NOW(), NOW()),
(1009, '冰沙系列', 0, 9, 1, NOW(), NOW()),
(1010, '特调饮品', 0, 10, 1, NOW(), NOW());

-- 2. 产品信息数据
INSERT INTO `product_info` (`product_id`, `product_name`, `category_id`, `price`, `description`, `image_url`, `is_hot`, `is_new`, `status`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES
(1001, '原味珍珠奶茶', 1001, 12.00, '经典原味奶茶搭配Q弹珍珠', 'img/milkyTea1.png', 1, 0, 1, 'admin', NOW(), 'admin', NOW()),
(1002, '草莓果茶', 1002, 15.00, '新鲜草莓搭配清香茶底', 'img/milkyTea2.png', 1, 1, 1, 'admin', NOW(), 'admin', NOW()),
(1003, '芝士奶盖绿茶', 1003, 18.00, '浓郁芝士奶盖搭配清香绿茶', 'img/milkyTea1.png', 1, 0, 1, 'admin', NOW(), 'admin', NOW()),
(1004, '柠檬冷泡茶', 1004, 14.00, '清爽柠檬冷泡茶，夏日解暑', 'img/milkyTea2.png', 0, 1, 1, 'admin', NOW(), 'admin', NOW()),
(1005, '芒果冰沙', 1009, 16.00, '新鲜芒果制作的冰沙', 'img/milkyTea1.png', 1, 0, 1, 'admin', NOW(), 'admin', NOW()),
(1006, '红豆奶茶', 1001, 13.00, '香甜红豆搭配丝滑奶茶', 'img/milkyTea2.png', 0, 0, 1, 'admin', NOW(), 'admin', NOW()),
(1007, '焦糖咖啡', 1006, 17.00, '浓郁咖啡搭配焦糖风味', 'img/milkyTea1.png', 0, 1, 1, 'admin', NOW(), 'admin', NOW()),
(1008, '黑糖珍珠奶茶', 1001, 15.00, '黑糖风味珍珠奶茶', 'img/milkyTea2.png', 1, 0, 1, 'admin', NOW(), 'admin', NOW()),
(1009, '蓝莓奶盖茶', 1003, 19.00, '蓝莓风味奶盖茶', 'img/milkyTea1.png', 0, 1, 1, 'admin', NOW(), 'admin', NOW()),
(1010, '杨枝甘露', 1005, 20.00, '季节限定杨枝甘露', 'img/milkyTea2.png', 1, 1, 1, 'admin', NOW(), 'admin', NOW());

-- 3. 原料分类数据
INSERT INTO `material_category` (`category_id`, `category_name`, `parent_id`, `order_num`, `status`, `create_time`, `update_time`)
VALUES
(2001, '茶叶类', 0, 1, 1, NOW(), NOW()),
(2002, '乳制品', 0, 2, 1, NOW(), NOW()),
(2003, '糖类', 0, 3, 1, NOW(), NOW()),
(2004, '水果类', 0, 4, 1, NOW(), NOW()),
(2005, '小料类', 0, 5, 1, NOW(), NOW()),
(2006, '果酱类', 0, 6, 1, NOW(), NOW()),
(2007, '咖啡类', 0, 7, 1, NOW(), NOW()),
(2008, '包装材料', 0, 8, 1, NOW(), NOW()),
(2009, '添加剂', 0, 9, 1, NOW(), NOW()),
(2010, '其他原料', 0, 10, 1, NOW(), NOW());

-- 4. 供应商数据
INSERT INTO `supplier` (`supplier_id`, `supplier_name`, `contact_person`, `contact_phone`, `email`, `address`, `status`, `create_time`, `update_time`)
VALUES
(3001, '优质茶叶供应商', '张三', '13800138001', 'tea@example.com', '北京市朝阳区', 1, NOW(), NOW()),
(3002, '新鲜水果供应商', '李四', '13800138002', 'fruit@example.com', '上海市浦东新区', 1, NOW(), NOW()),
(3003, '乳制品供应商', '王五', '13800138003', 'milk@example.com', '广州市天河区', 1, NOW(), NOW()),
(3004, '包装材料供应商', '赵六', '13800138004', 'packing@example.com', '深圳市南山区', 1, NOW(), NOW()),
(3005, '小料供应商', '孙七', '13800138005', 'topping@example.com', '杭州市西湖区', 1, NOW(), NOW()),
(3006, '咖啡供应商', '周八', '13800138006', 'coffee@example.com', '成都市锦江区', 1, NOW(), NOW()),
(3007, '糖类供应商', '吴九', '13800138007', 'sugar@example.com', '重庆市渝中区', 1, NOW(), NOW()),
(3008, '果酱供应商', '郑十', '13800138008', 'jam@example.com', '武汉市江汉区', 1, NOW(), NOW()),
(3009, '添加剂供应商', '钱一', '13800138009', 'additive@example.com', '南京市玄武区', 1, NOW(), NOW()),
(3010, '综合原料供应商', '孙二', '13800138010', 'general@example.com', '西安市雁塔区', 1, NOW(), NOW());

-- 5. 原料信息数据
INSERT INTO `material_info` (`material_id`, `material_name`, `category_id`, `specification`, `unit`, `supplier_id`, `purchase_price`, `shelf_life`, `warning_days`, `status`, `create_by`, `create_time`, `update_by`, `update_time`)
VALUES
(4001, '红茶茶叶', 2001, '500g/包', '包', 3001, 50.0000, 180, 15, 1, 'admin', NOW(), 'admin', NOW()),
(4002, '绿茶茶叶', 2001, '500g/包', '包', 3001, 45.0000, 180, 15, 1, 'admin', NOW(), 'admin', NOW()),
(4003, '牛奶', 2002, '1L/盒', '盒', 3003, 12.0000, 15, 3, 1, 'admin', NOW(), 'admin', NOW()),
(4004, '珍珠', 2005, '1kg/包', '包', 3005, 25.0000, 90, 10, 1, 'admin', NOW(), 'admin', NOW()),
(4005, '白砂糖', 2003, '5kg/包', '包', 3007, 20.0000, 365, 30, 1, 'admin', NOW(), 'admin', NOW()),
(4006, '草莓果酱', 2006, '1kg/瓶', '瓶', 3008, 40.0000, 180, 15, 1, 'admin', NOW(), 'admin', NOW()),
(4007, '芝士', 2002, '500g/盒', '盒', 3003, 60.0000, 30, 5, 1, 'admin', NOW(), 'admin', NOW()),
(4008, '柠檬', 2004, '500g/份', '份', 3002, 15.0000, 7, 2, 1, 'admin', NOW(), 'admin', NOW()),
(4009, '咖啡粉', 2007, '500g/包', '包', 3006, 80.0000, 270, 20, 1, 'admin', NOW(), 'admin', NOW()),
(4010, '一次性杯子', 2008, '500个/箱', '箱', 3004, 60.0000, 730, 60, 1, 'admin', NOW(), 'admin', NOW());

-- 6. 仓库数据
INSERT INTO `warehouse` (`warehouse_id`, `warehouse_name`, `address`, `manager_id`, `status`, `create_time`, `update_time`)
VALUES
(5001, '主仓库', '深圳市南山区科技园', 1, 1, NOW(), NOW()),
(5002, '冷鲜仓库', '深圳市南山区科技园', 1, 1, NOW(), NOW()),
(5003, '包装材料库', '深圳市南山区科技园', 1, 1, NOW(), NOW());

-- 7. 库存信息数据
INSERT INTO `stock_info` (`stock_id`, `material_id`, `quantity`, `alert_threshold`, `warehouse_id`, `location`, `create_time`, `update_time`)
VALUES
(6001, 4001, 50.0000, 5.0000, 5001, 'A区-01', NOW(), NOW()),
(6002, 4002, 45.0000, 5.0000, 5001, 'A区-02', NOW(), NOW()),
(6003, 4003, 30.0000, 10.0000, 5002, 'B区-01', NOW(), NOW()),
(6004, 4004, 20.0000, 3.0000, 5001, 'A区-03', NOW(), NOW()),
(6005, 4005, 15.0000, 5.0000, 5001, 'A区-04', NOW(), NOW()),
(6006, 4006, 18.0000, 5.0000, 5001, 'A区-05', NOW(), NOW()),
(6007, 4007, 12.0000, 3.0000, 5002, 'B区-02', NOW(), NOW()),
(6008, 4008, 25.0000, 10.0000, 5002, 'B区-03', NOW(), NOW()),
(6009, 4009, 8.0000, 2.0000, 5001, 'A区-06', NOW(), NOW()),
(6010, 4010, 5.0000, 1.0000, 5003, 'C区-01', NOW(), NOW());

-- 8. 库存变动记录数据
INSERT INTO `stock_record` (`record_id`, `material_id`, `type`, `quantity`, `before_quantity`, `after_quantity`, `order_id`, `reference_no`, `operator_id`, `remark`, `create_time`)
VALUES
(7001, 4001, 1, 50.0000, 0.0000, 50.0000, 1001, 'PO-20240101001', 1, '首次入库', NOW()),
(7002, 4002, 1, 45.0000, 0.0000, 45.0000, 1001, 'PO-20240101001', 1, '首次入库', NOW()),
(7003, 4003, 1, 30.0000, 0.0000, 30.0000, 1002, 'PO-20240101002', 1, '首次入库', NOW()),
(7004, 4004, 1, 20.0000, 0.0000, 20.0000, 1002, 'PO-20240101002', 1, '首次入库', NOW()),
(7005, 4001, 2, 2.0000, 50.0000, 48.0000, 2001, 'SO-20240101001', 1, '日常消耗', NOW()),
(7006, 4002, 2, 1.5000, 45.0000, 43.5000, 2002, 'SO-20240101002', 1, '日常消耗', NOW()),
(7007, 4003, 2, 5.0000, 30.0000, 25.0000, 2001, 'SO-20240101001', 1, '日常消耗', NOW()),
(7008, 4004, 2, 3.0000, 20.0000, 17.0000, 2001, 'SO-20240101001', 1, '日常消耗', NOW()),
(7009, 4005, 3, 15.0000, 14.5000, 15.0000, NULL, 'INV-20240101001', 1, '盘点调整', NOW()),
(7010, 4006, 4, 0.5000, 18.5000, 18.0000, NULL, 'LOSS-20240101001', 1, '过期报损', NOW());

-- 9. 顾客数据
INSERT INTO `customer` (`customer_id`, `customer_name`, `phone`, `avatar`, `member_level`, `points`, `total_consume`, `create_time`, `update_time`)
VALUES
(8001, '张三', '13812345678', NULL, 2, 120, 120.00, NOW(), NOW()),
(8002, '李四', '13887654321', NULL, 1, 50, 50.00, NOW(), NOW()),
(8003, '王五', '13912345678', NULL, 3, 300, 300.00, NOW(), NOW()),
(8004, '赵六', '13987654321', NULL, 1, 30, 30.00, NOW(), NOW()),
(8005, '孙七', '13712345678', NULL, 2, 150, 150.00, NOW(), NOW()),
(8006, '周八', '13787654321', NULL, 1, 20, 20.00, NOW(), NOW()),
(8007, '吴九', '13612345678', NULL, 3, 400, 400.00, NOW(), NOW()),
(8008, '郑十', '13687654321', NULL, 2, 180, 180.00, NOW(), NOW()),
(8009, '钱一', '13512345678', NULL, 1, 40, 40.00, NOW(), NOW()),
(8010, '孙二', '13587654321', NULL, 2, 160, 160.00, NOW(), NOW());

-- 10. 订单主表数据
INSERT INTO `order_main` (`order_id`, `order_no`, `customer_id`, `customer_name`, `customer_phone`, `total_amount`, `actual_amount`, `discount_amount`, `order_type`, `status`, `payment_status`, `payment_type`, `table_no`, `delivery_address`, `remark`, `operator_id`, `create_time`, `update_time`, `payment_time`, `completion_time`)
VALUES
(9001, 'ORD-20240101001', 8001, '张三', '13812345678', 12.00, 12.00, 0.00, 1, 6, 1, 1, 'A01', NULL, '少冰', 1, NOW(), NOW(), NOW(), NOW()),
(9002, 'ORD-20240101002', 8002, '李四', '13887654321', 15.00, 15.00, 0.00, 2, 6, 1, 2, NULL, '深圳市南山区科技园', '正常', 1, NOW(), NOW(), NOW(), NOW()),
(9003, 'ORD-20240101003', 8003, '王五', '13912345678', 18.00, 16.00, 2.00, 3, 6, 1, 1, NULL, NULL, '多芝士', 1, NOW(), NOW(), NOW(), NOW()),
(9004, 'ORD-20240101004', 8004, '赵六', '13987654321', 14.00, 14.00, 0.00, 1, 6, 1, 3, 'B02', NULL, '常温', 1, NOW(), NOW(), NOW(), NOW()),
(9005, 'ORD-20240101005', 8005, '孙七', '13712345678', 16.00, 16.00, 0.00, 2, 6, 1, 1, NULL, '深圳市福田区', '少糖', 1, NOW(), NOW(), NOW(), NOW()),
(9006, 'ORD-20240101006', 8006, '周八', '13787654321', 13.00, 13.00, 0.00, 3, 6, 1, 2, NULL, NULL, '正常', 1, NOW(), NOW(), NOW(), NOW()),
(9007, 'ORD-20240101007', 8007, '吴九', '13612345678', 17.00, 15.00, 2.00, 1, 6, 1, 1, 'C03', NULL, '多冰', 1, NOW(), NOW(), NOW(), NOW()),
(9008, 'ORD-20240101008', 8008, '郑十', '13687654321', 15.00, 15.00, 0.00, 2, 6, 1, 1, NULL, '深圳市罗湖区', '正常', 1, NOW(), NOW(), NOW(), NOW()),
(9009, 'ORD-20240101009', 8009, '钱一', '13512345678', 19.00, 19.00, 0.00, 3, 6, 1, 3, NULL, NULL, '少奶盖', 1, NOW(), NOW(), NOW(), NOW()),
(9010, 'ORD-20240101010', 8010, '孙二', '13587654321', 20.00, 18.00, 2.00, 1, 6, 1, 2, 'A04', NULL, '正常', 1, NOW(), NOW(), NOW(), NOW());

-- 11. 订单明细表数据
INSERT INTO `order_detail` (`detail_id`, `order_id`, `product_id`, `product_name`, `quantity`, `price`, `subtotal`, `customization`, `create_time`)
VALUES
(10001, 9001, 1001, '原味珍珠奶茶', 1, 12.00, 12.00, '少冰', NOW()),
(10002, 9002, 1002, '草莓果茶', 1, 15.00, 15.00, '正常', NOW()),
(10003, 9003, 1003, '芝士奶盖绿茶', 1, 18.00, 18.00, '多芝士', NOW()),
(10004, 9004, 1004, '柠檬冷泡茶', 1, 14.00, 14.00, '常温', NOW()),
(10005, 9005, 1005, '芒果冰沙', 1, 16.00, 16.00, '少糖', NOW()),
(10006, 9006, 1006, '红豆奶茶', 1, 13.00, 13.00, '正常', NOW()),
(10007, 9007, 1007, '焦糖咖啡', 1, 17.00, 17.00, '多冰', NOW()),
(10008, 9008, 1008, '黑糖珍珠奶茶', 1, 15.00, 15.00, '正常', NOW()),
(10009, 9009, 1009, '蓝莓奶盖茶', 1, 19.00, 19.00, '少奶盖', NOW()),
(10010, 9010, 1010, '杨枝甘露', 1, 20.00, 20.00, '正常', NOW());

-- 12. 订单状态历史数据
INSERT INTO `order_status_history` (`history_id`, `order_id`, `old_status`, `new_status`, `operator_id`, `remark`, `create_time`)
VALUES
(11001, 9001, 0, 1, 1, '订单创建', NOW()),
(11002, 9001, 1, 2, 1, '支付成功', NOW()),
(11003, 9001, 2, 3, 1, '开始制作', NOW()),
(11004, 9001, 3, 6, 1, '制作完成', NOW()),
(11005, 9002, 0, 1, 1, '订单创建', NOW()),
(11006, 9002, 1, 2, 1, '支付成功', NOW()),
(11007, 9002, 2, 3, 1, '开始制作', NOW()),
(11008, 9002, 3, 4, 1, '待配送', NOW()),
(11009, 9002, 4, 5, 1, '配送中', NOW()),
(11010, 9002, 5, 6, 1, '配送完成', NOW());

-- 13. 产品配方数据
INSERT INTO `product_formula` (`formula_id`, `product_id`, `material_id`, `quantity`, `unit`, `create_time`, `update_time`)
VALUES
(12001, 1001, 4001, 0.0500, 'kg', NOW(), NOW()),
(12002, 1001, 4003, 0.2000, 'L', NOW(), NOW()),
(12003, 1001, 4004, 0.0500, 'kg', NOW(), NOW()),
(12004, 1001, 4005, 0.0300, 'kg', NOW(), NOW()),
(12005, 1002, 4002, 0.0500, 'kg', NOW(), NOW()),
(12006, 1002, 4006, 0.1000, 'kg', NOW(), NOW()),
(12007, 1002, 4005, 0.0200, 'kg', NOW(), NOW()),
(12008, 1003, 4002, 0.0500, 'kg', NOW(), NOW()),
(12009, 1003, 4007, 0.0800, 'kg', NOW(), NOW()),
(12010, 1003, 4005, 0.0200, 'kg', NOW(), NOW());

-- 14. 产品属性数据
INSERT INTO `product_attribute` (`attribute_id`, `attribute_name`, `attribute_value`, `product_id`, `create_time`, `update_time`)
VALUES
(13001, '温度选择', '{"options":["冰","常温","热"]}', 1001, NOW(), NOW()),
(13002, '糖度选择', '{"options":["无糖","少糖","正常","多糖"]}', 1001, NOW(), NOW()),
(13003, '小料选择', '{"options":["珍珠","椰果","红豆","布丁"]}', 1001, NOW(), NOW()),
(13004, '温度选择', '{"options":["冰","常温","热"]}', 1002, NOW(), NOW()),
(13005, '糖度选择', '{"options":["无糖","少糖","正常","多糖"]}', 1002, NOW(), NOW()),
(13006, '温度选择', '{"options":["冰","常温"]}', 1003, NOW(), NOW()),
(13007, '糖度选择', '{"options":["无糖","少糖","正常"]}', 1003, NOW(), NOW()),
(13008, '奶盖厚度', '{"options":["标准","厚"]}', 1003, NOW(), NOW()),
(13009, '温度选择', '{"options":["冰"]}', 1004, NOW(), NOW()),
(13010, '糖度选择', '{"options":["少糖","正常","多糖"]}', 1004, NOW(), NOW());

-- 15. 采购订单数据
INSERT INTO `purchase_order` (`purchase_id`, `purchase_no`, `supplier_id`, `total_amount`, `status`, `remark`, `creator_id`, `auditor_id`, `create_time`, `update_time`, `audit_time`, `completion_time`)
VALUES
(14001, 'PO-20240101001', 3001, 4750.00, 4, '茶叶采购', 1, 1, NOW(), NOW(), NOW(), NOW()),
(14002, 'PO-20240101002', 3003, 1200.00, 4, '乳制品采购', 1, 1, NOW(), NOW(), NOW(), NOW()),
(14003, 'PO-20240101003', 3005, 500.00, 4, '小料采购', 1, 1, NOW(), NOW(), NOW(), NOW()),
(14004, 'PO-20240101004', 3002, 750.00, 4, '水果采购', 1, 1, NOW(), NOW(), NOW(), NOW()),
(14005, 'PO-20240101005', 3007, 300.00, 4, '糖类采购', 1, 1, NOW(), NOW(), NOW(), NOW()),
(14006, 'PO-20240101006', 3008, 3200.00, 4, '果酱采购', 1, 1, NOW(), NOW(), NOW(), NOW()),
(14007, 'PO-20240101007', 3006, 1600.00, 4, '咖啡采购', 1, 1, NOW(), NOW(), NOW(), NOW()),
(14008, 'PO-20240101008', 3004, 600.00, 4, '包装材料采购', 1, 1, NOW(), NOW(), NOW(), NOW()),
(14009, 'PO-20240101009', 3009, 900.00, 3, '添加剂采购', 1, 1, NOW(), NOW(), NOW(), NULL),
(14010, 'PO-20240101010', 3010, 2000.00, 2, '综合原料采购', 1, 1, NOW(), NOW(), NOW(), NULL);

-- 16. 采购明细数据
INSERT INTO `purchase_detail` (`detail_id`, `purchase_id`, `material_id`, `material_name`, `specification`, `unit`, `quantity`, `unit_price`, `amount`, `actual_quantity`, `create_time`, `update_time`)
VALUES
(15001, 14001, 4001, '红茶茶叶', '500g/包', '包', 50.0000, 50.0000, 2500.00, 50.0000, NOW(), NOW()),
(15002, 14001, 4002, '绿茶茶叶', '500g/包', '包', 50.0000, 45.0000, 2250.00, 50.0000, NOW(), NOW()),
(15003, 14002, 4003, '牛奶', '1L/盒', '盒', 100.0000, 12.0000, 1200.00, 100.0000, NOW(), NOW()),
(15004, 14003, 4004, '珍珠', '1kg/包', '包', 20.0000, 25.0000, 500.00, 20.0000, NOW(), NOW()),
(15005, 14004, 4008, '柠檬', '500g/份', '份', 50.0000, 15.0000, 750.00, 50.0000, NOW(), NOW()),
(15006, 14005, 4005, '白砂糖', '5kg/包', '包', 15.0000, 20.0000, 300.00, 15.0000, NOW(), NOW()),
(15007, 14006, 4006, '草莓果酱', '1kg/瓶', '瓶', 80.0000, 40.0000, 3200.00, 80.0000, NOW(), NOW()),
(15008, 14007, 4009, '咖啡粉', '500g/包', '包', 20.0000, 80.0000, 1600.00, 20.0000, NOW(), NOW()),
(15009, 14008, 4010, '一次性杯子', '500个/箱', '箱', 10.0000, 60.0000, 600.00, 10.0000, NOW(), NOW()),
(15010, 14009, 4007, '芝士', '500g/盒', '盒', 15.0000, 60.0000, 900.00, NULL, NOW(), NOW());

SET FOREIGN_KEY_CHECKS = 1;