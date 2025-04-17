package com.anypluspay.channel;

import cn.hutool.core.io.BOMInputStream;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import com.anypluspay.channel.domain.bizorder.fund.RefundOrder;
import com.anypluspay.channel.infra.persistence.dataobject.FundChannelDO;
import com.anypluspay.commons.lang.types.Money;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wxj
 * 2024/11/28
 */
public class MainTest {

    public static void main(String[] args) throws Exception {
        A a = new A();

        A b = a;
        a = null;
        System.out.println(a);
        System.out.println(b);
    }



}
