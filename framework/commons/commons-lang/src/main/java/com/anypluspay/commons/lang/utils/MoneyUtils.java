package com.anypluspay.commons.lang.utils;

import com.anypluspay.commons.lang.types.Money;

/**
 * @author wxj
 * 2026/2/2
 */
public class MoneyUtils {

    /**
     * 是否大于0
     *
     * @param money
     * @return
     */
    public static boolean isGreatThanZero(Money money) {
        return money != null && money.greaterThan(new Money(0, money.getCurrency()));
    }

    /**
     * from 是否大于等于 compare
     *
     * @param from
     * @param compare
     * @return
     */
    public static boolean isGreatEqual(Money from, Money compare) {
        if (from == null && compare == null) {
            return true;
        } else if (compare == null) {
            compare = new Money(0, from.getCurrency());
        } else if (from == null) {
            from = new Money(0, compare.getCurrency());
        }
        return from.compareTo(compare) >= 0;
    }
}
