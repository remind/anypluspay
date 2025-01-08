package com.anypluspay.account;

import com.anypluspay.commons.exceptions.BizException;
import com.anypluspay.commons.response.ResultCode;
import org.junit.Assert;

import java.util.UUID;

/**
 * @author wxj
 * 2025/1/3
 */
public abstract class BaseTest {

    protected String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-","").substring(0,32);
    }

    /**
     * 异常包装
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
}
