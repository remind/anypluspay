package com.anypluspay.admin.basis.controller;

import com.anypluspay.admin.basis.service.unionquery.UnionQueryResult;
import com.anypluspay.admin.basis.service.unionquery.UnionQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wxj
 * 2025/3/20
 */
@RestController
@RequestMapping("/union-query")
public class UnionQueryController {

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
    public List<UnionQueryResult> query(String inParamName, String inParamValue) {
        return unionQueryService.query(inParamName, inParamValue);
    }
}
