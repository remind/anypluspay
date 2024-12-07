package com.anypluspay.channel;

import cn.hutool.json.JSONUtil;
import com.anypluspay.channel.types.test.TestConstants;
import com.anypluspay.channel.types.test.TestFlag;

/**
 * @author wxj
 * 2024/11/28
 */
public class MainTest {

    public static void main(String[] args) {

        System.out.println(JSONUtil.toJsonStr(new TestFlag(TestConstants.S, null)));
    }
}
