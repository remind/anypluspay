package com.anypluspay.component.test;

import cn.hutool.core.io.BOMInputStream;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.core.util.RandomUtil;
import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.response.ResultCode;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 测试基类
 *
 * @author wxj
 * 2025/1/10
 */
public abstract class AbstractBaseTest {

    private final CsvReader reader = CsvUtil.getReader();

    /**
     * 读取csv文件
     *
     * @param filePath
     * @param tClass
     * @param <T>
     * @return
     */
    protected <T> List<T> getCsvData(String filePath, Class<T> tClass) {
        BOMInputStream stream = new BOMInputStream(ResourceUtil.getStream(filePath));
        try {
            return reader.read(
                    new BufferedReader(new InputStreamReader(stream, stream.getCharset())), tClass);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 异常包装
     *
     * @param resultCode
     * @param c
     */
    protected void wrapperBizException(ResultCode resultCode, Runnable c) {
        try {
            c.run();
            Assert.fail();
        } catch (BizException e) {
            Assert.assertEquals(resultCode.getCode(), e.getCode());
        }
    }

    /**
     * 随机id
     *
     * @return
     */
    protected String randomId() {
        return UUID.fastUUID().toString(true);
    }

    public String randomPersonalMemberId() {
        return "100000000" + RandomUtil.randomNumbers(3);
    }
}
