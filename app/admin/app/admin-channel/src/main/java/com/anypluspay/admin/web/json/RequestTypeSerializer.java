package com.anypluspay.admin.web.json;

import com.anypluspay.channel.types.enums.RequestType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author wxj
 * 2024/11/21
 */
public class RequestTypeSerializer extends StdSerializer<RequestType> {
    protected RequestTypeSerializer() {
        super(RequestType.class);
    }

    @Override
    public void serialize(RequestType value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            gen.writeString(value.getCode() + "(" + value.getDisplayName() + ")");
        }
    }
}
