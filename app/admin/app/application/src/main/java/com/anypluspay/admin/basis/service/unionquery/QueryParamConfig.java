package com.anypluspay.admin.basis.service.unionquery;

import com.anypluspay.admin.basis.mapper.dataobject.QueryDefineDO;
import com.anypluspay.admin.basis.mapper.dataobject.QueryParamDefineDO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 查询参数配置
 * @author wxj
 * 2025/3/20
 */
@Data
@AllArgsConstructor
public class QueryParamConfig {

    /**
     * 参数名称
     */
    private String name;

    /**
     * 参数定义
     */
    private QueryParamDefineDO queryParamDefineDO;

    /**
     * 查询定义
     */
    private List<QueryDefineDO> queryDefineDOS;
}
