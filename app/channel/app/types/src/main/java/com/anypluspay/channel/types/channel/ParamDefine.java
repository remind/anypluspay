package com.anypluspay.channel.types.channel;

import lombok.Data;

/**
 * 参数定义
 * @author wxj
 * 2024/11/15
 */
@Data
public class ParamDefine {

    /**
     * 渠道编码
     */
    private String channelCode;

    /**
     * 参数编码
     */
    private String paramCode;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数类型
     */
    private String type;

    /**
     * 备注
     */
    private String memo;

    /**
     * 排序
     */
    private int sort;

}
