package com.anypluspay.channel.infra.persistence.channel;

import com.anypluspay.channel.domain.channel.ChannelSupportInst;
import com.anypluspay.channel.domain.repository.channel.ChannelSupportInstRepository;
import com.anypluspay.channel.infra.persistence.convertor.ChannelSupportInstDalConvertor;
import com.anypluspay.channel.infra.persistence.mapper.ChannelSupportInstMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2024/8/28
 */
@Repository
public class ChannelSupportInstRepositoryImpl implements ChannelSupportInstRepository {

    @Autowired
    private ChannelSupportInstMapper dalMapper;

    @Autowired
    private ChannelSupportInstDalConvertor dalConvertor;

    @Override
    public List<ChannelSupportInst> loadAll() {
        return dalConvertor.toEntity(dalMapper.selectList(null));
    }

}
