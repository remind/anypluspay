package com.anypluspay.commons.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wxj
 * 2024/12/10
 */
@Data
public class FacadeResult implements Serializable {

    /**
     * 处理状态
     */
    private boolean success;

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态描述
     */
    private String message;
}
