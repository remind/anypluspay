package com.anypluspay.channel.infra.persistence.channel;

import cn.hutool.core.collection.CollectionUtil;
import com.anypluspay.channel.domain.channel.ChannelFullInfo;
import com.anypluspay.channel.domain.channel.ChannelMaintain;
import com.anypluspay.channel.domain.channel.ChannelSupportInst;
import com.anypluspay.channel.domain.channel.api.ChannelApi;
import com.anypluspay.channel.domain.channel.fund.FundChannel;
import com.anypluspay.channel.domain.repository.channel.*;
import com.anypluspay.channel.types.enums.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wxj
 * 2024/8/30
 */
@Repository
public class ChannelFullInfoRepositoryImpl implements ChannelFullInfoRepository {

    private Map<String, ChannelFullInfo> channelFullInfoMap = null;

    @Autowired
    private FundChannelRepository fundChannelRepository;

    @Autowired
    private ChannelApiRepository channelApiRepository;

    @Autowired
    private ChannelMaintainRepository channelMaintainRepository;

    @Autowired
    private ChannelSupportInstRepository channelSupportInstRepository;

    @Override
    public List<ChannelFullInfo> getAllAvailableChannels(RequestType requestType, String payMethod) {
        Map<String, ChannelFullInfo> channelFullInfoMap = getChannelFullInfoMap();
        Collection<ChannelFullInfo> channelFullInfos = channelFullInfoMap.values();
        if (CollectionUtil.isNotEmpty(channelFullInfos)) {
            return channelFullInfos.stream().filter(p -> p.getFundChannel().getRequestType().equals(requestType)
                    && p.getFundChannel().getPayMethods().contains(payMethod)
            ).toList();
        }
        return null;
    }

    @Override
    public ChannelFullInfo getChannelFullInfo(String channelCode) {
        Map<String, ChannelFullInfo> channelFullInfoMap = getChannelFullInfoMap();
        return channelFullInfoMap.get(channelCode);
    }

    private synchronized Map<String, ChannelFullInfo> getChannelFullInfoMap() {
        if (channelFullInfoMap == null) {
            channelFullInfoMap = loadAllChannelFullInfo();
        }
        return channelFullInfoMap;
    }

    private Map<String, ChannelFullInfo> loadAllChannelFullInfo() {
        Map<String, ChannelFullInfo> channelFullInfoMap = new HashMap<>();
        List<FundChannel> fundChannels = fundChannelRepository.loadAllEnable();
        if (CollectionUtil.isNotEmpty(fundChannels)) {
            List<ChannelApi> channelApis = channelApiRepository.loadAllEnable();
            List<ChannelSupportInst> channelSupportInsts = channelSupportInstRepository.loadAll();
            List<ChannelMaintain> channelMaintains = channelMaintainRepository.loadAll();
            fundChannels.forEach(fundChannel -> {
                ChannelFullInfo channelFullInfo = new ChannelFullInfo();
                channelFullInfo.setFundChannel(fundChannel);
                fillBaseChannelInfo(channelFullInfo, channelApis, channelSupportInsts, channelMaintains);
                channelFullInfoMap.put(fundChannel.getCode(), channelFullInfo);
            });
        }
        return channelFullInfoMap;
    }

    private void fillBaseChannelInfo(ChannelFullInfo channelFullInfo,
                                     List<ChannelApi> channelApis,
                                     List<ChannelSupportInst> channelSupportInsts,
                                     List<ChannelMaintain> channelMaintains) {
        if (CollectionUtil.isNotEmpty(channelApis)) {
            channelFullInfo.setChannelApis(channelApis.stream().filter(p -> p.getChannelCode().equals(channelFullInfo.getChannelCode())).toList());
        }
        if (CollectionUtil.isNotEmpty(channelSupportInsts)) {
            channelFullInfo.setChannelSupportInst(channelSupportInsts.stream().filter(p -> p.getChannelCode().equals(channelFullInfo.getChannelCode())).toList());
        }
        if (CollectionUtil.isNotEmpty(channelMaintains)) {
            channelFullInfo.setChannelMaintains(channelMaintains.stream().filter(p -> p.getChannelCode().equals(channelFullInfo.getChannelCode())).toList());
        }
    }

}
