package com.anypluspay.commons.lang.std;

import com.anypluspay.commons.enums.CodeEnum;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author wxj
 * 2024/12/30
 */
public class CodeEnumDeserializer extends StdDeserializer<CodeEnum> {
    protected CodeEnumDeserializer() {
        super(CodeEnum.class);
    }

    @Override
    public CodeEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        jsonParser.getText();
        return null;
    }
}
