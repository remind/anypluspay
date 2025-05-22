package com.anypluspay.admin.basis.service.unionquery;

import com.anypluspay.admin.basis.mapper.dataobject.QueryDefineDO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 查询结果
 * @author wxj
 * 2025/3/21
 */
@Data
@AllArgsConstructor
public class QueryResult {

    /**
     * 查询配置
     */
    private QueryDefineDO queryDefine;

    /**
     * 参数值
     */
    private Map<String, List<String>> paramValueMap;

    /**
     * 查询结果
     */
    private List<Map<String, Object>> item;
}
