package com.anypluspay.commons.lang.utils;

import com.anypluspay.commons.lang.types.Money;

/**
 * @author wxj
 * 2026/2/2
 */
public class MoneyUtils {

    public static boolean isGreatThanZero(Money money) {
        return money != null && money.greaterThan(new Money(0, money.getCurrency()));
    }
}
