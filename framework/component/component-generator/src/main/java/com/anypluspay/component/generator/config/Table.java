package com.anypluspay.component.generator.config;

import lombok.Data;

import java.util.List;

/**
 * @author wxj
 * 2024/1/12
 */
@Data
public class Table {

    /**
     * 前缀
     */
    private String[] prefix;

    /**
     * 表名
     */
    private List<String> name;

}
