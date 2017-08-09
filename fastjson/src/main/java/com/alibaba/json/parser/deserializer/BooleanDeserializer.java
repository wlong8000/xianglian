package com.alibaba.json.parser.deserializer;

import com.alibaba.json.JSONException;
import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.JSONScanner;
import com.alibaba.json.parser.JSONToken;
import com.alibaba.json.util.TypeUtils;

import java.lang.reflect.Type;

public class BooleanDeserializer implements ObjectDeserializer {
    public final static BooleanDeserializer instance = new BooleanDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName)
            throws JSONException {
        return (T)deserialze(parser);
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialze(DefaultJSONParser parser) throws JSONException {
        final JSONScanner lexer = parser.getLexer();

        if (lexer.token() == JSONToken.TRUE) {
            lexer.nextToken(JSONToken.COMMA);
            return (T)Boolean.TRUE;
        }

        if (lexer.token() == JSONToken.FALSE) {
            lexer.nextToken(JSONToken.COMMA);
            return (T) Boolean.FALSE;
        }
        
        if (lexer.token() == JSONToken.LITERAL_INT) {
            int intValue = lexer.intValue();
            lexer.nextToken(JSONToken.COMMA);
            
            if (intValue == 1) {
                return (T) Boolean.TRUE;
            } else {
                return (T) Boolean.FALSE;
            }
        }
        
        Object value = parser.parse();

        if (value == null) {
            return null;
        }
        
        return (T) TypeUtils.castToBoolean(value);
    }

    public int getFastMatchToken() {
        return JSONToken.TRUE;
    }
}
