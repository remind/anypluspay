package com.anypluspay.channel.domain;

import com.anypluspay.commons.enums.BizIdType;
import lombok.Getter;

/**
 * @author wxj
 * 2024/8/1
 */
@Getter
public enum IdType implements BizIdType {
    BIZ_FUND_IN("1", "01", "seq_biz_fund_in_id", "资金流入"),
    BIZ_REFUND("1", "02", "seq_biz_refund_id", "资金退款"),
    BIZ_FUND_OUT("1", "03", "seq_biz_fund_out_id", "资金流出"),

//    INST_ORDER("2", "01", "seq_inst_order_id", "机构订单"),
//    INST_PROCESS_ORDER("2", "02", "seq_inst_process_order_id", "机构过程订单"),
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
    @Override
    public String getBizTypeCode() {
        return parentCode + code;
    }

    @Override
    public String getSeqName() {
        return seqName;
    }
}
