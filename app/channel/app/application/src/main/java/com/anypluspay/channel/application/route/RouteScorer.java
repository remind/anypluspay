package com.anypluspay.channel.application.route;

/**
 * @author wxj
 * 2024/8/24
 */
public interface RouteScorer {

    /**
     * 0分，不可用
     */
    int ZERO_SCORE = 0;

    /**
     * 1分，最小可用
     */
    int MIN_SCORE = 1;

    /**
     * 5分，标准可用
     */
    int STANDARD_SCORE = 5;

    /**
     * 不限制值
     */
    String UNLIMITED = "UNLIMITED";
}
