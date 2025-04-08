package com.anypluspay.commons.lang.std;

import com.anypluspay.commons.lang.types.Extension;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author wxj
 * 2025/4/7
 */
public class ExtensionSerializer extends StdSerializer<Extension> {
    protected ExtensionSerializer() {
        super(Extension.class);
    }

    @Override
    public void serialize(Extension value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.toJsonString());
    }
}
