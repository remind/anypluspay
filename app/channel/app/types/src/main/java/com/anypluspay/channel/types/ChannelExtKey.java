package com.anypluspay.channel.types;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 扩展字段Key
 * @author wxj
 * 2024/6/30
 */
@Getter
public enum ChannelExtKey implements CodeEnum {
    WHITE_CHANNELS("whiteChannels", "白名单渠道编码"),
    CALLBACK_SERVER_URL("callbackServerUrl", "机构服务端回调地址"),
    CALLBACK_PAGE_URL("callbackPageUrl", "机构页面回调地址"),
    COMPANY_PERSONAL("companyOrPersonal", "对公对私"),
    CARD_TYPE("cardType", "卡类型"),
    ORIG_INST_ORDER_ID("origInstOrderId", "原始机构订单号"),
    ORIG_INST_REQUEST_NO("origInstRequestNo", "原始机构请求单号"),
    ORIG_INST_RESPONSE_NO("origInstResponseNo", "原始机构响应单号"),
    NOTIFY_RESPONSE_DATA("notify_response_data", "通知响应数据"),
    TEST_FLAG("testFlag", "测试标志"),
    GOODS_DESC("goodsDesc", "商品描述"),
    GOODS_SUBJECT("goodsSubject", "商品主题"),
    INST_REDIRECTION_DATA("ird", "机构跳转参数"),
    ;

    private final String code;

    private final String displayName;

    ChannelExtKey(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

}
