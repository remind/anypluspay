package com.anypluspay.commons.lang.std;

import com.anypluspay.commons.lang.types.Extension;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author wxj
 * 2025/4/7
 */
public class ExtensionDeserializer extends StdDeserializer<Extension> {
    protected ExtensionDeserializer() {
        super(Extension.class);
    }

    @Override
    public Extension deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return new Extension(p.getText());
    }
}
