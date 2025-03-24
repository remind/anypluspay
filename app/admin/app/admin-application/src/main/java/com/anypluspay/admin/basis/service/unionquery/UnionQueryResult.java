package com.anypluspay.admin.basis.service.unionquery;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 联合查询结果
 * @author wxj
 * 2025/3/21
 */
@Data
@AllArgsConstructor
public class UnionQueryResult {

    /**
     * 名称
     */
    private String name;

    /**
     * 顺序
     */
    private int sort;

    /**
     * 查询结果
     */
    private List<Map<String, Object>> item;
}
