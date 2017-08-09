package com.alibaba.json.parser.deserializer;

import com.alibaba.json.JSONException;
import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.util.FieldInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class FieldDeserializer {

    protected final FieldInfo fieldInfo;

    protected final Class<?>  clazz;

    public FieldDeserializer(Class<?> clazz, FieldInfo fieldInfo){
        this.clazz = clazz;
        this.fieldInfo = fieldInfo;
    }

    public Method getMethod() {
        return fieldInfo.getMethod();
    }

    public Class<?> getFieldClass() {
        return fieldInfo.getFieldClass();
    }

    public Type getFieldType() {
        return fieldInfo.getFieldType();
    }

    public abstract void parseField(DefaultJSONParser parser, Object object, Type objectType, Map<String, Object> fieldValues) throws JSONException;

    public abstract int getFastMatchToken();

    public void setValue(Object object, boolean value) throws JSONException {
        setValue(object, Boolean.valueOf(value));
    }

    public void setValue(Object object, int value) throws JSONException {
        setValue(object, Integer.valueOf(value));
    }

    public void setValue(Object object, long value) throws JSONException {
        setValue(object, Long.valueOf(value));
    }

    public void setValue(Object object, String value) throws JSONException {
        setValue(object, (Object) value);
    }

    public void setValue(Object object, Object value) throws JSONException {
        Method method = fieldInfo.getMethod();
        if (method != null) {
            try {
                method.invoke(object, value);
            } catch (Exception e) {
                throw new JSONException("set property error, " + fieldInfo.getName(), e);
            }
        } else if (fieldInfo.getField() != null) {
            try {
                fieldInfo.getField().set(object, value);
            } catch (Exception e) {
                throw new JSONException("set property error, " + fieldInfo.getName(), e);
            }
        }
    }
}
