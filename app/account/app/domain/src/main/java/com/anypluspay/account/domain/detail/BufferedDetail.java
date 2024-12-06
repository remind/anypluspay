package com.anypluspay.account.domain.detail;

import com.anypluspay.account.types.enums.BufferDetailStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 缓冲明细
 *
 * @author wxj
 * 2023/12/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BufferedDetail extends AccountDetail {

    /**
     * 状态
     */
    private BufferDetailStatus status;

    /**
     * 执行次数
     **/
    private Integer executeCount;
}
