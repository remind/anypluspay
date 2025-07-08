package com.anypluspay.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxj
 * 2025/7/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnumObject {

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;
}