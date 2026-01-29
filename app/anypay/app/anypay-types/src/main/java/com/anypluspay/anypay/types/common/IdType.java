package com.anypluspay.anypay.types.common;


import com.anypluspay.commons.enums.BizIdType;

/**
 * 订单类型
 *
 * @author wxj
 * 2024/1/15
 */
public enum IdType implements BizIdType {

    TRADE_ORDER_ID("1", "10", "seq_trade_id", "交易订单"),
    PAY_ORDER_ID("2", "01", "seq_pay_id", "支付订单"),
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