package com.anypluspay.channel.types.test;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 测试标签
 * @author wxj
 * 2024/8/25
 */
@Data
@AllArgsConstructor
public class TestFlag {

    /**
     * 扣款标志，可用于SG、SR
     */
    private String d;

    /**
     * 查询标志
     */
    private String q;
}
