package com.anypluspay.account.domain.detail;

import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.commons.lang.types.Money;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wxj
 * 2023/12/19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InnerAccountDetail extends AccountDetail {

    /**
     * 入账前余额
     */
    private Money beforeBalance = new Money();

    /**
     * 入账后余额
     */
    private Money afterBalance = new Money();

    /**
     * 加减方向
     */
    private IODirection ioDirection;
}
