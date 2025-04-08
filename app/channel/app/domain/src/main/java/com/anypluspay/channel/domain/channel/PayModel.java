package com.anypluspay.channel.domain.channel;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * 支付模式
 * @author wxj
 * 2024/10/31
 */
@Data
public class PayModel extends Entity {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String memo;

}
