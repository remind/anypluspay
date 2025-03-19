package com.anypluspay.payment.types.std;

import com.anypluspay.payment.types.asset.AssetInfo;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * 资产信息反序列化器
 * @author wxj
 * 2025/3/18
 */
public class AssetInfoDeserializer extends StdDeserializer<AssetInfo> {
    protected AssetInfoDeserializer() {
        super(AssetInfo.class);
    }

    @Override
    public AssetInfo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ctxt.getAttribute("");
        return null;
    }
}
