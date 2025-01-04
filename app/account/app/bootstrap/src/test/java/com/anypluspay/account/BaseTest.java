package com.anypluspay.account;

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
}
