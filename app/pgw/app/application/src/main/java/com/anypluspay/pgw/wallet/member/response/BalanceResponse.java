package com.anypluspay.pgw.wallet.member.response;

import com.anypluspay.commons.lang.std.MoneyDisplaySerializer;
import com.anypluspay.commons.lang.types.Money;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

/**
 * 余额
 * @author wxj
 * 2025/6/26
 */
@Data
public class BalanceResponse {

    /**
     * 总余额
     */
    @JsonSerialize(using = MoneyDisplaySerializer.class)
    private Money balance;

    /**
     * 可用余额
     */
    @JsonSerialize(using = MoneyDisplaySerializer.class)
    private Money availableBalance;

    /**
     * 冻结余额
     */
    @JsonSerialize(using = MoneyDisplaySerializer.class)
    private Money frozenBalance;

}
