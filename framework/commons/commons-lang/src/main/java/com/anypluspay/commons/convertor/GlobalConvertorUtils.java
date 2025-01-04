package com.anypluspay.commons.convertor;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.commons.lang.types.Money;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

/**
 * @author wxj
 * 2024/12/5
 */
public class GlobalConvertorUtils {

    public static final String LIST_SEPARATOR = ",";

    public static List<String> toList(String str) {
        return StrUtil.split(str, LIST_SEPARATOR);
    }

    public static String toStr(List<String> list) {
        return StrUtil.join(LIST_SEPARATOR, list);
    }

    public static Money toMoney(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }
}
