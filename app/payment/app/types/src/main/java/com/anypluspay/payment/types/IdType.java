package com.anypluspay.payment.types;


import com.anypluspay.commons.enums.BizIdType;

/**
 * 订单类型
 * @author wxj
 * 2024/1/15
 */
public enum IdType implements BizIdType {

    ACQUIRING_ORDER_ID("1", "10", "seq_trade_id", "交易-收单订单"),
    DEPOSIT_ORDER_ID("1", "20", "seq_trade_id", "交易-充值单"),
    WITHDRAW_ORDER_ID("1", "30", "seq_trade_id", "交易-提现单"),
    PAY_ORDER_ID("2", "01", "seq_pay_order_id", "支付-支付单"),
    REFUND_ORDER_ID("2", "02", "seq_pay_order_id", "支付-退款单"),
    FUND_DETAIL_ID("3", "01", "seq_fund_detail_id", "支付-资金明细单"),
    FLUX_ORDER_ID("4", "01", "seq_flux_order_id", "交换-资产交换单"),
    FLUX_INSTRUCT_ID("4", "02", "seq_flux_instruct_id", "交易-资产交换指令"),
    ;

    /**
     * 1位数字
     */
    private final String parentCode;

    /**
     * 2位数字
     */
    private final String code;

    private final String seqName;

    private final String name;

    IdType(String parentCode, String code, String seqName, String name) {
        this.parentCode = parentCode;
        this.code = code;
        this.seqName = seqName;
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static IdType getByBizTypeCode(String bizTypeCode) {
        for (IdType idType : IdType.values()) {
            if (idType.getBizTypeCode().equals(bizTypeCode)) {
                return idType;
            }
        }
        return null;
    }

    @Override
    public String getBizTypeCode() {
        return parentCode + code;
    }

    @Override
    public String getSeqName() {
        return seqName;
    }

}