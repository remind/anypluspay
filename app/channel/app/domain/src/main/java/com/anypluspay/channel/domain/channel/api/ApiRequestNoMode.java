package com.anypluspay.channel.domain.channel.api;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 请求流水号模式
 * @author wxj
 * 2024/9/18
 */
@Data
public class ApiRequestNoMode extends Entity {

    /**
     * 编码
     */
    private String code;

    /**
     * 生成模式
     */
    private String genPattern;

    /**
     * 序列名称
     */
    private String seqName;

    /**
     * 备注
     */
    private String memo;
}
