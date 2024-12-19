package com.anypluspay.channel;

import cn.hutool.core.io.BOMInputStream;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import com.anypluspay.channel.infra.persistence.dataobject.FundChannelDO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/**
 * @author wxj
 * 2024/11/28
 */
public class MainTest {

    public static void main(String[] args) throws Exception {
        final CsvReader reader = CsvUtil.getReader();
        BOMInputStream stream = new BOMInputStream(ResourceUtil.getStream("mock/tc_fund_channel.csv"));
        final List<FundChannelDO> result = reader.read(
                new BufferedReader(new InputStreamReader(stream, stream.getCharset())), FundChannelDO.class);
        result.forEach(System.out::println);
    }

}
