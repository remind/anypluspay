package com.anypluspay.commons.convertor;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.commons.lang.types.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

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

    public static String toDisplayMoney(BigDecimal amount, String currencyCode) {
        Money money = toMoney(amount, currencyCode);
        return money.getCurrency().getSymbol(Locale.CHINA) + money.getAmount().toString();
    }

    public static String dateToString(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "";
    }
}
