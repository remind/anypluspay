package com.anypluspay.commons.lang.std;

import com.anypluspay.commons.lang.types.Money;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author wxj
 * 2025/5/19
 */
public class MoneyShowSerializer extends StdSerializer<Money> {
    protected MoneyShowSerializer() {
        super(Money.class);
    }

    @Override
    public void serialize(Money value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            gen.writeString(value.getCurrency().getSymbol() + value.getAmount().toString());
        }
    }
}
