-- ----------------------------
-- Chat2DB export data , export time: 2025-09-15 14:06:59
-- ----------------------------
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for table tc_pay_method
-- ----------------------------
DROP TABLE IF EXISTS `tc_pay_method`;
CREATE TABLE `tc_pay_method` (
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '名称',
  `pay_model` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '支付模式',
  `asset_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '资产类型',
  `pay_inst` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '支付机构',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `extension` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展信息',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付方式';

-- ----------------------------
-- Records of tc_pay_method
-- ----------------------------
INSERT INTO `tc_pay_method` (`code`,`name`,`pay_model`,`asset_type`,`pay_inst`,`status`,`extension`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('BALANCE_101','余额支付','BALANCE','BALANCE','101','E',NULL,'普通余额支付','2025-05-29 12:00:45','2025-05-29 12:00:45');
INSERT INTO `tc_pay_method` (`code`,`name`,`pay_model`,`asset_type`,`pay_inst`,`status`,`extension`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('TESTBANK','测试银行','ONLINE_BANK','BANKCARD','UNLIMITED','E',NULL,'','2025-05-29 11:38:47','2025-05-29 11:44:02');
-- ----------------------------
-- Table structure for table tf_flux_order
-- ----------------------------
DROP TABLE IF EXISTS `tf_flux_order`;
CREATE TABLE `tf_flux_order` (
  `flux_order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交换单ID',
  `order_Id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付ID',
  `trade_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易ID',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交换状态',
  `relation_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联订单ID',
  `pay_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付指令类型',
  `result_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果码',
  `result_msg` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果信息',
  `gmt_create` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  PRIMARY KEY (`flux_order_id`),
  UNIQUE KEY `uk_pay_order_id` (`order_Id`),
  KEY `idx_payment_id` (`trade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交换单';

-- ----------------------------
-- Records of tf_flux_order
-- ----------------------------
-- ----------------------------
-- Table structure for table tf_flux_process
-- ----------------------------
DROP TABLE IF EXISTS `tf_flux_process`;
CREATE TABLE `tf_flux_process` (
  `flux_process_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交换指令ID',
  `pre_process_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '上一条指令ID',
  `flux_order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交换单ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付ID',
  `trade_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易ID',
  `type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '类型',
  `direction` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '方向',
  `fund_action` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资金操作类型',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '指令状态',
  `relation_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联订单ID',
  `amount` decimal(19,4) NOT NULL COMMENT '订单金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种',
  `fund_detail_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资金明细ID',
  `asset_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资产类型',
  `asset_info` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资产信息',
  `extension` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段',
  `result_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果码',
  `result_msg` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果信息',
  `gmt_create` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  PRIMARY KEY (`flux_process_id`),
  KEY `idx_flux_order_id` (`flux_order_id`),
  KEY `idx_payment_id` (`trade_id`),
  KEY `idx_pay_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='交换指令';

-- ----------------------------
-- Records of tf_flux_process
-- ----------------------------
-- ----------------------------
-- Table structure for table tf_sequence
-- ----------------------------
DROP TABLE IF EXISTS `tf_sequence`;
CREATE TABLE `tf_sequence` (
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '序列名',
  `current_value` bigint NOT NULL DEFAULT '1' COMMENT '当前值',
  `increment` smallint NOT NULL DEFAULT '1' COMMENT '增长步长',
  `total` smallint NOT NULL DEFAULT '20' COMMENT '单次取值总量，更新总量需重启应用',
  `threshold` smallint NOT NULL DEFAULT '20' COMMENT '刷新阀值，更新阀值需重启应用',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='序列';

-- ----------------------------
-- Records of tf_sequence
-- ----------------------------
INSERT INTO `tf_sequence` (`name`,`current_value`,`increment`,`total`,`threshold`)  VALUES ('seq_flux_instruct_id','40301','1','20','20');
INSERT INTO `tf_sequence` (`name`,`current_value`,`increment`,`total`,`threshold`)  VALUES ('seq_flux_order_id','29521','1','20','20');
INSERT INTO `tf_sequence` (`name`,`current_value`,`increment`,`total`,`threshold`)  VALUES ('seq_fund_detail_id','32581','1','20','20');
INSERT INTO `tf_sequence` (`name`,`current_value`,`increment`,`total`,`threshold`)  VALUES ('seq_pay_order_id','31381','1','20','20');
INSERT INTO `tf_sequence` (`name`,`current_value`,`increment`,`total`,`threshold`)  VALUES ('seq_trade_id','28641','1','20','20');
-- ----------------------------
-- Table structure for table tm_member
-- ----------------------------
DROP TABLE IF EXISTS `tm_member`;
CREATE TABLE `tm_member` (
  `member_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会员ID',
  `member_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会员类型',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '名称',
  `phone` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `gmt_create` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  PRIMARY KEY (`member_id`),
  KEY `idx_member_type` (`member_type`),
  KEY `idx_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会员表';

-- ----------------------------
-- Records of tm_member
-- ----------------------------
-- ----------------------------
-- Table structure for table tm_member_bank_card
-- ----------------------------
DROP TABLE IF EXISTS `tm_member_bank_card`;
CREATE TABLE `tm_member_bank_card` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `member_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会员ID',
  `card_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡号',
  `card_id_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证号码',
  `card_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `bank_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行编码',
  `mobile` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `gmt_create` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_member_id` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会员银行卡';

-- ----------------------------
-- Records of tm_member_bank_card
-- ----------------------------
-- ----------------------------
-- Table structure for table tm_partner_scene
-- ----------------------------
DROP TABLE IF EXISTS `tm_partner_scene`;
CREATE TABLE `tm_partner_scene` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `partner_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合作ID',
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '场景编码',
  `name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '场景名称',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_partner_code` (`partner_id`,`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='合作方场景';

-- ----------------------------
-- Records of tm_partner_scene
-- ----------------------------
-- ----------------------------
-- Table structure for table tm_scene_pay_method
-- ----------------------------
DROP TABLE IF EXISTS `tm_scene_pay_method`;
CREATE TABLE `tm_scene_pay_method` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `scene_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '场景编码',
  `pay_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付方式编码',
  `extension` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展信息',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_scene_pay_method` (`scene_code`,`pay_method`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='场景支付方式';

-- ----------------------------
-- Records of tm_scene_pay_method
-- ----------------------------
-- ----------------------------
-- Table structure for table tp_fund_detail
-- ----------------------------
DROP TABLE IF EXISTS `tp_fund_detail`;
CREATE TABLE `tp_fund_detail` (
  `detail_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '明细ID',
  `order_Id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付ID',
  `trade_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易ID',
  `relation_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联ID，如退款关联的原ID',
  `belong_to` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '归属方',
  `member_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '会员ID',
  `amount` decimal(19,4) NOT NULL COMMENT '订单金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种',
  `fund_action` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资金动作',
  `asset_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资产类型',
  `asset_info` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '资产信息',
  `pay_model` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付模式',
  `pay_param` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '交换参数',
  `extension` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_payment_id` (`trade_id`),
  KEY `idx_order_id` (`order_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付单资金明细';

-- ----------------------------
-- Records of tp_fund_detail
-- ----------------------------
-- ----------------------------
-- Table structure for table tp_pay_order
-- ----------------------------
DROP TABLE IF EXISTS `tp_pay_order`;
CREATE TABLE `tp_pay_order` (
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付ID',
  `trade_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易ID',
  `member_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会员ID',
  `amount` decimal(19,4) NOT NULL COMMENT '订单金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `result_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果码',
  `result_msg` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '结果信息',
  `extension` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展信息',
  `gmt_create` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_payment_id` (`trade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='支付单';

-- ----------------------------
-- Records of tp_pay_order
-- ----------------------------
-- ----------------------------
-- Table structure for table tp_refund_order
-- ----------------------------
DROP TABLE IF EXISTS `tp_refund_order`;
CREATE TABLE `tp_refund_order` (
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '退款ID',
  `trade_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易ID',
  `refund_type` varchar(16) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '退款类型',
  `relation_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '关联原支付指令ID',
  `member_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会员ID',
  `amount` decimal(19,4) NOT NULL COMMENT '订单金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `extension` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展信息',
  `gmt_create` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_payment_id` (`trade_id`),
  KEY `idx_relation_id` (`relation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='退款订单';

-- ----------------------------
-- Records of tp_refund_order
-- ----------------------------
-- ----------------------------
-- Table structure for table tt_acquiring_order
-- ----------------------------
DROP TABLE IF EXISTS `tt_acquiring_order`;
CREATE TABLE `tt_acquiring_order` (
  `trade_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易ID',
  `relation_trade_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '关联交易ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '支付ID',
  `trade_type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '交易类型',
  `out_trade_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合作方交易单号',
  `partner_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合作ID',
  `payee_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收款方ID',
  `payee_account_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '收款方账户号',
  `payer_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '付款方ID',
  `subject` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '标题',
  `amount` decimal(19,4) NOT NULL COMMENT '金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `gmt_expire` timestamp NULL DEFAULT NULL COMMENT '过期时间',
  `extension` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展信息',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`trade_id`),
  UNIQUE KEY `uk_partner_trade_no` (`out_trade_no`,`partner_id`),
  KEY `idx_relation_payment_id` (`relation_trade_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='收单订单';

-- ----------------------------
-- Records of tt_acquiring_order
-- ----------------------------
-- ----------------------------
-- Table structure for table tt_deposit_order
-- ----------------------------
DROP TABLE IF EXISTS `tt_deposit_order`;
CREATE TABLE `tt_deposit_order` (
  `trade_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '充值ID',
  `partner_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合作方ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付ID',
  `member_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会员ID',
  `account_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户号',
  `amount` decimal(19,4) NOT NULL COMMENT '订单金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单状态',
  `extension` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展信息',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  PRIMARY KEY (`trade_id`),
  KEY `idx_pay_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='充值单';

-- ----------------------------
-- Records of tt_deposit_order
-- ----------------------------
-- ----------------------------
-- Table structure for table tt_withdraw_order
-- ----------------------------
DROP TABLE IF EXISTS `tt_withdraw_order`;
CREATE TABLE `tt_withdraw_order` (
  `trade_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '提现ID',
  `partner_id` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合作方ID',
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付ID',
  `member_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会员ID',
  `account_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户号',
  `amount` decimal(19,4) NOT NULL COMMENT '订单金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单状态',
  `card_id_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证号码',
  `card_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `bank_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '银行编码',
  `card_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '卡号',
  `extension` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展信息',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `gmt_modified` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '最后修改时间',
  PRIMARY KEY (`trade_id`),
  KEY `idx_pay_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='提现单';

-- ----------------------------
-- Records of tt_withdraw_order
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
