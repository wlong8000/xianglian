package com.alibaba.json.parser.deserializer;

import com.alibaba.json.JSONException;
import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.JSONScanner;
import com.alibaba.json.parser.JSONToken;
import com.alibaba.json.util.TypeUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;

public class IntegerDeserializer implements ObjectDeserializer {
    public final static IntegerDeserializer instance = new IntegerDeserializer();

    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) throws JSONException {
        return (T) deserialze(parser);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T deserialze(DefaultJSONParser parser) throws JSONException {
        final JSONScanner lexer = parser.getLexer();
        
        if (lexer.token() == JSONToken.NULL) {
            lexer.nextToken(JSONToken.COMMA);
            return null;
        }
        
        if (lexer.token() == JSONToken.LITERAL_INT) {
            int val = lexer.intValue();
            lexer.nextToken(JSONToken.COMMA);
            return (T) Integer.valueOf(val);
        }
        
        if (lexer.token() == JSONToken.LITERAL_FLOAT) {
            BigDecimal decimalValue = lexer.decimalValue();
            lexer.nextToken(JSONToken.COMMA);
            return (T) Integer.valueOf(decimalValue.intValue());
        }
        
        Object value = parser.parse();

        return (T) TypeUtils.castToInt(value);
    }

    public int getFastMatchToken() {
        return JSONToken.LITERAL_INT;
    }
}
