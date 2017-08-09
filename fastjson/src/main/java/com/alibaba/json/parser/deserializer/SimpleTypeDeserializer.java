package com.alibaba.json.parser.deserializer;

import com.alibaba.json.JSONException;
import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.JSONToken;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

public class SimpleTypeDeserializer implements ObjectDeserializer {

    public final static SimpleTypeDeserializer instance = new SimpleTypeDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) throws JSONException {

        String text = (String) parser.parse();

        if (text == null) {
            return null;
        }

        if (clazz == TimeZone.class) {
            return (T) TimeZone.getTimeZone(text);
        }
        
        if (clazz == UUID.class) {
            return (T) UUID.fromString(text);
        }
        
        if (clazz == URI.class) {
            return (T) URI.create(text);
        }
        
        if (clazz == URL.class) {
            try {
                return (T) new URL(text);
            } catch (MalformedURLException e) {
                throw new JSONException("create url error", e);
            }
        }
        
        if (clazz == Pattern.class) {
            return (T) Pattern.compile(text);
        }
        
        if (clazz == Charset.class) {
            return (T) Charset.forName(text);
        }
        
        throw new JSONException("not support type : " + clazz);
    }

    public int getFastMatchToken() {
        return JSONToken.LITERAL_STRING;
    }
}
