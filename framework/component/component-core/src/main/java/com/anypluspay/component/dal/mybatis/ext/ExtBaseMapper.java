package com.anypluspay.component.dal.mybatis.ext;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * @author wxj
 * 2023/12/18
 */
public interface ExtBaseMapper<T> extends BaseMapper<T> {

    T lockById(Serializable id);

    T lockOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
