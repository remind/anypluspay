package com.anypluspay.channel.infra.persistence.channel;

import com.anypluspay.channel.domain.channel.ChannelMaintain;
import com.anypluspay.channel.domain.repository.channel.ChannelMaintainRepository;
import com.anypluspay.channel.infra.persistence.convertor.ChannelMaintainDalConvertor;
import com.anypluspay.channel.infra.persistence.mapper.ChannelMaintainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2024/8/30
 */
@Repository
public class ChannelMaintainRepositoryImpl implements ChannelMaintainRepository {

    @Autowired
    private ChannelMaintainMapper dalMapper;

    @Autowired
    private ChannelMaintainDalConvertor dalConvertor;

    @Override
    public List<ChannelMaintain> loadAll() {
        return dalConvertor.toEntity(dalMapper.selectList(null));
    }

}
