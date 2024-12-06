package com.anypluspay.channel.infra.persistence.channel;

import com.anypluspay.channel.domain.channel.fund.FundChannel;
import com.anypluspay.channel.domain.repository.channel.FundChannelRepository;
import com.anypluspay.channel.infra.persistence.convertor.FundChannelDalConvertor;
import com.anypluspay.channel.infra.persistence.dataobject.FundChannelDO;
import com.anypluspay.channel.infra.persistence.mapper.FundChannelMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wxj
 * 2024/7/11
 */
@Repository
public class FundChannelRepositoryImpl extends AbstractRepository implements FundChannelRepository {

    @Autowired
    private FundChannelDalConvertor dalConvertor;

    @Autowired
    private FundChannelMapper dalMapper;

    @Override
    public List<FundChannel> loadAllEnable() {
        return dalConvertor.toEntity(dalMapper.selectList(new LambdaQueryWrapper<FundChannelDO>().eq(FundChannelDO::getEnable, true)));
    }

}
