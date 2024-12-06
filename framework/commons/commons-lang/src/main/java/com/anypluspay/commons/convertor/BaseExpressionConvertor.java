package com.anypluspay.commons.convertor;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.anypluspay.commons.lang.types.Money;
import com.anypluspay.commons.terminal.Terminal;
import com.anypluspay.commons.terminal.TerminalType;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 基础表达式转换器
 *
 * @author wxj
 * 2024/1/19
 */
public interface BaseExpressionConvertor {

    /**
     * list分隔符
     */
    String LIST_SEPARATOR = ",";

    default Money toMoney(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }

    default String toCurrencyCode(Money money) {
        return money != null ? money.getCurrency().getCurrencyCode() : "";
    }

    default BigDecimal toAmountValue(Money money) {
        return money != null ? money.getAmount() : BigDecimal.ZERO;
    }

    default String mapToJsonString(Map<String, String> map) {
        return map != null ? JSONUtil.toJsonStr(map) : "";
    }

    default Map<String, String> jsonStringToMap(String jsonString) {
        return StrUtil.isNotBlank(jsonString) ? JSONUtil.toBean(jsonString, Map.class) : null;
    }

    default String terminalToString(Terminal terminal) {
        return terminal != null ? JSONUtil.toJsonStr(terminal) : "";
    }

    default String getTerminalType(Terminal terminal) {
        return terminal != null ? terminal.getTerminalType().getCode() : "";
    }

    default Terminal jsonStringToTerminal(String terminalType, String terminalJsonString) {
        if (StringUtils.isBlank(terminalType)) {
            return null;
        }
        return Terminal.fromJsonString(Objects.requireNonNull(TerminalType.getByCode(terminalType)), terminalJsonString);
    }

    default  List<String> toList(String str) {
        return StrUtil.split(str, LIST_SEPARATOR);
    }

    default String toStr(List<String> list) {
        return StrUtil.join(LIST_SEPARATOR, list);
    }
}
