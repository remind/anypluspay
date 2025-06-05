package com.anypluspay.channel.infra.persistence;

import com.anypluspay.channel.domain.repository.ApiResultRepository;
import com.anypluspay.channel.domain.result.ApiResultCode;
import com.anypluspay.channel.infra.persistence.convertor.ApiResultCodeDalConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.ApiResultCodeDO;
import com.anypluspay.channel.infra.persistence.mapper.ApiResultCodeMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author wxj
 * 2024/8/7
 */
@Repository
public class ApiResultRepositoryImpl implements ApiResultRepository {

    @Autowired
    private ApiResultCodeMapper apiResultCodeMapper;

    @Autowired
    private ApiResultCodeDalConvertor apiResultCodeDalConvertor;

    @Override
    public void store(ApiResultCode apiResultCode) {
        apiResultCodeMapper.insert(apiResultCodeDalConvertor.toDO(apiResultCode));
    }

    @Override
    public ApiResultCode load(String fundChannelCode, String apiTypeCode, String apiCode, String apiSubCode) {
        LambdaQueryWrapper<ApiResultCodeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApiResultCodeDO::getChannelCode, fundChannelCode)
                .eq(ApiResultCodeDO::getApiType, apiTypeCode)
                .eq(ApiResultCodeDO::getInstApiCode, apiCode);
        if (StringUtils.isNotBlank(apiSubCode)) {
            queryWrapper.eq(ApiResultCodeDO::getInstApiSubCode, apiSubCode);
        }
        ApiResultCodeDO apiResultCodeDO = apiResultCodeMapper.selectOne(queryWrapper);
        return apiResultCodeDalConvertor.toEntity(apiResultCodeDO);
    }
}
