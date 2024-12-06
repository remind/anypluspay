package com.anypluspay.component.web.json.std;

import com.anypluspay.commons.lang.types.Money;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author wxj
 * 2024/11/14
 */
public class MoneySerializer extends StdSerializer<Money> {
    protected MoneySerializer() {
        super(Money.class);
    }

    @Override
    public void serialize(Money value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.getCurrency().getSymbol() + " " + value.getAmount());
    }
}
