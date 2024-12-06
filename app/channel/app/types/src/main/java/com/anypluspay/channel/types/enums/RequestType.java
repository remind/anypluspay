package com.anypluspay.channel.types.enums;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/8/9
 */
@Getter
public enum RequestType implements CodeEnum  {
    FUND_IN(RequestRootType.FUND, "FUND_IN", "资金流入"),
    FUND_OUT(RequestRootType.FUND, "FUND_OUT", "资金流出"),
    REFUND(RequestRootType.FUND, "REFUND", "退款"),

    FOUR_FACTOR(RequestRootType.AUTH, "FOUR_FACTOR", "四要素验证"),

    DOWNLOAD_BILL(RequestRootType.CONTROL, "DB", "下载账单"),
    ;


    /**
     * 根类型编码，主要用于分类
     */
    private final RequestRootType requestRootType;

    /**
     * 类型编码，整体唯一
     */
    private final String code;

    /**
     * 显示名称
     */
    private final String displayName;

    RequestType(RequestRootType requestRootType, String code, String displayName) {
        this.requestRootType = requestRootType;
        this.code = code;
        this.displayName = displayName;
    }

}
