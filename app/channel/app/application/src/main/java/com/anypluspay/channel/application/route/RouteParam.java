package com.anypluspay.channel.application.route;


import com.anypluspay.channel.types.channel.ChannelApiType;
import com.anypluspay.channel.types.enums.*;
import com.anypluspay.commons.lang.types.Extension;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.terminal.Terminal;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 路由参数
 *
 * @author wxj
 * 2024/6/27
 */
@Data
public class RouteParam {

    /**
     * 资金流
     */
    private RequestType requestType;

    /**
     * 支付机构
     */
    private String payInst;

    /**
     * 支付模式
     */
    private String payModel;

    /**
     * 卡类型
     */
    private CardType cardType;

    /**
     * 公司或个人
     */
    private CompanyOrPersonal companyOrPersonal;

    /**
     * 金额
     */
    private Money amount;

    /**
     * 提交时间
     */
    private LocalDateTime submitTime;

    /**
     * 终端信息
     */
    private Terminal terminal;

    /**
     * 白名单渠道
     */
    private List<String> whiteChannels;

    /**
     * 指定渠道API类型
     */
    private List<ChannelApiType> channelApiTypes;

    /**
     * 扩展参数
     */
    private Extension extension;

}
