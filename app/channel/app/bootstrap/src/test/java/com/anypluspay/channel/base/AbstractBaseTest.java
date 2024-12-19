package com.anypluspay.channel.base;

import cn.hutool.core.io.BOMInputStream;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author wxj
 * 2024/12/16
 */
public class AbstractBaseTest {

    private final CsvReader reader = CsvUtil.getReader();

    protected  <T> List<T> getCsvData(String filePath, Class<T> tClass) {
        BOMInputStream stream = new BOMInputStream(ResourceUtil.getStream(filePath));
        try {
            return reader.read(
                    new BufferedReader(new InputStreamReader(stream, stream.getCharset())), tClass);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    protected String randomId() {
        return UUID.fastUUID().toString(true);
    }
}
