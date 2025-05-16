package com.anypluspay.commons.lang;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础结果
 * @author wxj
 * 2025/5/16
 */
@Data
public class BaseResult implements Serializable {

    /**
     * 处理状态
     */
    protected boolean success = false;

    /**
     * 结果码
     */
    protected String resultCode;

    /**
     * 结果信息
     */
    protected String resultMsg;
}
