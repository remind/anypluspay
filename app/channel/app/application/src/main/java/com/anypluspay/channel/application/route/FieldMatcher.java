package com.anypluspay.channel.application.route;

import com.anypluspay.commons.enums.CodeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 字段匹配器
 *
 * @author wxj
 * 2024/6/28
 */
public class FieldMatcher {

    /**
     * 没匹配上的分数
     */
    private static final int NONE_MATCH_SCORE = 0;

    /**
     * 默认值的分数
     */
    private static final int DEFAULT_MATCH_SCORE = 5;

    /**
     * 最优匹配的分数
     */
    private static final int PERFECT_MATCH_SCORE = 10;

    /**
     * 默认权重
     */
    private static final int DEFAULT_WEIGHT = 1;

    /**
     * 对匹配值进行打分，完全匹配>默认匹配>没匹配上
     *
     * @param actualValue   实际值
     * @param configValue   配置值
     * @param weight        权重
     * @return
     */
    public static int matcherScore(String actualValue, String configValue, int weight) {
        if (StringUtils.isBlank(configValue)) {
            if (StringUtils.isBlank(actualValue)) {
                return PERFECT_MATCH_SCORE * weight;
            } else {
                return DEFAULT_MATCH_SCORE * weight;
            }
        } if (configValue.equals(actualValue)) {
            return PERFECT_MATCH_SCORE * weight;
        }
        return NONE_MATCH_SCORE;
    }

    public static int matcherScore(String actualValue, String configValue) {
        return matcherScore(actualValue, configValue, DEFAULT_WEIGHT);
    }

    public static int matcherScore(CodeEnum actualValue, CodeEnum configValue, int weight) {
        return matcherScore(targetEnumToString(actualValue), configEnumToString(configValue), weight);
    }

    public static int matcherScore(CodeEnum actualValue, CodeEnum configValue) {
        return matcherScore(actualValue, configValue, DEFAULT_WEIGHT);
    }

    public static String targetEnumToString(CodeEnum enumObject) {
        return enumObject == null ? "" : enumObject.getCode();
    }

    public static String configEnumToString(CodeEnum enumObject) {
        return enumObject == null ? "" : enumObject.getCode();
    }
}
