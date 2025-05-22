package com.anypluspay.admin.basis.controller;

import com.anypluspay.admin.basis.mapper.QueryGroupMapper;
import com.anypluspay.admin.basis.mapper.QueryParamDefineMapper;
import com.anypluspay.admin.basis.mapper.dataobject.QueryGroupDO;
import com.anypluspay.admin.basis.mapper.dataobject.QueryParamDefineDO;
import com.anypluspay.admin.basis.service.unionquery.UnionQueryResult;
import com.anypluspay.admin.basis.service.unionquery.UnionQueryService;
import com.anypluspay.commons.response.ResponseResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 联合查询
 * @author wxj
 * 2025/3/20
 */
@RestController
@RequestMapping("/union-query")
public class UnionQueryController {

    @Autowired
    private QueryGroupMapper queryGroupMapper;

    @Autowired
    private QueryParamDefineMapper queryParamDefineMapper;

    @Autowired
    private UnionQueryService unionQueryService;

    /**
     * 查询
     *
     * @param inParamName  参数名称
     * @param inParamValue 参数值
     * @return
     */
    @GetMapping("/query")
    public ResponseResult<Map<Long, List<UnionQueryResult>>> query(String inParamName, String inParamValue) {
        return ResponseResult.success(unionQueryService.query(inParamName, inParamValue));
    }

    /**
     * 获取搜索参数
     *
     * @return
     */
    @GetMapping("/search-param")
    public ResponseResult<Map<String,String>> getSearchParam() {
        Map<String, String> result = new LinkedHashMap<>();
        LambdaQueryWrapper<QueryParamDefineDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(QueryParamDefineDO::isSearch, true);
        queryWrapper.orderByAsc(QueryParamDefineDO::getSort);
        List<QueryParamDefineDO> queryParamDefineDOS = queryParamDefineMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(queryParamDefineDOS)) {
            queryParamDefineDOS.forEach(queryParamDefineDO -> {
                result.put(queryParamDefineDO.getName(), queryParamDefineDO.getLabel());
            });
        }
        return ResponseResult.success(result);
    }

    /**
     * 获取分组
     *
     * @return
     */
    @GetMapping("/group")
    public ResponseResult<Map<String,String>> getGroup() {
        Map<String, String> result = new LinkedHashMap<>();
        LambdaQueryWrapper<QueryGroupDO> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(QueryGroupDO::getSort);
        List<QueryGroupDO> queryParamDefineDOS = queryGroupMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(queryParamDefineDOS)) {
            queryParamDefineDOS.forEach(queryParamDefineDO -> {
                result.put("g_" + queryParamDefineDO.getId(), queryParamDefineDO.getName());
            });
        }
        return ResponseResult.success(result);
    }
}
