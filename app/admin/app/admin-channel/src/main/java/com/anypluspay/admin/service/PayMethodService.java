package com.anypluspay.admin.service;

import cn.hutool.core.util.StrUtil;
import com.anypluspay.admin.model.query.PayMethodQuery;
import com.anypluspay.channel.infra.persistence.dataobject.PayMethodDO;
import com.anypluspay.channel.infra.persistence.mapper.PayMethodMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wxj
 * 2024/11/12
 */
@Service
public class PayMethodService extends AbstractService {

    @Autowired
    private PayMethodMapper payMethodMapper;

    /**
     * 列表查询
     * @param query
     * @return
     */
    public List<PayMethodDO> query(PayMethodQuery query) {
        LambdaQueryWrapper<PayMethodDO> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(query.getCode())) {
            queryWrapper.like(PayMethodDO::getCode, query.getCode());
        }
        if (StrUtil.isNotBlank(query.getName())) {
            queryWrapper.like(PayMethodDO::getName, query.getName());
        }
        return payMethodMapper.selectList(queryWrapper);
    }
}
