package com.anypluspay.commons.lang.std;

import com.anypluspay.commons.enums.CodeEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author wxj
 * 2024/12/30
 */
public class CodeEnumSerializer extends StdSerializer<CodeEnum> {

    protected CodeEnumSerializer() {
        super(CodeEnum.class);
    }

    @Override
    public void serialize(CodeEnum codeEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (codeEnum != null) {
            jsonGenerator.writeString(codeEnum.getCode());
        }
    }
}
