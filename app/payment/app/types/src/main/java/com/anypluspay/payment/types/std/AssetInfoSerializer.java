package com.anypluspay.payment.types.std;

import com.anypluspay.payment.types.asset.AssetInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author wxj
 * 2025/3/18
 */
public class AssetInfoSerializer extends StdSerializer<AssetInfo> {
    protected AssetInfoSerializer() {
        super(AssetInfo.class);
    }

    @Override
    public void serialize(AssetInfo value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.toJsonStr());
    }
}
