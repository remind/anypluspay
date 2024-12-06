package com.anypluspay.channel.types.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 处理结果
 * @author wxj
 * 2024/9/15
 */
@Data
public class ProcessResult implements Serializable {

    /**
     * 结果码
     */
    private String apiCode;

    /**
     * 子结果码
     */
    private String apiSubCode;

    /**
     * 描述信息
     */
    private String apiMessage;
}
