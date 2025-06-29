package com.anypluspay.commons.lang.std;

import com.anypluspay.commons.convertor.GlobalConvertorUtils;
import com.anypluspay.commons.lang.types.Money;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * @author wxj
 * 2025/6/29
 */
public class MoneyDisplaySerializer extends StdSerializer<Money> {
    protected MoneyDisplaySerializer() {
        super(Money.class);
    }

    @Override
    public void serialize(Money value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            gen.writeString(GlobalConvertorUtils.toDisplayMoney(value.getAmount(), value.getCurrency().getCurrencyCode()));
        }
    }
}
