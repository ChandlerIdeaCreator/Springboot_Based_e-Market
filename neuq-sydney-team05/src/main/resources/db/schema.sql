-- E-commerce platform schema (MySQL 8+)
-- Charset/Engine
SET NAMES utf8mb4;

-- Drop in dependency-safe order
DROP TABLE IF EXISTS `logistics`;
DROP TABLE IF EXISTS `order_item`;
DROP TABLE IF EXISTS `order`;
DROP TABLE IF EXISTS `product_comment`;
DROP TABLE IF EXISTS `cart`;
DROP TABLE IF EXISTS `favorite`;
DROP TABLE IF EXISTS `product_style`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `user_address`;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `login_code`;

-- 0) SMS login code (optional but recommended for phone login)
CREATE TABLE `login_code` (
  `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `phone`         VARCHAR(20)     NOT NULL,
  `code`          VARCHAR(10)     NOT NULL,
  `expire_time`   DATETIME        NOT NULL,
  `used`          TINYINT         NOT NULL DEFAULT 0 COMMENT '0:未使用,1:已使用',
  `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_login_code_phone` (`phone`),
  KEY `idx_login_code_expire` (`expire_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信验证码（登录/注册）';

-- 1) 用户
CREATE TABLE `user` (
  `id`           BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `phone`        VARCHAR(20)     NOT NULL,
  `nickname`     VARCHAR(50)     DEFAULT NULL,
  `gender`       TINYINT         DEFAULT NULL COMMENT '0:女,1:男,2:未知',
  `birthday`     DATE            DEFAULT NULL,
  `create_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`  DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- 2) 收货地址
CREATE TABLE `user_address` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`        BIGINT UNSIGNED NOT NULL,
  `receiver`       VARCHAR(50)     NOT NULL,
  `phone`          VARCHAR(20)     NOT NULL,
  `region`         VARCHAR(100)    NOT NULL,
  `detail_address` VARCHAR(200)    NOT NULL,
  `is_default`     TINYINT         NOT NULL DEFAULT 0 COMMENT '0:默认,1:非默认',
  `create_time`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_user_address_user` (`user_id`),
  CONSTRAINT `fk_user_address_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收货地址';

-- 3) 商品
CREATE TABLE `product` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `shop_type`      TINYINT         NOT NULL COMMENT '0:旗舰店,1:普通店',
  `product_name`   VARCHAR(200)    NOT NULL,
  `original_price` DECIMAL(10,2)   NOT NULL,
  `discount_price` DECIMAL(10,2)   NOT NULL,
  `joined_count`   INT             NOT NULL DEFAULT 0,
  `product_img`    VARCHAR(500)    NOT NULL COMMENT '多图逗号分隔',
  `quality_check`  TINYINT         NOT NULL COMMENT '0:检验,1:未检验',
  `create_time`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_product_name` (`product_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品';

-- 4) 商品款式（简单款式；如需多维SKU可扩展为 product_sku）
CREATE TABLE `product_style` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_id`  BIGINT UNSIGNED NOT NULL,
  `style_name`  VARCHAR(100)    NOT NULL,
  `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_style_product_name` (`product_id`,`style_name`),
  KEY `idx_style_product` (`product_id`),
  CONSTRAINT `fk_style_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品款式';

-- 5) 商品评价
CREATE TABLE `product_comment` (
  `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `product_id`      BIGINT UNSIGNED NOT NULL,
  `user_id`         BIGINT UNSIGNED NOT NULL,
  `nickname`        VARCHAR(50)     NOT NULL,
  `comment_content` TEXT            NULL,
  `comment_img`     VARCHAR(500)    NULL,
  `is_anonymous`    TINYINT         NOT NULL COMMENT '0:匿名,1:未匿名',
  `create_time`     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_comment_product` (`product_id`),
  KEY `idx_comment_user` (`user_id`),
  CONSTRAINT `fk_comment_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_comment_user`    FOREIGN KEY (`user_id`)    REFERENCES `user`    (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品评价';

-- 6) 订单（主表，存快照信息与状态）
CREATE TABLE `order` (
  `id`            BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_no`      VARCHAR(32)     NOT NULL,
  `user_id`       BIGINT UNSIGNED NOT NULL,
  `order_status`  TINYINT         NOT NULL DEFAULT 0 COMMENT '0:待付款,1:已付款,2:已取消,3:已完成',
  `total_amount`  DECIMAL(10,2)   NOT NULL COMMENT '应付金额（订单汇总）',
  -- 地址/收件人快照（避免后续地址修改影响历史订单）
  `receiver`      VARCHAR(50)     NOT NULL,
  `receiver_phone`VARCHAR(20)     NOT NULL,
  `region`        VARCHAR(100)    NOT NULL,
  `detail_address`VARCHAR(200)    NOT NULL,
  -- 支付/时间
  `pay_time`      DATETIME        DEFAULT NULL,
  `create_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`   DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_order_user` (`user_id`),
  CONSTRAINT `fk_order_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单主表';

-- 6.1) 订单明细（支持一单多商品）
CREATE TABLE `order_item` (
  `id`             BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id`       BIGINT UNSIGNED NOT NULL,
  `product_id`     BIGINT UNSIGNED NOT NULL,
  `style_id`       BIGINT UNSIGNED DEFAULT NULL,
  `product_name`   VARCHAR(200)    NOT NULL COMMENT '下单时快照',
  `product_img`    VARCHAR(500)    NOT NULL COMMENT '下单时快照',
  `style_name`     VARCHAR(100)    DEFAULT NULL COMMENT '下单时快照',
  `product_count`  INT             NOT NULL,
  `unit_price`     DECIMAL(10,2)   NOT NULL COMMENT '下单时单价快照',
  `total_amount`   DECIMAL(10,2)   NOT NULL COMMENT '明细小计',
  `create_time`    DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_item_order` (`order_id`),
  KEY `idx_item_product` (`product_id`),
  CONSTRAINT `fk_item_order`   FOREIGN KEY (`order_id`)   REFERENCES `order` (`id`)   ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_item_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_item_style`   FOREIGN KEY (`style_id`)   REFERENCES `product_style` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细';

-- 7) 物流（与订单关联；付款后插入，默认待发货）
CREATE TABLE `logistics` (
  `id`              BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id`        BIGINT UNSIGNED NOT NULL,
  `logistics_no`    VARCHAR(50)     NOT NULL,
  `express_name`    VARCHAR(50)     DEFAULT NULL,
  `logistics_status`TINYINT         NOT NULL DEFAULT 0 COMMENT '0:待发货,1:已发货,2:运输中,3:已签收',
  `create_time`     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time`     DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_logistics_no` (`logistics_no`),
  KEY `idx_logistics_order` (`order_id`),
  CONSTRAINT `fk_logistics_order` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流';

-- 8) 购物车
CREATE TABLE `cart` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`     BIGINT UNSIGNED NOT NULL,
  `product_id`  BIGINT UNSIGNED NOT NULL,
  `style_id`    BIGINT UNSIGNED DEFAULT NULL,
  `quantity`    INT             NOT NULL DEFAULT 1,
  `is_selected` TINYINT         NOT NULL DEFAULT 1 COMMENT '0:未选中,1:已选中',
  `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_cart_user` (`user_id`),
  KEY `idx_cart_product` (`product_id`),
  CONSTRAINT `fk_cart_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_cart_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_cart_style` FOREIGN KEY (`style_id`) REFERENCES `product_style` (`id`) ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车';

-- 9) 收藏/心愿单
CREATE TABLE `favorite` (
  `id`          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id`     BIGINT UNSIGNED NOT NULL,
  `product_id`  BIGINT UNSIGNED NOT NULL,
  `create_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_favorite_user_product` (`user_id`, `product_id`),
  KEY `idx_favorite_user` (`user_id`),
  KEY `idx_favorite_product` (`product_id`),
  CONSTRAINT `fk_favorite_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `fk_favorite_product` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收藏';

-- Helpful check constraints (optional for MySQL 8.0.16+)
-- ALTER TABLE `user_address` ADD CONSTRAINT chk_user_address_is_default CHECK (`is_default` IN (0,1));
-- ALTER TABLE `product`       ADD CONSTRAINT chk_product_shop_type CHECK (`shop_type` IN (0,1));
-- ALTER TABLE `product`       ADD CONSTRAINT chk_product_quality  CHECK (`quality_check` IN (0,1));
-- ALTER TABLE `product_comment` ADD CONSTRAINT chk_comment_is_anonymous CHECK (`is_anonymous` IN (0,1));
-- ALTER TABLE `order`         ADD CONSTRAINT chk_order_status CHECK (`order_status` IN (0,1,2,3));
-- ALTER TABLE `logistics`     ADD CONSTRAINT chk_logistics_status CHECK (`logistics_status` IN (0,1,2,3));
-- ALTER TABLE `cart`          ADD CONSTRAINT chk_cart_is_selected CHECK (`is_selected` IN (0,1)); 