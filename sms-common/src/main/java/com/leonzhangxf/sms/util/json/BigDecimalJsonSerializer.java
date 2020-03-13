package com.leonzhangxf.sms.util.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author leonzhangxf 20171018
 */
public class BigDecimalJsonSerializer extends JsonSerializer<BigDecimal> {
    public BigDecimalJsonSerializer() {
        super();
    }

    @Override
    public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException, JsonGenerationException {
        if (null == value) {
            // write the word 'null' if there's no value available
            jgen.writeNull();
        } else {
            jgen.writeString(value.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        }
    }

}
