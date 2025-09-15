-- ----------------------------
-- Chat2DB export data , export time: 2025-09-15 14:07:09
-- ----------------------------
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for table t_pay_order
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_order`;
CREATE TABLE `t_pay_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `out_trade_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '外部订单号',
  `subject` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `goods_desc` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '商品描述',
  `amount` decimal(19,4) NOT NULL COMMENT '金额',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `notify_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '通知URL',
  `return_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '跳转URL',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_out_trade_no` (`out_trade_no`)
) ENGINE=InnoDB AUTO_INCREMENT=100194 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付订单表';

-- ----------------------------
-- Records of t_pay_order
-- ----------------------------
-- ----------------------------
-- Table structure for table t_refund_order
-- ----------------------------
DROP TABLE IF EXISTS `t_refund_order`;
CREATE TABLE `t_refund_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pay_order_id` bigint NOT NULL COMMENT '支付订单号',
  `out_request_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '外部退款请求号',
  `amount` decimal(19,4) NOT NULL COMMENT '退款金额',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `notify_url` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '通知URL',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_out_trade_no` (`out_request_no`),
  KEY `idx_pay_order_id` (`pay_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100052 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付退款表';

-- ----------------------------
-- Records of t_refund_order
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
