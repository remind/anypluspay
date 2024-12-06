package com.anypluspay.commons.enums;

/**
 * 业务ID类型
 *
 * @author wxj
 * 2024/1/9
 */
public interface BizIdType {

    /**
     * 3位数字编码
     *
     * @return
     */
    String getBizTypeCode();

    /**
     * 序列名称
     *
     * @return
     */
    String getSeqName();
}
