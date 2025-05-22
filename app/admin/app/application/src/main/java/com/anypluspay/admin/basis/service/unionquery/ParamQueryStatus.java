package com.anypluspay.admin.basis.service.unionquery;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 参数查询状态
 * @author wxj
 * 2025/3/20
 */
@Data
@AllArgsConstructor
public class ParamQueryStatus {

    /**
     * 输入参数值
     */
    private List<String> paramValue;

    /**
     * 是否查询完成
     */
    private boolean finish;
}
