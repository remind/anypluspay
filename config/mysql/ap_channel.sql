-- ----------------------------
-- Chat2DB export data , export time: 2025-09-15 14:06:51
-- ----------------------------
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for table tb_biz_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_biz_order`;
CREATE TABLE `tb_biz_order` (
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道订单号',
  `request_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求号',
  `partner_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合作方ID',
  `member_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会员ID',
  `request_root_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求根类型',
  `request_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求类型',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `inst_order_id` bigint DEFAULT NULL COMMENT '机构订单ID',
  `extension` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展信息',
  `inst_ext` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构扩展信息',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  UNIQUE KEY `uk_request_id` (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='业务订单';

-- ----------------------------
-- Records of tb_biz_order
-- ----------------------------
-- ----------------------------
-- Table structure for table tb_fund_in_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_fund_in_order`;
CREATE TABLE `tb_fund_in_order` (
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道订单号',
  `pay_model` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付模式',
  `pay_inst` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付机构',
  `amount` decimal(19,4) NOT NULL COMMENT '金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道服务-资金流入订单';

-- ----------------------------
-- Records of tb_fund_in_order
-- ----------------------------
-- ----------------------------
-- Table structure for table tb_fund_out_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_fund_out_order`;
CREATE TABLE `tb_fund_out_order` (
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道订单号',
  `pay_method` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支付模式',
  `bank_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '银行编码',
  `amount` decimal(19,4) NOT NULL COMMENT '金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种',
  `account_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户号',
  `account_name` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户名称',
  `account_type` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '账户类型：B（公司账户），C（个人账户）',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资金流出订单';

-- ----------------------------
-- Records of tb_fund_out_order
-- ----------------------------
-- ----------------------------
-- Table structure for table tb_refund_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_refund_order`;
CREATE TABLE `tb_refund_order` (
  `order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单号',
  `refund_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '退款类型',
  `orig_order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原订单号',
  `orig_request_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '原请求号',
  `amount` decimal(19,4) NOT NULL COMMENT '退款金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种',
  `reason` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原因',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`order_id`) USING BTREE,
  KEY `idx_orig_order_id` (`orig_order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道服务-退款订单';

-- ----------------------------
-- Records of tb_refund_order
-- ----------------------------
-- ----------------------------
-- Table structure for table tc_api_request_no_mode
-- ----------------------------
DROP TABLE IF EXISTS `tc_api_request_no_mode`;
CREATE TABLE `tc_api_request_no_mode` (
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `gen_pattern` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '生成模式',
  `seq_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '序列名称',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道API请求单号模式';

-- ----------------------------
-- Records of tc_api_request_no_mode
-- ----------------------------
INSERT INTO `tc_api_request_no_mode` (`code`,`gen_pattern`,`seq_name`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('TEST_FI','f:TFI001|t:YYMMdd|s:8','seq_api_request_test','','2024-09-18 17:47:35','2024-09-18 17:51:42');
INSERT INTO `tc_api_request_no_mode` (`code`,`gen_pattern`,`seq_name`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('TEST_FO','f:TFO001|t:YYMMdd|s:8','seq_api_request_test','','2025-05-16 10:56:44','2025-05-16 10:56:44');
INSERT INTO `tc_api_request_no_mode` (`code`,`gen_pattern`,`seq_name`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('TEST_RF','f:TRF001|t:YYMMdd|s:8','seq_api_request_test','','2024-09-18 17:51:42','2024-09-18 17:51:42');
INSERT INTO `tc_api_request_no_mode` (`code`,`gen_pattern`,`seq_name`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('WXPAY_FI','f:FI001|t:YYMMdd|s:8','seq_api_request_wxpay','','2024-09-18 17:45:40','2024-09-18 17:51:42');
INSERT INTO `tc_api_request_no_mode` (`code`,`gen_pattern`,`seq_name`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('WXPAY_RF','f:RF001|t:YYMMdd|s:8','seq_api_request_wxpay','','2024-09-18 17:51:42','2024-09-18 17:51:42');
-- ----------------------------
-- Table structure for table tc_api_result_code
-- ----------------------------
DROP TABLE IF EXISTS `tc_api_result_code`;
CREATE TABLE `tc_api_result_code` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `channel_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
  `api_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口类型',
  `inst_api_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机构API结果码',
  `inst_api_sub_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构API子结果码',
  `inst_api_message` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '机构API消息',
  `unity_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '统一结果码',
  `result_status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构订单结果状态',
  `use_mapping` tinyint(1) NOT NULL COMMENT '是否映射',
  `re_route_enable` tinyint(1) NOT NULL COMMENT '是否重路由',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_channel_api_code` (`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='API结果码';

-- ----------------------------
-- Records of tc_api_result_code
-- ----------------------------
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('1','LOCALBANK001','SQ','200','SUCCESS','1','S001','SU',1,0,'2024-08-07 18:05:53','2024-12-09 15:30:54');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('2','LOCALBANK001','SG','PARAM_ERROR','','','F001','FA',1,0,'2024-08-19 19:40:04','2024-12-09 15:30:54');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('3','LOCALBANK001','SG','200','','','P001','PR',1,0,'2024-08-19 19:40:04','2024-12-09 15:30:54');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('4','LOCALBANK001','SQ','ORDER_CLOSED','','','F001','FA',1,0,'2024-08-19 19:45:24','2024-12-09 15:30:54');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('5','LOCALBANK001','VS','SUCCESS','','','S001','SU',1,0,'2024-08-20 17:42:22','2024-12-09 15:30:54');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('8','LOCALBANK001','SQ','SUCCESS','','','S001','SU',1,0,'2024-08-20 18:20:34','2024-12-09 15:30:54');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('9','LOCALBANK001','SR','SUCCESS','','处理成功','S001','SU',1,0,'2024-08-20 19:15:19','2024-12-09 15:30:54');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('10','LOCALBANK001','SR','PARAM_ERROR','','参数错误','F001','FA',1,0,'2024-08-20 23:18:26','2024-12-09 15:30:54');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('11','LOCALBANK001','SR','PROCESS','','','P001','PR',1,0,'2024-08-20 23:20:24','2024-12-09 15:30:54');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('15','WXPAY001','SG','200','','','P001','PR',1,0,'2024-09-18 14:07:14','2024-09-18 14:09:20');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('16','WXPAY001','SG','PARAM_ERROR','','输入源“/body/notify_url”映射到值字段“通知地址”字符串规则校验失败，字符串必须匹配正则表达式“^https?://([^\\s/?#\\[\\]\\@]+\\@)?([^\\s/?#\\@:]+)(?::\\d{2,5})?([^\\s?#\\[\\]]*)$”','F001','FA',1,0,'2024-09-18 17:59:43','2024-09-18 21:46:34');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('17','WXPAY001','SQ','NOTPAY','','','P001','PR',1,0,'2024-09-18 21:42:54','2024-09-18 21:48:06');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('18','TESTBANK002','SG','0000','','','P001','PR',1,0,'2024-12-03 18:14:28','2024-12-04 15:28:20');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('21','TESTBANK002','VS','TRADE_SUCCESS','','','S001','SU',1,0,'2024-12-08 17:33:52','2024-12-08 18:02:04');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('22','TESTBANK002','VS','TRADE_CLOSED','','','F001','FA',1,0,'2024-12-08 18:11:57','2024-12-08 18:13:19');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('23','TESTBANK002','SR','SUCCESS','','','S001','SU',1,0,'2024-12-09 23:30:34','2024-12-09 23:36:11');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('25','TESTBANK002','SR','PROCESS','','','P001','PR',1,0,'2025-04-29 15:20:12','2025-04-29 15:52:52');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('26','TESTBANK002','RVS','SUCCESS','','','S001','SU',1,0,'2025-04-29 15:45:22','2025-04-29 15:53:03');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('28','TESTFUNDOUT001','SFO','SUCCESS','','','S001','SU',1,0,'2025-05-16 11:22:54','2025-05-16 11:24:06');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('29','ALIPAY001','SG','0000','','','P001','PR',1,0,'2025-06-03 19:50:51','2025-06-03 19:57:35');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('30','ALIPAY001','VS','TRADE_SUCCESS','','','S001','SU',1,0,'2025-06-04 14:36:31','2025-06-04 14:36:50');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('46','ALIPAY001','SQ','TRADE_SUCCESS',NULL,'','S001','SU',1,0,'2025-06-05 22:29:29','2025-06-05 22:29:54');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('73','ALIPAY001','SR','Y',NULL,'','S001','UK',0,0,'2025-06-05 22:41:44','2025-06-05 22:47:39');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('77','ALIPAY001','SG','UNKNOWN',NULL,'','',NULL,0,0,'2025-06-05 22:48:24','2025-06-05 22:48:24');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('78','ALIPAY001','SR','UNKNOWN',NULL,'','',NULL,0,0,'2025-06-05 22:49:16','2025-06-05 22:49:16');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('79','ALIPAY001','SRQ','UNKNOWN',NULL,'','',NULL,0,0,'2025-06-05 22:53:33','2025-06-05 22:53:33');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('80','TESTBANK002','SQ','TRADE_SUCCESS',NULL,'','',NULL,0,0,'2025-06-15 12:16:18','2025-06-15 12:16:18');
INSERT INTO `tc_api_result_code` (`id`,`channel_code`,`api_type`,`inst_api_code`,`inst_api_sub_code`,`inst_api_message`,`unity_code`,`result_status`,`use_mapping`,`re_route_enable`,`gmt_create`,`gmt_modified`)  VALUES ('81','TESTBANK002','SQ','WAIT_BUYER_PAY',NULL,'','',NULL,0,0,'2025-06-15 12:17:11','2025-06-15 12:17:11');
-- ----------------------------
-- Table structure for table tc_channel_api
-- ----------------------------
DROP TABLE IF EXISTS `tc_channel_api`;
CREATE TABLE `tc_channel_api` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `channel_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
  `type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口类型',
  `protocol` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '协议',
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '地址',
  `enable` tinyint(1) NOT NULL COMMENT '状态，是否可用',
  `extra` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '扩展信息',
  `request_no_mode` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '机构请求号生成模式',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_channel_api_type` (`channel_code`,`type`),
  KEY `idx_channel_code` (`channel_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道API';

-- ----------------------------
-- Records of tc_channel_api
-- ----------------------------
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1','LOCALBANK001','SG','BEAN','localBankSignGateway',1,'','TEST_FI','123456','2024-08-30 14:13:45','2025-04-03 16:58:37');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2','LOCALBANK001','SQ','BEAN','localBankQueryGateway',1,'',NULL,'','2024-08-30 14:15:31','2025-04-03 16:53:17');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('3','LOCALBANK001','SR','BEAN','localBankRefundGateway',1,'','TEST_RF','','2024-08-30 14:15:31','2025-04-03 16:53:17');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('4','LOCALBANK001','SRQ','BEAN','localBankRefundQueryGateway',1,'',NULL,'','2024-08-30 14:15:31','2025-04-03 16:58:37');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('5','LOCALBANK001','VS','BEAN','localBankVerifySignGateway',1,'',NULL,'1234','2024-08-30 14:15:31','2025-04-03 16:53:17');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('6','TESTBANK002','SG','DISCOVERY','test-bank-gateway/testBankSignGateway',1,'','WXPAY_FI','','2024-09-18 11:30:42','2025-04-07 11:46:42');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('7','TESTBANK002','SQ','DISCOVERY','test-bank-gateway/testBankQueryGateway',1,'',NULL,'','2024-09-18 21:39:57','2025-04-07 11:46:47');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('8','WXPAY001','SG','HTTP','http://127.0.0.1:8081/wxpay',1,'','WXPAY_FI','','2024-09-18 11:30:42','2025-04-03 16:58:37');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('9','WXPAY001','SQ','HTTP','http://127.0.0.1:8081/wxpay',1,'',NULL,'','2024-09-18 21:39:57','2025-04-03 16:58:37');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('15','TESTBANK002','VS','DISCOVERY','test-bank-gateway/testOnlineBankVerifySignGateway',1,'',NULL,'','2024-12-08 17:21:11','2025-04-07 11:46:51');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('16','LOCALBANK001','RVS','BEAN','localBankRefundVerifySignGateway',1,'',NULL,'','2024-12-09 16:40:35','2025-04-03 16:58:37');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('17','TESTBANK002','SR','DISCOVERY','test-bank-gateway/testBankRefundGateway',1,'','TEST_RF','','2024-12-09 23:10:00','2025-04-07 11:46:55');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('18','TESTBANK002','RVS','DISCOVERY','test-bank-gateway/testOnlineBankVerifySignGateway',1,'',NULL,'1','2025-04-29 15:32:04','2025-05-20 19:33:30');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('19','TESTFUNDOUT001','SFO','DISCOVERY','test-bank-gateway/testBankFundOutGateway',1,'','TEST_FO','','2025-05-15 21:46:38','2025-05-16 10:56:58');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('20','TESTFUNDOUT001','SQ','DISCOVERY','test-bank-gateway/testBankFundOutQueryGateway',1,'',NULL,'','2025-05-16 08:56:18','2025-05-16 08:56:18');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('21','ALIPAY001','SG','DISCOVERY','alipay-gateway/alipaySignGateway',1,'','TEST_FI','','2025-06-03 19:30:37','2025-06-03 19:39:45');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('22','ALIPAY001','VS','DISCOVERY','alipay-gateway/alipayVerifySignGateway',1,'',NULL,'','2025-06-04 14:23:19','2025-06-04 14:32:19');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('23','ALIPAY001','SQ','DISCOVERY','alipay-gateway/alipayQueryGateway',1,'',NULL,'','2025-06-05 21:28:19','2025-06-05 21:28:19');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('24','ALIPAY001','SR','DISCOVERY','alipay-gateway/alipayRefundGateway',1,'','TEST_RF','','2025-06-05 21:28:37','2025-06-05 21:33:34');
INSERT INTO `tc_channel_api` (`id`,`channel_code`,`type`,`protocol`,`address`,`enable`,`extra`,`request_no_mode`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('25','ALIPAY001','SRQ','DISCOVERY','alipay-gateway/alipayRefundQueryGateway',1,'',NULL,'','2025-06-05 21:28:57','2025-06-05 21:28:57');
-- ----------------------------
-- Table structure for table tc_channel_api_param
-- ----------------------------
DROP TABLE IF EXISTS `tc_channel_api_param`;
CREATE TABLE `tc_channel_api_param` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `partner_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '合作方ID',
  `channel_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
  `param` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数json',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_partner_channel` (`partner_id`,`channel_code`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道api参数';

-- ----------------------------
-- Records of tc_channel_api_param
-- ----------------------------
INSERT INTO `tc_channel_api_param` (`id`,`partner_id`,`channel_code`,`param`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('23','100000003','TESTBANK002','{appId:1}
','','2025-06-05 10:40:43','2025-06-19 20:18:56');
INSERT INTO `tc_channel_api_param` (`id`,`partner_id`,`channel_code`,`param`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('24','100000003','ALIPAY001','{"env":"sandbox","appId":"2021000149640040","signType":"RSA2","privateKey":"MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCYkiyYdovwZK+nhpp7ie8DX53MuSEgIqjYujiQL6xCYtT9Z3vDNeQ9LucsB6p/U+S6QrxQqxWoPIgyxZ3tyvNh76K3DEdjtZYTRznKhay6pc/+NZQfeLMU/Pt2vHZzrQU2A6qTf5yVBXjncU7R7m1bHfb164uvTMftZZ99vHO2lmhE6QaYipucOFLc4DpvnIhgnp0zkgTizFvTs02sv8s6xRWTXulyMfACy9jpUrP2wH1msKrB2hqlHUBX27Sfdyz5IwvTjumxK5vCeo9hnUBePwbuFNhzOz8P7M0jhR2Z8rRheqhHgS8UEcg4hpIoOK+PAWaTTe/93XeC/5188k2LAgMBAAECggEAI9grvAIV7HtrPTuFbcLBMZS0ORXBb5BVBN3zTgS1Pr+DdKlsTTeLEMjFLPX8G3/1O8FUBsu7AdR+1xbQfL5yoa6wFrs+xnEx+gOA01DGfO9Zx0qFK0OrJyIsXf/3AERE9iS0XYTcn7IyboWTVATlEkQe8jXNknjCe8alSuAMe3fKpkAwOTYVa1C6XMAPx6yXeitPZUY/cQEIV0SljRP4Z4DcbwyJBaaV6kFCl1PNUA/cpNTXc+5Y3F9rlS/fWwtLB8yLdbyUY/F0vsY9tYkiuD0t1mnj5XbMFNdAfgTVgNdeI/OIdKQASwlZ9w1B0QUlbhmeT8UPRXLkgbgqReqJYQKBgQDoAPpMxpG+yWAwPdK1FtPy6WwE4PnCzFtG6r8RqL7kCX5+ZTrmO0GyeSbKX2i6AjF6PReoXz0RevDqP37jc87dXQlsfeRBu8sQpzuaNJ7tgubyADINhJK1gXZbFeoOLLaQX2pZd44dijT0VK/ZRLHxOSHttkqwCIEWjMzo4Pzl+wKBgQCoWfcqD1G4q7PpRKWYgUvhuCDLjl+VLuzC5wrMvP/MN1tO8GgUq2GTGdPuBh/4D+gRxAjEyZGZo1fh1O8fTOZge/fIblK/wlYB3dPTC4bS7OdJF/0wzDJL4LWBNk1fPWb83RuwuTrdA7EVQlc7RSROc2rppt7Jd/5SnpCL1uLxsQKBgQCIyKLKBzzAnlu+tYR4TfgyPFr6WWYH6pJaMmbqU3IQYmSYwrhCK2elgPZU1IGbQVXIeyJqm5QWrURC56K5GXs6WfMHawXbl4u2ytJCLYoSAF6HCYR5IdOZfbW5KCdxqf0NC6RZETrRwb1M3G09DNpYnVWaoCJSvQDWubbKCnQwZwKBgAfk2AsX1swi+PeqKV2iSm2WFauVzHORVLa64K3C+veXbOehrC+z7YpZPq+9h8g47rfOil632OTsXNWRB3lfQLorde3nBmhJc4D45tLRiovfYeTyhAPCv2UBb2FUq4IYvfiDYaYCuXjdkHMfvjlgP5iSHuQ/19h4kOtM1hhIhjGxAoGAB4V1SdRbCOgp3HZ1s+JJ8U5G6vz2Kci+jv7jdq60nwmUtaH3WM9iT1puURzURtuJZi+8oo70/bBUCtbg60YvFqNmzMJ6joLGcdbjXGXirmRPdkrdj1KNQXfshCMR8DXm/chgJILNhV9XJ0+EPLIbzdD6MWi7zfRNHv95nlFwChQ=","alipayPublicKey":"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjI4HGdIHAxUsaXYPLKJBy8hIhkcKPxpve/oYSoKgMx5t3v/FIS9ldW58Xr2ZsM2Tbbl04D6BgBy3MIfrIQIm5id2cJg8nEog5A5/bM/IQdIzFM1YgHHxaWxfv9m42haEgG7FLmf/ncM8S8DDtLaQveEIOpfo6cbXIwmAir6vE88gcddC6RCPIqCnVHcLVivOncW7nyJ0J4yMzYkoPnpwrMpxlNqKB5QvYopFElvPivbXOFGmZFWvPHFYIQXCD5IlgyImAQDA/HacVOf2E9kCoQ7FdzAIqT2AorcprDqHlg+JeGUUkHlDDpHkXWSu5i2mXJLNS5Cfuy+c2BgZlfimaQIDAQAB"}','','2025-06-05 13:41:42','2025-06-19 20:18:56');
INSERT INTO `tc_channel_api_param` (`id`,`partner_id`,`channel_code`,`param`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('25','100000003','TESTFUNDOUT001','{appId:1}','','2025-06-06 20:28:25','2025-06-19 20:18:56');
-- ----------------------------
-- Table structure for table tc_channel_maintain
-- ----------------------------
DROP TABLE IF EXISTS `tc_channel_maintain`;
CREATE TABLE `tc_channel_maintain` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `channel_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
  `maintain_time_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '维护时间类型',
  `time_range` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '时间区间',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_channel_code` (`channel_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道维护期';

-- ----------------------------
-- Records of tc_channel_maintain
-- ----------------------------
INSERT INTO `tc_channel_maintain` (`id`,`channel_code`,`maintain_time_type`,`time_range`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1','LOCALBANK001','FIXED','2024-12-03 00:00:00,2024-12-03 12:00:00','123','2024-12-03 11:19:11','2024-12-09 15:30:54');
-- ----------------------------
-- Table structure for table tc_channel_support_inst
-- ----------------------------
DROP TABLE IF EXISTS `tc_channel_support_inst`;
CREATE TABLE `tc_channel_support_inst` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `channel_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
  `target_inst_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '目标机构编码',
  `card_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '卡类型',
  `extra` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展条件',
  `per_amount_range` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '单笔限额',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_channel_inst` (`channel_code`,`target_inst_code`,`card_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道支持机构';

-- ----------------------------
-- Records of tc_channel_support_inst
-- ----------------------------
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('1','LOCALBANK001','ABC','UNLIMITED',NULL,'(10,2000)','2024-08-30 14:20:00','2025-05-16 10:49:57');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('2','LOCALBANK001','ICBC','UNLIMITED',NULL,NULL,'2024-08-30 14:20:00','2025-05-16 10:49:57');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('3','WXPAY001','WXPAY','UNLIMITED',NULL,NULL,'2024-09-18 11:30:42','2025-05-16 10:49:57');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('5','TESTBANK002','ABC','UNLIMITED',NULL,NULL,'2024-11-30 23:07:34','2025-05-16 10:49:57');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('7','TESTBANK002','ABC','debit',NULL,NULL,'2024-11-30 23:19:31','2024-12-04 14:11:49');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('9','TESTBANK002','ABC','credit',NULL,NULL,'2024-11-30 23:21:44','2024-12-04 14:11:49');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('11','TESTBANK002','TB2','UNLIMITED',NULL,NULL,'2024-12-07 21:21:25','2025-05-16 10:49:57');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('12','LOCALBANK001','TB1','UNLIMITED',NULL,NULL,'2024-12-07 21:21:35','2025-05-16 10:49:57');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('13','WXPAY001','WXPAY_JSAPI','UNLIMITED',NULL,NULL,'2025-04-18 15:32:00','2025-05-16 10:49:57');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('14','WXPAY001','WXPAY_NATIVE','UNLIMITED',NULL,NULL,'2025-04-18 15:32:12','2025-05-16 10:49:57');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('15','TESTFUNDOUT001','UNLIMITED','UNLIMITED',NULL,NULL,'2025-05-15 21:45:50','2025-05-16 10:49:57');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('16','ALIPAY001','ALIPAY','UNLIMITED',NULL,NULL,'2025-06-03 19:29:07','2025-06-03 19:29:07');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('17','ALIPAY001','ALIPAY_WEB','UNLIMITED',NULL,NULL,'2025-06-03 19:29:21','2025-06-03 19:29:21');
INSERT INTO `tc_channel_support_inst` (`id`,`channel_code`,`target_inst_code`,`card_type`,`extra`,`per_amount_range`,`gmt_create`,`gmt_modified`)  VALUES ('18','TESTBANK002','ALL_BANKCARD','UNLIMITED',NULL,NULL,'2025-06-03 20:00:27','2025-06-03 20:00:27');
-- ----------------------------
-- Table structure for table tc_fund_channel
-- ----------------------------
DROP TABLE IF EXISTS `tc_fund_channel`;
CREATE TABLE `tc_fund_channel` (
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '名称',
  `inst_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '所属机构代码',
  `request_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求模式',
  `pay_methods` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '支持的支付方式',
  `in_clearing_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流入待清算账户',
  `out_clearing_account` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '流出待清算账户',
  `enable` tinyint(1) NOT NULL COMMENT '状态，是否可用',
  `extra` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展信息',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='资金渠道';

-- ----------------------------
-- Records of tc_fund_channel
-- ----------------------------
INSERT INTO `tc_fund_channel` (`code`,`name`,`inst_code`,`request_type`,`pay_methods`,`in_clearing_account`,`out_clearing_account`,`enable`,`extra`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('ALIPAY001','支付宝直连渠道','ALIPAY','FUND_IN','ONLINE_BANK','40010010011560001','40010030011560001',1,NULL,'支付宝直连渠道','2024-09-18 11:30:42','2025-04-29 11:47:40');
INSERT INTO `tc_fund_channel` (`code`,`name`,`inst_code`,`request_type`,`pay_methods`,`in_clearing_account`,`out_clearing_account`,`enable`,`extra`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('LOCALBANK001','测试银行渠道1','TEST_BANK','FUND_IN','ONLINE_BANK,qpay','40010010011560001',NULL,1,'','网银支付，通过Spring Bean连接网关','2024-08-30 14:12:14','2025-04-11 14:46:32');
INSERT INTO `tc_fund_channel` (`code`,`name`,`inst_code`,`request_type`,`pay_methods`,`in_clearing_account`,`out_clearing_account`,`enable`,`extra`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('TEST003','测试银行渠道1','TEST_BANK','FUND_IN','qpay',NULL,NULL,1,NULL,'测试网银渠道','2024-11-26 18:32:55','2024-12-06 21:36:52');
INSERT INTO `tc_fund_channel` (`code`,`name`,`inst_code`,`request_type`,`pay_methods`,`in_clearing_account`,`out_clearing_account`,`enable`,`extra`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('TESTBANK002','测试银行渠道2','TEST_BANK','FUND_IN','ONLINE_BANK','40010010011560001','40010030011560001',1,'','网银支付，通过Feign连接网关','2024-09-01 11:43:04','2025-05-15 21:40:50');
INSERT INTO `tc_fund_channel` (`code`,`name`,`inst_code`,`request_type`,`pay_methods`,`in_clearing_account`,`out_clearing_account`,`enable`,`extra`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('TESTFUNDOUT001','测试出款渠道1','TEST_BANK','FUND_OUT','balance','40010010011560001','40010030011560001',1,NULL,'测试银行出款渠道','2025-05-15 21:44:31','2025-05-15 21:45:30');
INSERT INTO `tc_fund_channel` (`code`,`name`,`inst_code`,`request_type`,`pay_methods`,`in_clearing_account`,`out_clearing_account`,`enable`,`extra`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('WXPAY001','微信直连渠道','WXPAY','FUND_IN','ONLINE_BANK',NULL,NULL,0,NULL,'微信直连渠道','2024-09-18 11:30:42','2025-04-29 11:47:40');
-- ----------------------------
-- Table structure for table tc_institution
-- ----------------------------
DROP TABLE IF EXISTS `tc_institution`;
CREATE TABLE `tc_institution` (
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '名称',
  `inst_ability` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '机构能力',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='机构';

-- ----------------------------
-- Records of tc_institution
-- ----------------------------
INSERT INTO `tc_institution` (`code`,`name`,`inst_ability`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('ABC','农行','PAY','农业银行','2024-11-12 09:37:13','2024-12-06 21:35:23');
INSERT INTO `tc_institution` (`code`,`name`,`inst_ability`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('ALIPAY','支付宝','PAY,ACQUIRING','这是支付宝','2024-11-02 23:48:11','2024-12-05 21:16:49');
INSERT INTO `tc_institution` (`code`,`name`,`inst_ability`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('TB1','测试银行1','PAY','用于TESTBANK1支付','2024-12-07 21:20:57','2024-12-07 21:20:57');
INSERT INTO `tc_institution` (`code`,`name`,`inst_ability`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('TB2','测试银行2','PAY','用于TESTBANK2支付','2024-12-07 21:21:11','2024-12-07 21:21:11');
INSERT INTO `tc_institution` (`code`,`name`,`inst_ability`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('TEST_BANK','测试机构','ACQUIRING','测试通道专用','2024-12-06 21:37:11','2024-12-06 21:37:37');
INSERT INTO `tc_institution` (`code`,`name`,`inst_ability`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('WXPAY','微信','PAY,ACQUIRING','','2024-11-02 23:48:11','2024-12-05 21:16:49');
-- ----------------------------
-- Table structure for table tc_param_define
-- ----------------------------
DROP TABLE IF EXISTS `tc_param_define`;
CREATE TABLE `tc_param_define` (
  `channel_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
  `param_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数编码',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '参数名称',
  `type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '参数类型',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `sort` smallint DEFAULT '1' COMMENT '排序',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`channel_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='渠道参数定义';

-- ----------------------------
-- Records of tc_param_define
-- ----------------------------
-- ----------------------------
-- Table structure for table tc_unity_result_code
-- ----------------------------
DROP TABLE IF EXISTS `tc_unity_result_code`;
CREATE TABLE `tc_unity_result_code` (
  `code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `template` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '消息模板',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='统一结果码';

-- ----------------------------
-- Records of tc_unity_result_code
-- ----------------------------
INSERT INTO `tc_unity_result_code` (`code`,`template`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('F001','处理失败','','2024-08-07 18:03:40','2024-08-07 18:03:40');
INSERT INTO `tc_unity_result_code` (`code`,`template`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('P001','正在处理中','','2024-08-07 18:03:10','2024-08-07 18:03:10');
INSERT INTO `tc_unity_result_code` (`code`,`template`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('S001','处理成功','','2024-08-07 18:03:40','2024-08-07 18:03:40');
-- ----------------------------
-- Table structure for table tf_sequence
-- ----------------------------
DROP TABLE IF EXISTS `tf_sequence`;
CREATE TABLE `tf_sequence` (
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '序列名',
  `current_value` bigint NOT NULL DEFAULT '1' COMMENT '当前值',
  `increment` smallint NOT NULL DEFAULT '1' COMMENT '增长步长',
  `total` smallint NOT NULL DEFAULT '20' COMMENT '单次取值总量，更新总量需重启应用',
  `threshold` smallint NOT NULL DEFAULT '20' COMMENT '刷新阀值，更新阀值需重启应用',
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='序列';

-- ----------------------------
-- Records of tf_sequence
-- ----------------------------
INSERT INTO `tf_sequence` (`name`,`current_value`,`increment`,`total`,`threshold`)  VALUES ('seq_api_request_test','28681','1','20','20');
INSERT INTO `tf_sequence` (`name`,`current_value`,`increment`,`total`,`threshold`)  VALUES ('seq_api_request_wxpay','24961','1','20','20');
INSERT INTO `tf_sequence` (`name`,`current_value`,`increment`,`total`,`threshold`)  VALUES ('seq_biz_fund_in_id','47321','1','20','20');
INSERT INTO `tf_sequence` (`name`,`current_value`,`increment`,`total`,`threshold`)  VALUES ('seq_biz_fund_out_id','9461','1','20','20');
INSERT INTO `tf_sequence` (`name`,`current_value`,`increment`,`total`,`threshold`)  VALUES ('seq_biz_refund_id','26901','1','20','20');
-- ----------------------------
-- Table structure for table ti_inst_command_order
-- ----------------------------
DROP TABLE IF EXISTS `ti_inst_command_order`;
CREATE TABLE `ti_inst_command_order` (
  `command_id` bigint NOT NULL AUTO_INCREMENT COMMENT '指令ID',
  `inst_order_id` bigint NOT NULL COMMENT '机构订单号',
  `fund_channel_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
  `api_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口类型',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `unity_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '统一结果码',
  `unity_message` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '统一结果消息',
  `api_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '渠道API结果码',
  `api_sub_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '渠道API子结果码',
  `api_message` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '渠道API消息',
  `extension` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '扩展信息',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`command_id`),
  KEY `idx_inst_order_id` (`inst_order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1298 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='机构指令订单';

-- ----------------------------
-- Records of ti_inst_command_order
-- ----------------------------
-- ----------------------------
-- Table structure for table ti_inst_order
-- ----------------------------
DROP TABLE IF EXISTS `ti_inst_order`;
CREATE TABLE `ti_inst_order` (
  `inst_order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '机构订单号',
  `biz_order_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '业务订单号',
  `submit_time_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '提交时间类型',
  `inst_request_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '机构请求单号',
  `inst_response_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '机构响应单号',
  `fund_channel_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '渠道编码',
  `api_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口类型',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `api_param_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'API参数ID',
  `request_ext` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '请求扩展信息',
  `response_ext` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '响应扩展信息',
  `submit_time` timestamp NULL DEFAULT NULL COMMENT '提交时间',
  `book_submit_time` timestamp NOT NULL COMMENT '预约提交时间',
  `next_retry_time` timestamp NULL DEFAULT NULL COMMENT '下次补单时间',
  `retry_times` int NOT NULL DEFAULT '0' COMMENT '补单次数',
  `task_status` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '任务状态',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`inst_order_id`) USING BTREE,
  KEY `idx_inst_request_no` (`inst_request_no`) USING BTREE,
  KEY `idx_inst_response_no` (`inst_response_no`) USING BTREE,
  KEY `idx_biz_order_id` (`biz_order_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=428 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='机构订单';

-- ----------------------------
-- Records of ti_inst_order
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
