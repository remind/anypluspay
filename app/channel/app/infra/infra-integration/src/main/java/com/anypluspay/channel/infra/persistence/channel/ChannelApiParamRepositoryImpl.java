package com.anypluspay.channel.infra.persistence.channel;

import com.anypluspay.channel.domain.channel.api.ChannelApiParam;
import com.anypluspay.channel.domain.repository.channel.ChannelApiParamRepository;
import com.anypluspay.channel.infra.persistence.convertor.ChannelApiParamDalConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.ChannelApiParamDO;
import com.anypluspay.channel.infra.persistence.mapper.ChannelApiParamMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2025/6/5
 */
@Repository
public class ChannelApiParamRepositoryImpl implements ChannelApiParamRepository {

    @Autowired
    private ChannelApiParamMapper dalMapper;

    @Autowired
    private ChannelApiParamDalConvertor dalConvertor;

    @Override
    public List<ChannelApiParam> loadAllEnable() {
        return dalConvertor.toEntity(dalMapper.selectList(Wrappers.emptyWrapper()));
    }

    @Override
    public ChannelApiParam loadByPartnerIdANdChannelCode(String partnerId, String channelCode) {
        ChannelApiParamDO channelApiParamDO = dalMapper.selectOne(Wrappers.<ChannelApiParamDO>lambdaQuery()
                .eq(ChannelApiParamDO::getPartnerId, partnerId)
                .eq(ChannelApiParamDO::getChannelCode, channelCode));
        return dalConvertor.toEntity(channelApiParamDO);
    }

    @Override
    public ChannelApiParam load(String id) {
        return dalConvertor.toEntity(dalMapper.selectById(id));
    }
}
