package com.anypluspay.commons.lang.types;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数值区间
 *
 * @author wxj
 * 2024/11/28
 */
public class NumberRang implements Serializable {

    /**
     * 开始值
     */
    private final RangeValue start;

    /**
     * 结束值
     */
    private final RangeValue end;

    /**
     * 解析范围字符串 (0,10) [0,10] (0,10] [0,10)
     *
     * @param rangeStr
     * @return
     */
    public static NumberRang parse(String rangeStr) {
        String[] str = rangeStr.split(",");

        boolean startEndpoint;
        if (str[0].startsWith("[")) {
            startEndpoint = true;
        } else if (str[0].startsWith("(")) {
            startEndpoint = false;
        } else {
            throw new IllegalArgumentException("Invalid range string: " + rangeStr);
        }

        BigDecimal startValue;
        if (str[0].length() == 1) {
            startValue = null;
        } else {
            startValue = new BigDecimal(str[0].substring(1));
        }

        boolean endEndpoint;
        if (str[1].endsWith("]")) {
            endEndpoint = true;
        } else if (str[1].endsWith(")")) {
            endEndpoint = false;
        } else {
            throw new IllegalArgumentException("Invalid range string: " + rangeStr);
        }

        BigDecimal endValue;
        if (str[1].length() == 1) {
            endValue = null;
        } else {
            endValue = new BigDecimal(str[1].substring(0, str[1].length() - 1));
        }

        return new NumberRang(startValue, startEndpoint, endValue, endEndpoint);
    }

    private NumberRang(BigDecimal startValue, boolean startEndpoint, BigDecimal endValue, boolean endEndpoint) {
        this.start = new RangeValue(startValue, startEndpoint);
        this.end = new RangeValue(endValue, endEndpoint);
    }

    /**
     * 是否包含指定值
     *
     * @param value
     * @return
     */
    public boolean contains(Object value) {
        if (value == null) {
            return false;
        }
        BigDecimal valueAsBigDecimal = getValue(value);
        return (start.value == null
                || (start.endpoint && valueAsBigDecimal.compareTo(start.value) >= 0)
                || (!start.endpoint && valueAsBigDecimal.compareTo(start.value) > 0)
        )
                && (end.value == null
                || (end.endpoint && end.value.compareTo(valueAsBigDecimal) >= 0)
                || (!end.endpoint && end.value.compareTo(valueAsBigDecimal) > 0)
        );
    }

    /**
     * 类型转换
     * @param value
     * @return
     */
    private static BigDecimal getValue(Object value) {
        BigDecimal valueAsBigDecimal;
        if (value instanceof BigDecimal) {
            valueAsBigDecimal = (BigDecimal) value;
        } else if (value instanceof Integer) {
            valueAsBigDecimal = new BigDecimal((Integer) value);
        } else if (value instanceof Long) {
            valueAsBigDecimal = new BigDecimal((Long) value);
        } else if (value instanceof String) {
            valueAsBigDecimal = new BigDecimal((String) value);
        } else {
            throw new IllegalArgumentException("Invalid value type: " + value.getClass());
        }
        return valueAsBigDecimal;
    }

    @Override
    public String toString() {
        return (start.endpoint ? "[" : "(") +
                (start.value == null ? "" : start.value) +
                "," +
                (end.value == null ? "" : end.value) +
                (end.endpoint ? "]" : ")");
    }

    @AllArgsConstructor
    @Getter
    private static class RangeValue {

        /**
         * 值
         */
        private BigDecimal value;

        /**
         * 是否包含
         */
        private boolean endpoint;
    }
}
