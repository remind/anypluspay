-- ----------------------------
-- Chat2DB export data , export time: 2025-09-15 14:06:44
-- ----------------------------
SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for table t_query_define
-- ----------------------------
DROP TABLE IF EXISTS `t_query_define`;
CREATE TABLE `t_query_define` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint NOT NULL COMMENT '分组ID',
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `data_source` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '数据源',
  `query_sql` varchar(1024) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '查询SQL',
  `param_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '查询参数,多个用逗号分隔',
  `sort` tinyint NOT NULL DEFAULT '1' COMMENT '排序',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='查询定义';

-- ----------------------------
-- Records of t_query_define
-- ----------------------------
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('2','3','资金流入','channel','select 
b.order_id as channelOrderId, 
request_id as payFluxProcessId,
member_id as 会员ID,
case when status = ''SU'' then ''成功''
when status = ''FA'' then ''失败''
when status = ''PR'' then ''处理中''
else ''未知'' end as 状态,
f.pay_model as 支付模式,
pay_inst as 支付机构,
amount as 金额,
b.inst_order_id,
b.extension as 扩展信息,
b.inst_ext as 机构扩展信息,
b.gmt_create as 创建时间,
b.gmt_modified as 修改时间
from tb_biz_order b 
inner join tb_fund_in_order f on b.order_id = f.order_id','channelOrderId,payFluxProcessId',1,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('3','3','机构订单','channel','select 
inst_order_id,
biz_order_id as channelOrderId,
case when submit_time_type = ''R'' then ''实时''
when submit_time_type = ''D'' then ''延时''
else ''未知'' end as 提交类型,
inst_request_no,
inst_response_no,
fund_channel_code as 资金渠道编码,
api_type as 接口类型,
case when status = ''IN'' then ''初始化''
when status = ''SU'' then ''成功''
when status = ''FA'' then ''失败''
when status = ''PR'' then ''处理中''
else ''未知'' end as 状态,
request_ext as 请求扩展信息,
response_ext as 响应扩展信息,
submit_time as 提交时间,
book_submit_time as 预约提交时间,
next_retry_time as 下次重试时间,
retry_times as 重试次数,
case when task_status = ''L'' then ''锁定''
when task_status = ''U'' then ''未锁定''
else ''未知'' end as 任务状态,
gmt_create as 创建时间,
gmt_modified as 修改时间
from ti_inst_order','channelOrderId,inst_order_id,inst_request_no',3,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('4','3','机构指令单','channel','select command_id,
inst_order_id,
fund_channel_code as 资金渠道编码,
api_type as 接口类型,
case when status = ''IN'' then ''初始化''
when status = ''SU'' then ''成功''
when status = ''FA'' then ''失败''
when status = ''PR'' then ''处理中''
else ''未知'' end as 状态,
unity_code as 统一结果码,
unity_message as 统一消息,
api_code as 机构结果码,
api_sub_code as 机构子结果码,
api_message as 机构消息,
extension as 扩展信息,
gmt_create as 创建时间,
gmt_modified as 修改时间
from ti_inst_command_order','command_id,inst_order_id',5,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('6','1','支付单','payment','select 
trade_id as tradeId,
order_id as payId,
member_id as 会员ID,
amount as 金额,
case when status = ''INIT'' then ''待支付''
when status = ''PAYING'' then ''支付中''
when status = ''FAIL'' then ''失败''
else ''成功'' end as 支付状态,
result_code as 结果码,
result_msg as 结果信息,
gmt_create as 创建时间,
gmt_modified as 修改时间
 from tp_pay_order','tradeId,payId',6,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('7','1','资金明细','payment','select 
trade_id as tradeId,
order_id as payId,
detail_id as payFundDetailId,
case when belong_to = ''payee'' then ''收款方''
when belong_to = ''payer'' then ''付款方''
else '''' end as 归属方,
member_id as 会员ID,
amount as 金额,
case when fund_action = ''DECREASE'' then ''减少''
when fund_action = ''INCREASE'' then ''增加''
else '''' end as 资金动作,
case when asset_type = ''BANKCARD'' then ''银行卡''
when asset_type = ''BALANCE'' then ''余额''
else '''' end as 资产类型,
asset_info as  资产信息
 from tp_fund_detail','tradeId,payId',8,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('8','1','交换单','payment','select 
trade_id as tradeId,
order_id as payId,
flux_order_id as payFluxOrderId,
case when pay_type = ''PAY'' then ''支付''
when pay_type = ''REFUND'' then ''退款''
else ''未知'' end as 支付类型,
case when status = ''INIT'' then ''初始化''
when status = ''PROCESS'' then ''处理中''
when status = ''FAIL'' then ''失败''
when status = ''CANCEL'' then ''撤消''
when status = ''SUCCESS'' then ''成功''
else ''未知'' end as 状态,
result_code as 结果码,
result_msg as 结果信息,
gmt_create as 创建时间,
gmt_modified as 修改时间
 from tf_flux_order','tradeId,payId,payFluxOrderId ',9,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('9','1','交换指令','payment','select 
trade_id as tradeId,
order_id as payId,
flux_order_id as payFluxOrderId,
fund_detail_id as payFundDetailId,
flux_process_id as payFluxProcessId,
case when fund_action = ''DECREASE'' then ''减少''
when fund_action = ''INCREASE'' then ''增加''
else '''' end as 资金动作,
case when type = ''C'' then ''清算指令''
when type = ''N'' then ''常规指令''
else '''' end as 指令类型,
case when direction = ''A'' then ''申请''
when direction = ''R'' then ''撤消''
else '''' end as 指令方向,
case when status = ''INIT'' then ''初始化''
when status = ''PROCESS'' then ''处理中''
when status = ''FAIL'' then ''失败''
when status = ''CANCEL'' then ''撤消''
when status = ''SUCCESS'' then ''成功''
else ''未知'' end as 状态,
amount as 金额,
case when asset_type = ''BANKCARD'' then ''银行卡''
when asset_type = ''BALANCE'' then ''余额''
else '''' end as 资产类型,
asset_info as  资产信息,
result_code as 结果码,
result_msg as 结果信息,
gmt_create as 创建时间,
gmt_modified as 修改时间
 from tf_flux_process
 order by gmt_modified asc','tradeId,payId,payFluxOrderId,payFluxProcessId',10,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('10','2','入账事务','account','select 
request_no as payFluxProcessId,
accounting_date as 会计日,
gmt_create as 创建时间
 from t_account_transaction
 order by gmt_create asc



','payFluxProcessId',10,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('11','2','外部户明细','account','select 
request_no as payFluxProcessId,
voucher_no as accountVoucherNo,
account_no as 账户号,
before_balance as 入账前余额, 
after_balance as 入账后余额, 
amount as 入账金额, 
case when operation_type = ''NM'' then ''常规''
when operation_type = ''FR'' then ''冻结''
when operation_type = ''UF'' then ''解冻''
else '''' end as 操作类型,
case when cr_dr = ''D'' then ''借方''
when cr_dr = ''C'' then ''贷方''
else '''' end as 借贷,
case when io_direction = ''I'' then ''加''
when io_direction = ''O'' then ''减''
else '''' end as 余额方向,
memo as 备注,
gmt_create as 创建时间
 from t_outer_account_detail
 order by gmt_create asc','payFluxProcessId,accountVoucherNo',11,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('12','2','外部户子户明细','account','select 
request_no as payFluxProcessId,
voucher_no as accountVoucherNo,
account_no as 账户号,
case when fund_type = ''NORMAL'' then ''常规''
else '''' end as 资金类型,
before_balance as 入账前余额, 
after_balance as 入账后余额, 
amount as 入账金额,
gmt_create as 创建时间
 from t_outer_sub_account_detail','payFluxProcessId,accountVoucherNo',12,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('13','2','内部户明细','account','select 
request_no as payFluxProcessId,
voucher_no as accountVoucherNo,
account_no as 账户号,
before_balance as 入账前余额, 
after_balance as 入账后余额, 
amount as 入账金额, 
case when cr_dr = ''D'' then ''借方''
when cr_dr = ''C'' then ''贷方''
else '''' end as 借贷,
case when io_direction = ''I'' then ''加''
when io_direction = ''O'' then ''减''
else '''' end as 余额方向,
memo as 备注,
gmt_create as 创建时间 from t_inner_account_detail
 order by gmt_create asc','payFluxProcessId,accountVoucherNo',13,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('16','1','退款单','payment','select 
trade_id as tradeId,
order_id as payId,
relation_id as 支付单,
member_id as 会员ID,
amount as 金额,
case when status = ''INIT'' then ''初始化''
when status = ''PAYING'' then ''支付中''
when status = ''FAIL'' then ''失败''
else ''成功'' end as 状态,
case when refund_type = ''rp'' then ''重复支付退款''
when refund_type = ''oc'' then ''订单已关闭''
when refund_type = ''br'' then ''业务请求退款''
else refund_type end as 退款类型,
gmt_create as 创建时间,
gmt_modified as 修改时间
 from tp_refund_order','tradeId,payId,refundRequestId',7,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('20','3','渠道退款订单','channel','select 
b.order_id as channelOrderId, 
request_id as payFluxProcessId,
f.orig_order_id as 原支付单, 
member_id as 会员ID,
case when status = ''SU'' then ''成功''
when status = ''FA'' then ''失败''
when status = ''PR'' then ''处理中''
else ''未知'' end as 状态,
amount as 金额,
b.inst_order_id,
b.extension as 扩展信息,
b.inst_ext as 机构扩展信息,
b.gmt_create as 创建时间,
b.gmt_modified as 修改时间
from tb_biz_order b 
inner join tb_refund_order f on b.order_id = f.order_id','channelOrderId,payFluxProcessId',2,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('22','1','充值订单','payment','select 
trade_id as tradeId,
order_id as payId,
member_id as 会员ID,
account_no as 账户号,
amount as 金额,
case when status = ''SUCCESS'' then ''成功''
when status = ''PAYING'' then ''支付中''
when status = ''FAIL'' then ''失败''
else status end as 充值状态,
extension as 扩展信息,
memo as 备注,
gmt_create as 创建时间,
gmt_modified as 修改时间
 from tt_deposit_order','tradeId,payId',5,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('23','1','提现订单','payment','select 
trade_id as tradeId,
order_id as payId,
member_id as 会员ID,
account_no as 账户号,
amount as 金额,
case when status = ''SUCCESS'' then ''成功''
when status = ''PAYING'' then ''支付中''
when status = ''FAIL'' then ''失败''
else status end as 支付状态,
card_id_no as 身份证号,
card_name as 姓名,
bank_code as 银行编码,
card_no as 银行卡号,
memo as 备注,
gmt_create as 创建时间,
gmt_modified as 修改时间
 from tt_withdraw_order','tradeId,payId',5,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('24','3','资金流出','channel','select 
b.order_id as channelOrderId, 
request_id as payFluxProcessId,
member_id as 会员ID,
case when status = ''SU'' then ''成功''
when status = ''FA'' then ''失败''
when status = ''PR'' then ''处理中''
else ''未知'' end as 状态,
bank_code as 银行编码,
amount as 金额,
b.inst_order_id,
b.extension as 扩展信息,
b.inst_ext as 机构扩展信息,
b.gmt_create as 创建时间,
b.gmt_modified as 修改时间
from tb_biz_order b 
inner join tb_fund_out_order f on b.order_id = f.order_id','channelOrderId,payFluxProcessId',1,'2025-03-24 11:42:36','2025-03-24 11:42:36');
INSERT INTO `t_query_define` (`id`,`group_id`,`name`,`data_source`,`query_sql`,`param_name`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('25','1','收单订单','payment','select 
trade_id as tradeId,
order_id as payId,
case when trade_type = ''10'' then ''普通转账交易''
when trade_type = ''20'' then ''即时到账收单交易''
when trade_type = ''21'' then ''担保收单交易''
when trade_type = ''30'' then ''收单退款交易''
else trade_type end as 交易类型,
out_trade_no as 外部交易号,
partner_id as 合作方ID,
payee_id as 收款方ID,
payee_account_no as 收款方账户号,
payer_id as 付款方ID,
subject as 标题,
amount as 金额,
case when status = ''INIT'' then ''待支付''
when status = ''PAYING'' then ''支付中''
when status = ''SUCCESS'' then ''成功''
when status = ''CLOSED'' then ''关闭''
else status end as 状态,
gmt_expire as 过期时间,
extension as 扩展信息,
gmt_create as 创建时间,
gmt_modified as 修改时间
 from tt_acquiring_order','tradeId,payId',5,'2025-03-24 11:42:36','2025-03-24 11:42:36');
-- ----------------------------
-- Table structure for table t_query_group
-- ----------------------------
DROP TABLE IF EXISTS `t_query_group`;
CREATE TABLE `t_query_group` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `sort` tinyint NOT NULL DEFAULT '1' COMMENT '排序',
  `memo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='查询分组';

-- ----------------------------
-- Records of t_query_group
-- ----------------------------
INSERT INTO `t_query_group` (`id`,`name`,`sort`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('1','支付',1,NULL,'2025-03-24 11:44:25','2025-03-24 11:44:25');
INSERT INTO `t_query_group` (`id`,`name`,`sort`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('2','账务',2,NULL,'2025-03-24 11:44:25','2025-03-24 11:44:25');
INSERT INTO `t_query_group` (`id`,`name`,`sort`,`memo`,`gmt_create`,`gmt_modified`)  VALUES ('3','渠道',3,NULL,'2025-03-24 11:44:25','2025-03-24 11:44:25');
-- ----------------------------
-- Table structure for table t_query_param_define
-- ----------------------------
DROP TABLE IF EXISTS `t_query_param_define`;
CREATE TABLE `t_query_param_define` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
  `label` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '显示名称',
  `search` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可查询',
  `sort` tinyint NOT NULL DEFAULT '1' COMMENT '排序',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='查询参数定义';

-- ----------------------------
-- Records of t_query_param_define
-- ----------------------------
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('1','channelOrderId','渠道订单号',1,6,'2025-03-24 09:28:57','2025-03-24 09:28:57');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('2','inst_order_id','机构订单号',0,7,'2025-03-24 11:43:00','2025-03-24 11:43:00');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('3','command_id','指令ID',0,8,'2025-03-24 11:44:25','2025-03-24 11:44:25');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('4','tradeId','交易单号',1,1,'2025-03-24 11:44:25','2025-03-24 11:44:25');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('5','payId','支付ID',1,2,'2025-03-24 11:44:25','2025-03-24 11:44:25');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('6','payFluxOrderId','交换单号',0,3,'2025-03-24 11:44:25','2025-03-24 11:44:25');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('7','payFundDetailId','资金明细单号',0,4,'2025-03-24 11:44:25','2025-03-24 11:44:25');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('8','payFluxProcessId','交换指令ID',0,5,'2025-03-24 11:44:25','2025-03-24 11:44:25');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('10','accountVoucherNo','入账凭证号',0,10,'2025-03-24 11:44:25','2025-03-24 11:44:25');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('11','inst_request_no','机构请求号',1,1,'2025-03-24 11:44:25','2025-03-24 11:44:25');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('12','inst_response_no','机构响应号',1,12,'2025-03-24 11:44:25','2025-04-07 14:19:47');
INSERT INTO `t_query_param_define` (`id`,`name`,`label`,`search`,`sort`,`gmt_create`,`gmt_modified`)  VALUES ('16','refundChannelOrderId','退款渠道订单号',0,1,'2025-03-24 11:44:25','2025-03-24 11:44:25');
-- ----------------------------
-- Table structure for table t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '登录账户',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '盐',
  `status` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '是否可用',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100005 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` (`id`,`nickname`,`username`,`password`,`salt`,`status`,`gmt_create`,`gmt_modified`)  VALUES ('100002','测试用户2','testuser1','8e1c12ecd0f6e4b64374233b5f0621c7','dP3o1U','E','2025-05-26 18:16:09','2025-05-29 11:49:08');
INSERT INTO `t_sys_user` (`id`,`nickname`,`username`,`password`,`salt`,`status`,`gmt_create`,`gmt_modified`)  VALUES ('100003','admin','admin','4543f7ba0ae6fa5fd01581e9090900f1','nRKpjv','E','2025-05-27 10:00:12','2025-05-29 17:28:21');
INSERT INTO `t_sys_user` (`id`,`nickname`,`username`,`password`,`salt`,`status`,`gmt_create`,`gmt_modified`)  VALUES ('100004','测试用户2','testuser2','1ee52738527f8a07da85069d7e596f0c','PbY0ei','E','2025-05-27 10:17:45','2025-05-29 11:49:08');
SET FOREIGN_KEY_CHECKS=1;
