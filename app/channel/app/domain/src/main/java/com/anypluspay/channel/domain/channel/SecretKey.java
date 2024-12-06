package com.anypluspay.channel.domain.channel;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 密钥信息
 *
 * @author wxj
 * 2024/9/19
 */
@Data
public class SecretKey extends Entity {

    /**
     * ID
     */
    private String keyId;

    /**
     * 名称
     */
    private String name;

    /**
     * 值
     */
    private String value;

    /**
     * 备注
     */
    private String memo;
}
