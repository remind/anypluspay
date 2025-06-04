-- 订单清理
delete from tb_biz_order;
delete from tb_fund_in_order;
delete from tb_refund_order;
delete from tb_fund_out_order;

delete from ti_inst_command_order;
delete from ti_inst_order;

-- 修改渠道编码
update tc_api_result_code set channel_code = 'LOCALBANK001' where channel_code = 'TESTBANK001';
update tc_channel_api set channel_code = 'LOCALBANK001' where channel_code = 'TESTBANK001';
update tc_channel_maintain set channel_code = 'LOCALBANK001' where channel_code = 'TESTBANK001';
update tc_channel_param set channel_code = 'LOCALBANK001' where channel_code = 'TESTBANK001';
update tc_channel_support_inst set channel_code = 'LOCALBANK001' where channel_code = 'TESTBANK001';
update tc_fund_channel set code = 'LOCALBANK001' where code = 'TESTBANK001';