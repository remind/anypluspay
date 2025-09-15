-- ----------------------------
-- Chat2DB export data , export time: 2025-09-15 14:06:36
-- ----------------------------
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for table t_account_title
-- ----------------------------
DROP TABLE IF EXISTS `t_account_title`;
CREATE TABLE `t_account_title` (
  `code` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '科目代码',
  `name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '科目名称',
  `tier` decimal(2,0) DEFAULT NULL COMMENT '科目级别',
  `parent_code` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '父科目代码',
  `leaf` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否为叶子节点',
  `type` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '类型：1（资产类）；2（负债类）；3(所有者权益)；4（共同类）5(损益类)',
  `balance_direction` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '余额方向：1:借 2:贷 0:双向',
  `enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否有效',
  `scope` char(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '适用范围：1.内部科目;2,外部科目',
  `memo` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`code`),
  UNIQUE KEY `uk_title_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会计科目表';

-- ----------------------------
-- Records of t_account_title
-- ----------------------------
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1001','银行存款1',1,NULL,0,'1','D',1,'1','1234','2023-12-24 00:16:38','2025-01-04 21:37:11');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1002','渠道管理费',1,NULL,0,'1','D',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1003','调拨手续费',1,NULL,0,'1','D',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1004','银行短款',1,NULL,0,'1','D',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1005','其他应收款',1,NULL,0,'1','D',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1006','理财总投资',1,NULL,0,'1','D',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2001','客户资金',1,NULL,0,'2','C',1,'2',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2001001','客户资金-商户',2,'2001',0,'2','C',1,'2',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2001001001','客户资金-商户-基本户',3,'2001001',1,'2','C',1,'2',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2001002','客户资金-个人',2,'2001',0,'2','C',1,'2',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2001002001','客户资金-个人-基本户',3,'2001002',1,'2','C',1,'2',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2003','其他应付款',1,NULL,0,'2','C',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2004','其他业务收入',1,NULL,0,'2','C',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2008','公司自有资金',1,NULL,0,'2','C',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2020','其他应付款-结算过渡户',1,NULL,0,'2','C',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2020001','其他应付款-入款',2,'2020',0,'2','C',1,'1','其他应付款-入款','2025-03-19 08:59:53','2025-03-19 08:59:53');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2020001001','其他应付款-入款-公共',3,'2020001',1,'2','C',1,'1','其他应付款-入款-公共','2025-03-19 09:00:37','2025-03-19 09:00:37');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('4001','待清算',1,NULL,0,'4','T',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('4001001','待清算-入款待清算',2,'4001',0,'4','T',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('4001001001','待清算-入款待清算-测试渠道',3,'4001001',1,'4','T',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('4001002','待清算-出款待清算',2,'4001',0,'4','T',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('4001003','待清算-退款待清算',2,'4001',0,'4','T',1,'1',NULL,'2023-12-24 00:16:38','2023-12-24 00:16:38');
INSERT INTO `t_account_title` (`code`,`name`,`tier`,`parent_code`,`leaf`,`type`,`balance_direction`,`enable`,`scope`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('4001003001','待清算-退款待清算-测试渠道',3,'4001003',1,'4','T',1,'1',NULL,'2025-04-11 14:49:38','2025-04-11 14:49:38');
-- ----------------------------
-- Table structure for table t_account_transaction
-- ----------------------------
DROP TABLE IF EXISTS `t_account_transaction`;
CREATE TABLE `t_account_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `request_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求号',
  `accounting_date` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会计日',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_request_no` (`request_no`)
) ENGINE=InnoDB AUTO_INCREMENT=644 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='入账事务表';

-- ----------------------------
-- Records of t_account_transaction
-- ----------------------------
-- ----------------------------
-- Table structure for table t_accounting_entry
-- ----------------------------
DROP TABLE IF EXISTS `t_accounting_entry`;
CREATE TABLE `t_accounting_entry` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `voucher_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '凭证号',
  `request_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求号',
  `suite_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '套号',
  `title_code` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '科目编码',
  `account_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户号',
  `amount` decimal(19,4) NOT NULL COMMENT '发生金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '货币类型',
  `cr_dr` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '借贷标志',
  `accounting_date` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会计日',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_voucher_no` (`voucher_no`),
  KEY `idx_request_no` (`request_no`),
  KEY `idx_accounting_date` (`accounting_date`),
  KEY `idx_gmt_create` (`gmt_create`)
) ENGINE=InnoDB AUTO_INCREMENT=1095 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='会计分录';

-- ----------------------------
-- Records of t_accounting_entry
-- ----------------------------
-- ----------------------------
-- Table structure for table t_buffered_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_buffered_detail`;
CREATE TABLE `t_buffered_detail` (
  `voucher_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '凭证号',
  `request_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求号',
  `account_no` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户号',
  `amount` decimal(19,4) NOT NULL COMMENT '发生金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '货币类型',
  `cr_dr` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '借贷标志',
  `accounting_date` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会计日',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `execute_count` int NOT NULL DEFAULT '0' COMMENT '执行次数',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`voucher_no`),
  KEY `idx_request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='缓冲入账明细';

-- ----------------------------
-- Records of t_buffered_detail
-- ----------------------------
-- ----------------------------
-- Table structure for table t_buffered_rule
-- ----------------------------
DROP TABLE IF EXISTS `t_buffered_rule`;
CREATE TABLE `t_buffered_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_no` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户号',
  `cr_dr` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '借贷标志，为空则均支持',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '状态',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account` (`account_no`,`cr_dr`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='缓冲规则';

-- ----------------------------
-- Records of t_buffered_rule
-- ----------------------------
INSERT INTO `t_buffered_rule` (`id`,`account_no`,`cr_dr`,`status`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1','40010010011560002',NULL,'1','测试规则','2023-12-25 20:19:56','2023-12-25 20:19:56');
-- ----------------------------
-- Table structure for table t_dbajob_conf
-- ----------------------------
DROP TABLE IF EXISTS `t_dbajob_conf`;
CREATE TABLE `t_dbajob_conf` (
  `job_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务名字',
  `job_status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '任务进度',
  `comments` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据库任务配置';

-- ----------------------------
-- Records of t_dbajob_conf
-- ----------------------------
INSERT INTO `t_dbajob_conf` (`job_name`,`job_status`,`comments`)  VALUES ('proc_title_daily_stat','20250530','lastjoblogid: 459');
-- ----------------------------
-- Table structure for table t_dbajob_log
-- ----------------------------
DROP TABLE IF EXISTS `t_dbajob_log`;
CREATE TABLE `t_dbajob_log` (
  `jobid` bigint NOT NULL AUTO_INCREMENT,
  `job_name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `job_status` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `comments` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `parameter` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`jobid`)
) ENGINE=InnoDB AUTO_INCREMENT=549 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='数据库任务执行日志';

-- ----------------------------
-- Records of t_dbajob_log
-- ----------------------------
-- ----------------------------
-- Table structure for table t_inner_account
-- ----------------------------
DROP TABLE IF EXISTS `t_inner_account`;
CREATE TABLE `t_inner_account` (
  `account_no` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户号',
  `title_code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '科目号',
  `account_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户名称',
  `current_balance_direction` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '当前余额方向 1:借，2:贷',
  `balance_direction` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户余额方向 1:借，2:贷，0:双向',
  `currency_code` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '货币类型',
  `balance` decimal(19,4) NOT NULL DEFAULT '0.0000' COMMENT '余额',
  `memo` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`account_no`),
  UNIQUE KEY `uk_account_no` (`account_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内部户';

-- ----------------------------
-- Records of t_inner_account
-- ----------------------------
INSERT INTO `t_inner_account` (`account_no`,`title_code`,`account_name`,`current_balance_direction`,`balance_direction`,`currency_code`,`balance`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('20200010011560001','2020001001','其他应付款-入款-过渡户','C','C','CNY',216.6000,'其他应付款-入款-过渡户','2025-03-19 09:43:34','2025-07-03 19:42:24');
INSERT INTO `t_inner_account` (`account_no`,`title_code`,`account_name`,`current_balance_direction`,`balance_direction`,`currency_code`,`balance`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('40010010011560001','4001001001','待清算-入款待清算-测试渠道','D','T','CNY',1001115.8000,'123','2023-12-24 00:23:57','2025-07-03 19:42:24');
INSERT INTO `t_inner_account` (`account_no`,`title_code`,`account_name`,`current_balance_direction`,`balance_direction`,`currency_code`,`balance`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('40010010011560002','4001001001','测试账户','D','T','CNY',999992.0000,NULL,'2023-12-25 20:19:08','2025-02-11 10:24:02');
INSERT INTO `t_inner_account` (`account_no`,`title_code`,`account_name`,`current_balance_direction`,`balance_direction`,`currency_code`,`balance`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('40010010011560003','4001001001','测试账户3','D','T','CNY',999888.0000,NULL,'2025-01-06 23:14:37','2025-05-13 16:38:57');
INSERT INTO `t_inner_account` (`account_no`,`title_code`,`account_name`,`current_balance_direction`,`balance_direction`,`currency_code`,`balance`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('40010010011560004','4001001001','测试账号4','D','T','CNY',999978.0000,'账号备注','2025-01-10 11:46:11','2025-05-09 16:30:29');
INSERT INTO `t_inner_account` (`account_no`,`title_code`,`account_name`,`current_balance_direction`,`balance_direction`,`currency_code`,`balance`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('40010010011560005','4001001001','测试账号4','D','T','CNY',0.0000,'账号备注','2025-01-10 13:43:34','2025-01-10 13:43:34');
INSERT INTO `t_inner_account` (`account_no`,`title_code`,`account_name`,`current_balance_direction`,`balance_direction`,`currency_code`,`balance`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('40010010011560006','4001001001','测试账户6','D','T','CNY',0.0000,'123','2025-01-14 23:08:07','2025-01-14 23:08:07');
INSERT INTO `t_inner_account` (`account_no`,`title_code`,`account_name`,`current_balance_direction`,`balance_direction`,`currency_code`,`balance`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('40010030011560001','4001003001','待清算-退款待清算-测试渠道','D','T','CNY',0.0000,NULL,'2025-04-11 14:50:51','2025-04-11 14:50:51');
-- ----------------------------
-- Table structure for table t_inner_account_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_inner_account_detail`;
CREATE TABLE `t_inner_account_detail` (
  `voucher_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '凭证号',
  `request_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求号',
  `account_no` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户号',
  `before_balance` decimal(19,4) NOT NULL COMMENT '入账前余额',
  `after_balance` decimal(19,4) NOT NULL COMMENT '入账后余额',
  `amount` decimal(19,4) NOT NULL COMMENT '发生金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '货币类型',
  `cr_dr` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '借贷标志',
  `io_direction` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '加减方向',
  `accounting_date` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会计日',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`voucher_no`),
  KEY `idx_request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='内部户入账明细';

-- ----------------------------
-- Records of t_inner_account_detail
-- ----------------------------
-- ----------------------------
-- Table structure for table t_outer_account
-- ----------------------------
DROP TABLE IF EXISTS `t_outer_account`;
CREATE TABLE `t_outer_account` (
  `account_no` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户号',
  `title_code` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '科目号',
  `account_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户名称',
  `member_id` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '会员号',
  `deny_status` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '禁止状态',
  `account_attribute` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户属性 1:对私，2:对公',
  `account_type` varchar(16) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户类型',
  `current_balance_direction` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '当前余额方向 1:借，2:贷',
  `balance_direction` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户余额方向 1:借，2:贷，0:双向',
  `currency_code` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '货币类型',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`account_no`),
  UNIQUE KEY `uk_account_no` (`account_no`) USING BTREE,
  UNIQUE KEY `uk_member_id_account_type` (`member_id`,`account_type`),
  KEY `idx_member_id` (`member_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='外部户';

-- ----------------------------
-- Records of t_outer_account
-- ----------------------------
INSERT INTO `t_outer_account` (`account_no`,`title_code`,`account_name`,`member_id`,`deny_status`,`account_attribute`,`account_type`,`current_balance_direction`,`balance_direction`,`currency_code`,`gmt_create`,`gmt_modified`)  VALUES ('200100200110000000215600001','2001002001','基本户','100000002','0','1','101','C','C','CNY','2023-12-22 23:11:16','2025-07-03 19:42:24');
INSERT INTO `t_outer_account` (`account_no`,`title_code`,`account_name`,`member_id`,`deny_status`,`account_attribute`,`account_type`,`current_balance_direction`,`balance_direction`,`currency_code`,`gmt_create`,`gmt_modified`)  VALUES ('200100200110000000315600001','2001002001','基本户','100000003','0','1','101','C','C','CNY','2023-12-22 23:11:16','2025-06-20 09:58:59');
INSERT INTO `t_outer_account` (`account_no`,`title_code`,`account_name`,`member_id`,`deny_status`,`account_attribute`,`account_type`,`current_balance_direction`,`balance_direction`,`currency_code`,`gmt_create`,`gmt_modified`)  VALUES ('200100200110000000415600001','2001002001','基本户','100000004','0','1','101','C','C','CNY','2023-12-22 23:11:16','2025-01-10 11:30:50');
INSERT INTO `t_outer_account` (`account_no`,`title_code`,`account_name`,`member_id`,`deny_status`,`account_attribute`,`account_type`,`current_balance_direction`,`balance_direction`,`currency_code`,`gmt_create`,`gmt_modified`)  VALUES ('200100200110000000515600001','2001002001','基本户','100000005','0','1','101','C','C','CNY','2025-01-10 11:19:57','2025-01-10 11:30:50');
-- ----------------------------
-- Table structure for table t_outer_account_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_outer_account_detail`;
CREATE TABLE `t_outer_account_detail` (
  `voucher_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '凭证号',
  `request_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求号',
  `account_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户号',
  `before_balance` decimal(19,4) NOT NULL COMMENT '入账前余额',
  `after_balance` decimal(19,4) NOT NULL COMMENT '入账后余额',
  `amount` decimal(19,4) NOT NULL COMMENT '发生金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '货币类型',
  `operation_type` varchar(2) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '操作类型，常规、冻结、解冻',
  `cr_dr` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '借贷标志',
  `io_direction` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '加减方向',
  `accounting_date` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '会计日',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`voucher_no`),
  KEY `idx_request_no` (`request_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='外部户入账明细';

-- ----------------------------
-- Records of t_outer_account_detail
-- ----------------------------
-- ----------------------------
-- Table structure for table t_outer_account_type
-- ----------------------------
DROP TABLE IF EXISTS `t_outer_account_type`;
CREATE TABLE `t_outer_account_type` (
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '编码',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `title_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '科目编码',
  `account_attribute` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账户分类',
  `currency_code` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种代码',
  `fund_types` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资金类型编码，多个用逗号分隔',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`code`),
  UNIQUE KEY `uk_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='外部户类型';

-- ----------------------------
-- Records of t_outer_account_type
-- ----------------------------
INSERT INTO `t_outer_account_type` (`code`,`name`,`title_code`,`account_attribute`,`currency_code`,`fund_types`,`gmt_create`,`gmt_modified`)  VALUES ('101','基本户','2001002001','1','CNY','NORMAL','2023-12-22 23:11:16','2023-12-22 23:11:16');
-- ----------------------------
-- Table structure for table t_outer_sub_account
-- ----------------------------
DROP TABLE IF EXISTS `t_outer_sub_account`;
CREATE TABLE `t_outer_sub_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_no` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户号',
  `fund_type` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '资金类型',
  `balance` decimal(19,4) DEFAULT NULL COMMENT '余额',
  `available_balance` decimal(19,4) DEFAULT NULL COMMENT '可用余额',
  `currency_code` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '币种代码',
  `memo` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account_fund` (`account_no`,`fund_type`),
  KEY `idx_member_id` (`available_balance`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='外部户子账户';

-- ----------------------------
-- Records of t_outer_sub_account
-- ----------------------------
INSERT INTO `t_outer_sub_account` (`id`,`account_no`,`fund_type`,`balance`,`available_balance`,`currency_code`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1','200100200110000000215600001','NORMAL',71.4000,68.4000,'CNY',NULL,'2023-12-22 23:40:48','2025-07-03 19:42:24');
INSERT INTO `t_outer_sub_account` (`id`,`account_no`,`fund_type`,`balance`,`available_balance`,`currency_code`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2','200100200110000000315600001','NORMAL',829.8000,779.8000,'CNY',NULL,'2023-12-23 16:29:56','2025-06-20 09:58:59');
INSERT INTO `t_outer_sub_account` (`id`,`account_no`,`fund_type`,`balance`,`available_balance`,`currency_code`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('3','200100200110000000415600001','NORMAL',68.0000,68.0000,'CNY',NULL,'2023-12-23 16:53:32','2025-01-10 11:30:50');
INSERT INTO `t_outer_sub_account` (`id`,`account_no`,`fund_type`,`balance`,`available_balance`,`currency_code`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('4','200100200110000000515600001','NORMAL',1008.0000,1008.0000,'CNY',NULL,'2025-01-10 11:19:57','2025-01-10 11:30:50');
-- ----------------------------
-- Table structure for table t_outer_sub_account_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_outer_sub_account_detail`;
CREATE TABLE `t_outer_sub_account_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `voucher_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '凭证号',
  `request_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求号',
  `account_no` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '账户号',
  `fund_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '资金类型',
  `before_balance` decimal(19,4) NOT NULL COMMENT '入账前余额',
  `after_balance` decimal(19,4) NOT NULL COMMENT '入账后余额',
  `amount` decimal(19,4) NOT NULL COMMENT '发生金额',
  `currency_code` varchar(3) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '货币类型',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_account_voucher` (`voucher_no`,`account_no`,`fund_type`),
  KEY `idx_request_no` (`request_no`),
  KEY `idx_voucher_no` (`voucher_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=533 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='外部户子户入账明细';

-- ----------------------------
-- Records of t_outer_sub_account_detail
-- ----------------------------
-- ----------------------------
-- Table structure for table t_title_daily
-- ----------------------------
DROP TABLE IF EXISTS `t_title_daily`;
CREATE TABLE `t_title_daily` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `account_date` char(8) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '日期',
  `title_code` varchar(16) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '科目编号',
  `balance_direction` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '余额方向',
  `debit_amount` decimal(19,4) DEFAULT NULL COMMENT '借记金额',
  `credit_amount` decimal(19,4) DEFAULT NULL COMMENT '贷记金额',
  `debit_count` bigint DEFAULT NULL COMMENT '借记次数',
  `credit_count` bigint DEFAULT NULL COMMENT '贷记次数',
  `debit_balance` decimal(19,4) DEFAULT NULL COMMENT '借记余额',
  `credit_balance` decimal(19,4) DEFAULT NULL COMMENT '贷记余额',
  `currency_code` varchar(3) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '币种代码',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `memo` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注字段',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_title_daily_date` (`account_date`,`title_code`),
  KEY `idx_td_account_date` (`account_date`)
) ENGINE=InnoDB AUTO_INCREMENT=124633 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='科目日汇总';

-- ----------------------------
-- Records of t_title_daily
-- ----------------------------
-- ----------------------------
-- Table structure for table tf_scheduler_task
-- ----------------------------
DROP TABLE IF EXISTS `tf_scheduler_task`;
CREATE TABLE `tf_scheduler_task` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `biz_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务ID',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务类型',
  `param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '任务参数',
  `status` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务状态',
  `execute_count` tinyint NOT NULL COMMENT '执行次数',
  `next_execute_time` bigint NOT NULL COMMENT '下次执行时间',
  `start_execute_time` bigint DEFAULT NULL COMMENT '开始执行时间',
  `env` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '环境',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_biz_id_type` (`biz_id`,`type`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE,
  KEY `idx_next_execute_time` (`next_execute_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='调度任务';

-- ----------------------------
-- Records of tf_scheduler_task
-- ----------------------------
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
SET FOREIGN_KEY_CHECKS=1;
-- ----------------------------
-- Procedure structure for procedure proc_title_daily_stat
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_title_daily_stat`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `proc_title_daily_stat`(
  IN `account_date_in` varchar(10),
  OUT `result_code` varchar(20),
  OUT `error_level` varchar(10),
  OUT `error_message` varchar(100)
)
BEGIN DECLARE v_lastdate VARCHAR(8);
DECLARE i_account_date VARCHAR(8);
DECLARE i_pre_date VARCHAR(8);
DECLARE v_jobid BIGINT DEFAULT 0;
DECLARE error_code BIGINT DEFAULT 0;
DECLARE i_title_code VARCHAR(20);
DECLARE i_balance_direction CHAR(1);
DECLARE i_currency_code VARCHAR(10);
DECLARE i_debit_count BIGINT(15);
DECLARE i_debit_amount NUMERIC(15, 4);
DECLARE i_credit_amount NUMERIC(15, 4);
DECLARE i_credit_count BIGINT(15);
DECLARE notfound int(1);
DECLARE too_early_exception CONDITION FOR SQLSTATE '45001';
DECLARE multirun_exception CONDITION FOR SQLSTATE '45002';
DECLARE date_format_exception CONDITION FOR SQLSTATE '45003';
DECLARE cs_title_daily_group CURSOR for
select e.title_code,e.currency_code,t.balance_direction,
  sum(case when e.cr_dr = 'C' then 1 else 0 end) as crCount,
  sum(case when e.cr_dr = 'C' then e.amount else 0 end) as crAmount,
  sum(case when e.cr_dr = 'D' then 1 else 0 end) as drCount,
  sum(case when e.cr_dr = 'D' then e.amount else 0 end) as drAmount
from ap_account.t_accounting_entry e 
  inner join  t_account_title t on e.title_code = t.code
where e.accounting_date = account_date_in
group by e.title_code,e.currency_code,t.balance_direction;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET notfound = 1;
DECLARE EXIT HANDLER FOR too_early_exception BEGIN
  SET
    result_code = 'too_early_exception';
  SET
    error_level = 'F';
  update t_dbajob_log
  set end_date = NOW(), job_status = 'failed', comments = result_code
  where jobid = v_jobid;
  commit;
END;
DECLARE EXIT HANDLER FOR multirun_exception BEGIN
  SET
    result_code = 'multirun_exception';
  SET
    error_level = 'F';
  update
    t_dbajob_log
  set
    end_date = NOW(),
    job_status = 'failed',
    comments = result_code
  where
    jobid = v_jobid;
  commit;
END;
DECLARE EXIT HANDLER FOR date_format_exception BEGIN ROLLBACK;
  SET
    result_code = 'date_format_exception';
  SET
    error_level = 'F';
  SET
    error_message = SUBSTRB(SQLERRM, 1, 1024);
  update
    t_dbajob_log
  set
    end_date = NOW(),
    job_status = 'failed',
    comments = result_code
  where
    jobid = v_jobid;
  commit;
END;
insert into
  t_dbajob_log (
    job_name,
    start_date,
    end_date,
    job_status,
    comments,
    parameter
  )
  values
    (
      'proc_title_daily_stat',
      NOW(),
      null,
      'running',
      null,
      concat('account_date:', account_date_in)
    );
select
    LAST_INSERT_ID() into v_jobid
  from
    t_dbajob_log
  limit 1;
  commit;
BEGIN
  set
    i_account_date = DATE_FORMAT(STR_TO_DATE(account_date_in, '%Y%m%d'), '%Y%m%d');
  set
    i_pre_date = DATE_FORMAT(
      STR_TO_DATE(account_date_in, '%Y%m%d') - INTERVAL 1 DAY,
      '%Y%m%d'
    );
END;
select
    job_status into v_lastdate
  from
    t_dbajob_conf
  where
    job_name = 'proc_title_daily_stat';
if v_lastdate < i_pre_date then
  call proc_title_daily_stat(i_pre_date, result_code, error_level, error_message);
  ELSEIF v_lastdate >= i_account_date then SIGNAL multirun_exception;
 END if;
OPEN cs_title_daily_group;
  SET
    account_date_in = i_account_date;
  dept_cursor: 
    LOOP FETCH cs_title_daily_group INTO 
    i_title_code,
    i_currency_code,
    i_balance_direction,
    i_debit_amount,
    i_credit_amount,
    i_debit_count,
    i_credit_count;
    if notfound = 1 THEN leave dept_cursor;
     END if;
    INSERT INTO
      t_title_daily (
        account_date,
        title_code,
        balance_direction,
        debit_amount,
        credit_amount,
        debit_count,
        credit_count,
        debit_balance,
        credit_balance,
        currency_code
      )
    VALUES
      (
        i_account_date,
        i_title_code,
        i_balance_direction,
        i_debit_amount,
        i_credit_amount,
        i_debit_count,
        i_credit_count,
        i_debit_amount,
        i_credit_amount,
        i_currency_code
      );
   END LOOP dept_cursor;
CLOSE cs_title_daily_group;
INSERT INTO
  t_title_daily (
    account_date,
    title_code,
    balance_direction,
    debit_amount,
    credit_amount,
    debit_count,
    credit_count,
    debit_balance,
    credit_balance,
    currency_code
  )
  select
    account_date,
    title_code,
    balance_direction,
    debitAmount,
    creditAmount,
    debitCount,
    creditCount,
    debitBalance,
    creditBalance,
    currency_code
  from
    (
      SELECT
        d.account_date,
        t.parent_code AS title_code,
        d.balance_direction,
        d.currency_code,
        SUM(d.debit_balance) AS debitBalance,
        SUM(d.credit_balance) AS creditBalance,
        SUM(d.debit_count) AS debitCount,
        SUM(d.credit_count) AS creditCount,
        SUM(d.debit_amount) as debitAmount,
        SUM(d.credit_amount) as creditAmount        
      FROM
        t_title_daily d
        inner join t_account_title t on d.title_code = t.code
      WHERE
        account_date = i_account_date
        AND t.tier = 3 
      GROUP BY
        d.account_date,
        t.parent_code,
        d.balance_direction,
        d.currency_code
    ) c on DUPLICATE key
  UPDATE
    debit_balance = t_title_daily.debit_balance + c.debitBalance,
    credit_balance = t_title_daily.credit_balance + c.creditBalance,
    debit_count = t_title_daily.debit_count + c.debitCount,
    credit_count = t_title_daily.credit_count + c.creditCount,
    gmt_modified = now(),
    debit_amount = t_title_daily.debit_amount + c.debitAmount,
    credit_amount = t_title_daily.credit_amount + c.creditAmount;
INSERT INTO
  t_title_daily (
    account_date,
    title_code,
    balance_direction,
    debit_amount,
    credit_amount,
    debit_count,
    credit_count,
    debit_balance,
    credit_balance,
    currency_code
  )
  select
    account_date,
    title_code,
    balance_direction,
    debitAmount,
    creditAmount,
    debitCount,
    creditCount,
    debitBalance,
    creditBalance,
    currency_code
  from
    (
      SELECT
        d.account_date,
        t.parent_code AS title_code,
        d.balance_direction,
        d.currency_code,
        SUM(d.debit_balance) AS debitBalance,
        SUM(d.credit_balance) AS creditBalance,
        SUM(d.debit_count) AS debitCount,
        SUM(d.credit_count) AS creditCount,
        SUM(d.debit_amount) as debitAmount,
        SUM(d.credit_amount) as creditAmount
      FROM
        t_title_daily d
        inner join t_account_title t on d.title_code = t.code
      WHERE
        account_date = i_account_date
        AND t.tier = 2
      GROUP BY
        d.account_date,
        t.parent_code,
        d.balance_direction,
        d.currency_code
    ) c on DUPLICATE key
  UPDATE
    debit_balance = t_title_daily.debit_balance + c.debitBalance,
    credit_balance = t_title_daily.credit_balance + c.creditBalance,
    debit_count = t_title_daily.debit_count + c.debitCount,
    credit_count = t_title_daily.credit_count + c.creditCount,
    gmt_modified = now(),
    debit_amount = t_title_daily.debit_amount + c.debitAmount,
    credit_amount = t_title_daily.credit_amount + c.creditAmount;

/*
  修改当前期末余额
  1、前一天有发生，当天有发生，前一天期末余额+当前发生额
  2、前一天没发生，当天有发生，取前一在的期末余额
*/
INSERT INTO
  t_title_daily (
    account_date,
    title_code,
    balance_direction,
    debit_count,
    credit_count,
    debit_balance,
    credit_balance,
    currency_code
  )
    select
    accountDate,
    titleCode,
    balanceDirection,
    debitCount,
    creditCount,
    debitBalance,
    creditBalance,
    currency_code
  from
    (
      SELECT
        i_account_date as accountDate,
        f.title_code as titleCode,
        f.title_code,
        f.balance_direction as balanceDirection,
        f.debit_balance as debitBalance,
        f.credit_balance as creditBalance,
        0 as debitCount,
        0 as creditCount,
        f.currency_code
      FROM
        t_title_daily f
      WHERE
        account_date = i_pre_date
        AND (
          debit_balance != 0
          OR credit_balance != 0
        )
    ) c on DUPLICATE key
  UPDATE
    debit_balance = t_title_daily.debit_balance + c.debitBalance,
    credit_balance = t_title_daily.credit_balance + c.creditBalance,
    gmt_modified = now();
UPDATE
  t_title_daily
  SET
    debit_balance = debit_balance - credit_balance,
    credit_balance = 0,
    gmt_modified = NOW()
  WHERE
    account_date = i_account_date
    AND balance_direction = 'D';
UPDATE
  t_title_daily
  SET
    credit_balance = credit_balance - debit_balance,
    debit_balance = 0,
    gmt_modified = NOW()
  WHERE
    account_date = i_account_date
    AND balance_direction = 'C';
UPDATE
  t_title_daily
  SET
    credit_balance = credit_balance - debit_balance,
    debit_balance = 0,
    gmt_modified = NOW()
  WHERE
    account_date = i_account_date
    AND (credit_balance - debit_balance >= 0)
    AND balance_direction = 'T';
UPDATE
  t_title_daily
  SET
    debit_balance = debit_balance - credit_balance,
    credit_balance = 0,
    gmt_modified = NOW()
  WHERE
    account_date = i_account_date
    AND (credit_balance - debit_balance < 0)
    AND balance_direction = 'T';
COMMIT;
SET
  result_code = 'SUCCESS';
update
  t_dbajob_log
set
  end_date = NOW(),
  job_status = 'done',
  comments = result_code
where
  jobid = v_jobid;
update
  t_dbajob_conf
set
  job_status = i_account_date,
  comments = concat('lastjoblogid: ', cast(v_jobid as char))
where
  job_name = 'proc_title_daily_stat';
commit;
END;;
delimiter ;


