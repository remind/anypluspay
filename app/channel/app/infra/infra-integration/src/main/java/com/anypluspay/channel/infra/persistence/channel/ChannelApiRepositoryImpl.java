package com.anypluspay.channel.infra.persistence.channel;

import cn.hutool.core.collection.CollectionUtil;
import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channel.domain.repository.channel.ChannelApiRepository;
import com.anypluspay.channel.infra.persistence.convertor.ApiRequestNoModeDalConvertor;
import com.anypluspay.channel.infra.persistence.convertor.ChannelApiDalConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelApiDO;
import com.anypluspay.channel.infra.persistence.mapper.ApiRequestNoModeMapper;
import com.anypluspay.channel.infra.persistence.mapper.ChannelApiMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2024/8/28
 */
@Repository
public class ChannelApiRepositoryImpl extends AbstractRepository implements ChannelApiRepository {

    @Autowired
    private ChannelApiDalConvertor dalConvertor;

    @Autowired
    private ApiRequestNoModeDalConvertor apiRequestNoModeDalConvertor;

    @Autowired
    private ChannelApiMapper dalMapper;

    @Autowired
    private ApiRequestNoModeMapper apiRequestNoModeMapper;


    @Override
    public List<ChannelApi> loadAllEnable() {
        List<ChannelApiDO> channelApiDOS = dalMapper.selectList(new LambdaQueryWrapper<ChannelApiDO>().eq(ChannelApiDO::getEnable, true));
        List<ChannelApi> channelApis = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(channelApiDOS)) {
            channelApiDOS.forEach(channelApiDO -> {
                ChannelApi channelApi = dalConvertor.toEntity(channelApiDO);
                channelApi.setApiRequestNoMode(apiRequestNoModeDalConvertor.toEntity(apiRequestNoModeMapper.selectById(channelApiDO.getRequestNoMode())));
                channelApis.add(channelApi);
            });
        }
        return channelApis;
    }

}
