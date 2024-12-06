package com.anypluspay.admin.service;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.admin.model.query.InstQuery;
import com.anypluspay.channel.infra.persistence.dataobject.InstitutionDO;
import com.anypluspay.channel.infra.persistence.mapper.InstitutionMapper;
import com.anypluspay.commons.response.page.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wxj
 * 2024/11/5
 */
@Service
public class InstitutionService extends AbstractService {

    @Autowired
    private InstitutionMapper institutionMapper;

    public PageResult<InstitutionDO> pageQuery(InstQuery query) {
        LambdaQueryWrapper<InstitutionDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getCode())) {
            queryWrapper.like(InstitutionDO::getCode, query.getCode());
        }
        if (StrUtil.isNotBlank(query.getName())) {
            queryWrapper.like(InstitutionDO::getName, query.getName());
        }
        if (StrUtil.isNotBlank(query.getType())) {
            queryWrapper.like(InstitutionDO::getInstAbility, query.getType());
        }
        return toPageResult(institutionMapper.selectPage(getIPage(query), queryWrapper));
    }


}
