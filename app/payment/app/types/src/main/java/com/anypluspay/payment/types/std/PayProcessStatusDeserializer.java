package com.anypluspay.payment.types.std;

import com.anypluspay.commons.lang.utils.EnumUtil;
import com.anypluspay.payment.types.status.PayProcessStatus;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author wxj
 * 2025/3/19
 */
public class PayProcessStatusDeserializer extends StdDeserializer<PayProcessStatus> {
    protected PayProcessStatusDeserializer() {
        super(PayProcessStatus.class);
    }

    @Override
    public PayProcessStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        return EnumUtil.getByCode(PayProcessStatus.class, p.getText());
    }
}
