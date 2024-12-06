package com.anypluspay.channel.domain.channel;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

import java.util.Map;

/**
 * @author wxj
 * 2024/8/23
 */
@Data
public class BaseChannel extends Entity {

    /**
     * 渠道编码
     */
    private String code;

    /**
     * 所属机构代码
     */
    private String instCode;

    /**
     * 渠道名称
     */
    private String name;

    /**
     * 是否可用
     */
    private boolean enable;

    /**
     * 扩展参数
     */
    private Map<String,String> extra;

    /**
     * 描述
     */
    private String memo;

}
