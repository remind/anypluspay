package com.anypluspay.admin.basis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wxj
 * 2024/11/5
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
