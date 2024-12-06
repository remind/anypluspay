package com.anypluspay.channel.types.channel;

import com.anypluspay.commons.enums.CodeEnum;
import lombok.Getter;

/**
 * 维护时间类型
 *
 * @author wxj
 * 2024/6/27
 */
@Getter
public enum MaintainTimeType implements CodeEnum {

    /**
     * 固定，格式为"yyyy-MM-dd HH:mm:ss,yyyy-MM-dd HH:mm:ss"
     */
    FIXED("FIXED", "固定"),

    /**
     * 每天，格式为"HH:mm:ss,HH:mm:ss"
     */
    DAY("DAY", "每天"),

    /**
     * 每月，格式为"dd HH:mm:ss,dd HH:mm:ss"
     */
    MONTHLY("MONTHLY", "每月"),

    /**
     * cron表达式，格式为"开始时间cron表达式,结束时间cron表达式"
     */
    CRON("CRON", "cron表达式"),
    ;

    private final String code;

    private final String displayName;

    MaintainTimeType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}
