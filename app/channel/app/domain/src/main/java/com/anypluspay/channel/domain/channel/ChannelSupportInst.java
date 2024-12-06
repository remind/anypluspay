package com.anypluspay.channel.domain.channel;

import com.anypluspay.channel.types.enums.CardType;
import com.anypluspay.commons.lang.Entity;
import com.anypluspay.commons.lang.types.NumberRang;
import lombok.Data;

/**
 * 渠道支持的机构
 *
 * @author wxj
 * 2024/6/27
 */
@Data
public class ChannelSupportInst extends Entity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 目标机构编码，必须有值
     */
    private String targetInstCode;

    /**
     * 卡类型，为空表示无限制
     */
    private CardType cardType;

    /**
     * 扩展条件，如a=1&b=2，为空表示无限制
     */
    private String extra;

    /**
     * 单笔金额区间，为空表示无限制
     */
    private NumberRang perAmountRange;

}
