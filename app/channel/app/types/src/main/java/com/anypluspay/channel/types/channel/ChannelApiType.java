package com.anypluspay.channel.types.channel;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * @author wxj
 * 2024/6/27
 */
@Getter
public enum ChannelApiType implements CodeEnum {

    SINGLE_DEBIT("SD", "单笔扣款"), // 直接扣款类
    SIGN("SG", "签名"), // 网银、微信/支付宝等需要先签名或下单
    SEND_MESSAGE("SM", "发送短信"), // 签约后需要再发送短信
    SINGLE_QUERY("SQ", "单笔查询"),
    SINGLE_REFUND("SR", "单笔退款"),
    SINGLE_REFUND_QUERY("SRQ", "单笔退款查询"),
    MANUAL_REFUND("MR", "人工退款"),
    VERIFY_SIGN("VS", "验签"), // 异步通知结果验签
    REFUND_VERIFY_SIGN("RVS", "验签"), // 退款异步通知结果验签
    DOWNLOAD_BILL("DB", "下载账单"), // 下载对账文件

    SINGLE_FUND_OUT("SFO", "单笔出款"),
    ;

    private final String code;

    private final String displayName;

    ChannelApiType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    /**
     * 是否人工类
     * @param channelApiType
     * @return
     */
    public static boolean isManual(ChannelApiType channelApiType) {
        return MANUAL_REFUND.equals(channelApiType);
    }

    /**
     * 是否首次指令
     * @param channelApiType
     * @return
     */
    public static boolean isFirstCommand(ChannelApiType channelApiType) {
        return SINGLE_DEBIT.equals(channelApiType) || SIGN.equals(channelApiType) || SINGLE_FUND_OUT.equals(channelApiType);
    }
}