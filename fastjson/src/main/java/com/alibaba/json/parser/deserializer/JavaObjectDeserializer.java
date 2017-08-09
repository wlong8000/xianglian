package com.alibaba.json.parser.deserializer;

import com.alibaba.json.JSONException;
import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.JSONToken;

import java.lang.reflect.Type;

public class JavaObjectDeserializer implements ObjectDeserializer {

    public final static JavaObjectDeserializer instance = new JavaObjectDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) throws JSONException {
        return (T) parser.parse(fieldName);
    }

    public int getFastMatchToken() {
        return JSONToken.LBRACE;
    }
}
