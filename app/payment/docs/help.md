
测试SQL：
```sql
select * from ap_payment.tp_payment where payment_id = '202501240011014500001601';
select * from ap_payment.tp_pay_order where payment_id = '202501240011014500001601';
select * from ap_payment.tp_fund_detail where payment_id = '202501240011014500001601';
select * from ap_payment.tp_flux_order where payment_id = '202501240011014500001601';
select * from ap_payment.tp_flux_instruction where payment_id = '202501240011014500001601';

delete from ap_payment.tp_payment;
delete from ap_payment.tp_pay_order;
delete from ap_payment.tp_fund_detail;
delete from ap_payment.tp_flux_order;
delete from ap_payment.tp_flux_instruction;
```