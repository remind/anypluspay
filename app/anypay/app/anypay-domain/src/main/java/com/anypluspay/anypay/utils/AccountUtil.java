package com.anypluspay.anypay.utils;

import com.anypluspay.anypay.types.account.*;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.lang.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author wxj
 * 2023/12/16
 */
public class AccountUtil {

    private static final DateTimeFormatter ACCOUNTING_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

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
     * 是否为外部账户
     *
     * @param accountNo 账户号
     * @return
     */
    public static boolean isOuterAccount(String accountNo) {
        return getAccountFamily(accountNo) == AccountFamily.OUTER;
    }

    /**
     * 获取余额实际借贷方向
     *
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

    /**
     * 获取当天的记账日期
     *
     * @return
     */
    public static String getTodayAccounting() {
        return LocalDate.now().format(ACCOUNTING_DATE_FORMATTER);
    }

    /**
     * 获取账户号科目代码
     *
     * @param accountNo 账户号
     * @return 科目代码
     */
    public static String getAccountTitle(String accountNo) {
        AssertUtil.notNull(getAccountFamily(accountNo), AccountResultCode.ACCOUNT_NOT_EXISTS);
        return accountNo.substring(0, 10);
    }

}
