package com.anypluspay.anypay.domain.account;

import com.anypluspay.anypay.types.account.AccountFamily;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 内部账户
 *
 * @author wxj
 * 2023/12/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InnerAccount extends Account {

    /**
     * 备注
     */
    private String memo;

    @Override
    public AccountFamily getAccountFamily() {
        return AccountFamily.INNER;
    }

}
