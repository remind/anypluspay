package com.anypluspay.commons.lang.std;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.anypluspay.commons.lang.types.Money;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Currency;

/**
 * @author wxj
 * 2025/4/7
 */
public class MoneyDeserializer extends StdDeserializer<Money> {
    protected MoneyDeserializer() {
        super(Money.class);
    }

    @Override
    public Money deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        if (p.getText() != null) {
            JSONObject jsonObject = JSONUtil.parseObj(p.getText());
            return new Money(jsonObject.getStr("amount") , Currency.getInstance(jsonObject.getStr("currency")));
        }
        return null;
    }
}
