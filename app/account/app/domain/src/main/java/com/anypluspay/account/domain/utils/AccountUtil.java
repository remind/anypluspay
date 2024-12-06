package com.anypluspay.account.domain.utils;

import com.anypluspay.account.types.enums.AccountFamily;
import com.anypluspay.account.types.enums.BalanceDirection;
import com.anypluspay.account.types.enums.CrDr;
import com.anypluspay.account.types.enums.IODirection;
import com.anypluspay.commons.lang.types.Money;
import org.apache.commons.lang3.StringUtils;

/**
 * @author wxj
 * 2023/12/16
 */
public class AccountUtil {

    /**
     * 根据账户方向和明细方向计算加减方向
     *
     * @param accountDirection
     * @param detailDirection
     * @return
     */
    public static IODirection convert(CrDr accountDirection, CrDr detailDirection) {
        return accountDirection == detailDirection ? IODirection.IN : IODirection.OUT;
    }

    /**
     * 更新余额
     *
     * @param balance
     * @param ioDirection
     * @param amount
     */
    public static Money changeBalance(Money balance, IODirection ioDirection, Money amount) {
        return switch (ioDirection) {
            case IN -> balance.add(amount);
            case OUT -> balance.subtract(amount);
        };
    }

    /**
     * 识别账户类型
     *
     * @param accountNo
     * @return
     */
    public static AccountFamily getAccountFamily(String accountNo) {
        if (StringUtils.isNotBlank(accountNo)) {
            if (accountNo.length() == 27) {
                return AccountFamily.OUTER;
            } else if (accountNo.length() == 17) {
                return AccountFamily.INNER;
            }
        }
        return null;
    }

    /**
     * 获取余额实际借贷方向
     * @param balanceDirection
     * @return
     */
    public static CrDr getBalanceCrdr(BalanceDirection balanceDirection) {
        if (balanceDirection == BalanceDirection.CREDIT) {
            return CrDr.CREDIT;
        } else {
            return CrDr.DEBIT;
        }
    }

}
