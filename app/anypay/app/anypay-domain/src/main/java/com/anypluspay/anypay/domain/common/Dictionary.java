package com.anypluspay.anypay.domain.common;

import com.anypluspay.commons.lang.Entity;
import lombok.Data;

/**
 * @author wxj
 * 2025/12/22
 */
@Data
public class Dictionary extends Entity {
    /**
     * 字典类型
     */
    private String type;

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

    /**
     * 扩展信息
     */
    private String extra;
}
