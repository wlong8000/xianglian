/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.json;

import com.alibaba.json.parser.DefaultJSONParser;
import com.alibaba.json.parser.DefaultJSONParser.ResolveTask;
import com.alibaba.json.parser.Feature;
import com.alibaba.json.parser.JSONScanner;
import com.alibaba.json.parser.JSONToken;
import com.alibaba.json.parser.ParserConfig;
import com.alibaba.json.parser.deserializer.FieldDeserializer;
import com.alibaba.json.serializer.JSONSerializer;
import com.alibaba.json.serializer.SerializeConfig;
import com.alibaba.json.serializer.SerializeWriter;
import com.alibaba.json.serializer.SerializerFeature;
import com.alibaba.json.util.FieldInfo;
import com.alibaba.json.util.IOUtils;
import com.alibaba.json.util.ThreadLocalCache;
import com.alibaba.json.util.TypeUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wenshao<szujobs@hotmail.com>
 */
public abstract class JSON implements JSONStreamAware, JSONAware {

    public static int    DEFAULT_PARSER_FEATURE;
    static {
        int features = 0;
        features |= Feature.AutoCloseSource.getMask();
        features |= Feature.InternFieldNames.getMask();
        features |= Feature.UseBigDecimal.getMask();
        features |= Feature.AllowUnQuotedFieldNames.getMask();
        features |= Feature.AllowSingleQuotes.getMask();
        features |= Feature.AllowArbitraryCommas.getMask();
        features |= Feature.SortFeidFastMatch.getMask();
        features |= Feature.IgnoreNotMatch.getMask();
        DEFAULT_PARSER_FEATURE = features;
    }

    public static String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static int    DEFAULT_GENERATE_FEATURE;
    static {
        int features = 0;
        features |= com.alibaba.json.serializer.SerializerFeature.QuoteFieldNames.getMask();
        features |= com.alibaba.json.serializer.SerializerFeature.SkipTransientField.getMask();
        features |= com.alibaba.json.serializer.SerializerFeature.WriteEnumUsingToString.getMask();
        features |= com.alibaba.json.serializer.SerializerFeature.SortField.getMask();
        features |= com.alibaba.json.serializer.SerializerFeature.WriteTabAsSpecial.getMask();
//        features |= com.alibaba.fastjson.serializer.SerializerFeature.WriteSlashAsSpecial.getMask();
        DEFAULT_GENERATE_FEATURE = features;
    }

    public static final Object parse(String text) throws JSONException {
        return parse(text, DEFAULT_PARSER_FEATURE);
    }

    public static final Object parse(String text, int features) throws JSONException {
        if (text == null) {
            return null;
        }

        DefaultJSONParser parser = new DefaultJSONParser(text, ParserConfig.getGlobalInstance(), features);
        Object value = parser.parse();

        handleResovleTask(parser, value);

        parser.close();

        return value;
    }

    public static final Object parse(byte[] input, Feature... features) throws JSONException {
        return parse(input, 0, input.length, ThreadLocalCache.getUTF8Decoder(), features);
    }

    public static final Object parse(byte[] input, int off, int len, CharsetDecoder charsetDecoder, Feature... features) throws JSONException {
        if (input == null || input.length == 0) {
            return null;
        }

        int featureValues = DEFAULT_PARSER_FEATURE;
        for (Feature featrue : features) {
            featureValues = Feature.config(featureValues, featrue, true);
        }

        return parse(input, off, len, charsetDecoder, featureValues);
    }

    public static final Object parse(byte[] input, int off, int len, CharsetDecoder charsetDecoder, int features) throws JSONException {
        charsetDecoder.reset();

        int scaleLength = (int) (len * (double) charsetDecoder.maxCharsPerByte());
        char[] chars = ThreadLocalCache.getChars(scaleLength);

        ByteBuffer byteBuf = ByteBuffer.wrap(input, off, len);
        CharBuffer charBuf = CharBuffer.wrap(chars);
        IOUtils.decode(charsetDecoder, byteBuf, charBuf);

        int position = charBuf.position();

        DefaultJSONParser parser = new DefaultJSONParser(chars, position, ParserConfig.getGlobalInstance(), features);
        Object value = parser.parse();

        handleResovleTask(parser, value);

        parser.close();

        return value;
    }

    public static final Object parse(String text, Feature... features) throws JSONException {
        int featureValues = DEFAULT_PARSER_FEATURE;
        for (Feature featrue : features) {
            featureValues = Feature.config(featureValues, featrue, true);
        }

        return parse(text, featureValues);
    }

    public static final JSONObject parseObject(String text, Feature... features) throws JSONException {
        return (JSONObject) parse(text, features);
    }

    public static final JSONObject parseObject(String text) throws JSONException {
        Object obj = parse(text);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }

        return (JSONObject) JSON.toJSON(obj);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T parseObject(String text, TypeReference<T> type, Feature... features) throws JSONException {
        return (T) parseObject(text, type.getType(), ParserConfig.getGlobalInstance(), DEFAULT_PARSER_FEATURE, features);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T parseObject(String text, Class<T> clazz, Feature... features) throws JSONException {
        return (T) parseObject(text, (Type) clazz, ParserConfig.getGlobalInstance(), DEFAULT_PARSER_FEATURE, features);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T parseObject(String input, Type clazz, Feature... features) throws JSONException {
        return (T) parseObject(input, clazz, ParserConfig.getGlobalInstance(), DEFAULT_PARSER_FEATURE, features);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T parseObject(String input, Type clazz, int featureValues, Feature... features) throws JSONException {
        if (input == null) {
            return null;
        }

        for (Feature featrue : features) {
            featureValues = Feature.config(featureValues, featrue, true);
        }

        DefaultJSONParser parser = new DefaultJSONParser(input, ParserConfig.getGlobalInstance(), featureValues);
        T value = (T) parser.parseObject(clazz);

        handleResovleTask(parser, value);

        parser.close();

        return (T) value;
    }

    @SuppressWarnings("unchecked")
    public static final <T> T parseObject(String input, Type clazz, ParserConfig config, int featureValues,
                                          Feature... features) throws JSONException {
        if (input == null) {
            return null;
        }

        for (Feature featrue : features) {
            featureValues = Feature.config(featureValues, featrue, true);
        }

        DefaultJSONParser parser = new DefaultJSONParser(input, config, featureValues);
        T value = (T) parser.parseObject(clazz);

        handleResovleTask(parser, value);

        parser.close();

        return (T) value;
    }

    public static <T> int handleResovleTask(DefaultJSONParser parser, T value) throws JSONException {
        if (parser.isEnabled(Feature.DisableCircularReferenceDetect)) {
            return 0;
        }

        int size = parser.getResolveTaskList().size();
        for (int i = 0; i < size; ++i) {
            ResolveTask task = parser.getResolveTaskList().get(i);
            FieldDeserializer fieldDeser = task.getFieldDeserializer();

            Object object = null;
            if (task.getOwnerContext() != null) {
                object = task.getOwnerContext().getObject();
            }

            String ref = task.getReferenceValue();
            Object refValue;
            if (ref.startsWith("$")) {
                refValue = parser.getObject(ref);
            } else {
                refValue = task.getContext().getObject();
            }
            fieldDeser.setValue(object, refValue);
        }

        return size;
    }

    @SuppressWarnings("unchecked")
    public static final <T> T parseObject(byte[] input, Type clazz, Feature... features) throws JSONException {
        return (T) parseObject(input, 0, input.length, ThreadLocalCache.getUTF8Decoder(), clazz, features);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T parseObject(byte[] input, int off, int len, CharsetDecoder charsetDecoder, Type clazz,
                                          Feature... features) throws JSONException {
        charsetDecoder.reset();

        int scaleLength = (int) (len * (double) charsetDecoder.maxCharsPerByte());
        char[] chars = ThreadLocalCache.getChars(scaleLength);

        ByteBuffer byteBuf = ByteBuffer.wrap(input, off, len);
        CharBuffer charByte = CharBuffer.wrap(chars);
        IOUtils.decode(charsetDecoder, byteBuf, charByte);

        int position = charByte.position();

        return (T) parseObject(chars, position, clazz, features);
    }

    @SuppressWarnings("unchecked")
    public static final <T> T parseObject(char[] input, int length, Type clazz, Feature... features) throws JSONException {
        if (input == null || input.length == 0) {
            return null;
        }

        int featureValues = DEFAULT_PARSER_FEATURE;
        for (Feature featrue : features) {
            featureValues = Feature.config(featureValues, featrue, true);
        }

        DefaultJSONParser parser = new DefaultJSONParser(input, length, ParserConfig.getGlobalInstance(), featureValues);
        T value = (T) parser.parseObject(clazz);

        handleResovleTask(parser, value);

        parser.close();

        return (T) value;
    }

    public static final <T> T parseObject(String text, Class<T> clazz) throws JSONException {
        return parseObject(text, clazz, new Feature[0]);
    }

    public static final JSONArray parseArray(String text) throws JSONException {
        if (text == null) {
            return null;
        }

        DefaultJSONParser parser = new DefaultJSONParser(text, ParserConfig.getGlobalInstance());

        JSONArray array;

        JSONScanner lexer = parser.getLexer();
        if (lexer.token() == JSONToken.NULL) {
            lexer.nextToken();
            array = null;
        } else if (lexer.token() == JSONToken.EOF) {
            array = null;
        } else {
            array = new JSONArray();
            parser.parseArray(array);

            handleResovleTask(parser, array);
        }

        parser.close();

        return array;
    }

    public static final <T> List<T> parseArray(String text, Class<T> clazz) throws JSONException {
        if (text == null) {
            return null;
        }

        List<T> list;

        DefaultJSONParser parser = new DefaultJSONParser(text, ParserConfig.getGlobalInstance());
        JSONScanner lexer = parser.getLexer();
        if (lexer.token() == JSONToken.NULL) {
            lexer.nextToken();
            list = null;
        } else {
            list = new ArrayList<T>();
            parser.parseArray(clazz, list);

            handleResovleTask(parser, list);
        }

        parser.close();

        return list;
    }

    public static final List<Object> parseArray(String text, Type[] types) throws JSONException {
        if (text == null) {
            return null;
        }

        List<Object> list;

        DefaultJSONParser parser = new DefaultJSONParser(text, ParserConfig.getGlobalInstance());
        Object[] objectArray = parser.parseArray(types);
        if (objectArray == null) {
            list = null;
        } else {
            list = Arrays.asList(objectArray);
        }

        handleResovleTask(parser, list);

        parser.close();

        return list;
    }

    // ======================

    public static final String toJSONString(Object object) throws JSONException {
        return toJSONString(object, new SerializerFeature[0]);
    }

    public static final String toJSONString(Object object, SerializerFeature... features) throws JSONException {
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out);
            for (com.alibaba.json.serializer.SerializerFeature feature : features) {
                serializer.config(feature, true);
            }

            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }

    /**
     * @since 1.1.14
     */
    public static final String toJSONStringWithDateFormat(Object object, String dateFormat,
            SerializerFeature... features) throws JSONException {
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out);
            for (com.alibaba.json.serializer.SerializerFeature feature : features) {
                serializer.config(feature, true);
            }

            serializer.config(SerializerFeature.WriteDateUseDateFormat, true);

            if (dateFormat != null) {
                serializer.setDateFormat(dateFormat);
            }

            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }

    public static final byte[] toJSONBytes(Object object, SerializerFeature... features) throws JSONException {
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out);
            for (com.alibaba.json.serializer.SerializerFeature feature : features) {
                serializer.config(feature, true);
            }

            serializer.write(object);

            return out.toBytes("UTF-8");
        } finally {
            out.close();
        }
    }

    public static final String toJSONString(Object object, SerializeConfig config, SerializerFeature... features) throws JSONException {
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out, config);
            for (com.alibaba.json.serializer.SerializerFeature feature : features) {
                serializer.config(feature, true);
            }

            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }

    public static final String toJSONStringZ(Object object, SerializeConfig mapping, SerializerFeature... features) throws JSONException {
        SerializeWriter out = new SerializeWriter(features);

        try {
            JSONSerializer serializer = new JSONSerializer(out, mapping);

            serializer.write(object);

            return out.toString();
        } finally {
            out.close();
        }
    }

    public static final byte[] toJSONBytes(Object object, SerializeConfig config,
            SerializerFeature... features) throws JSONException {
        SerializeWriter out = new SerializeWriter();

        try {
            JSONSerializer serializer = new JSONSerializer(out, config);
            for (com.alibaba.json.serializer.SerializerFeature feature : features) {
                serializer.config(feature, true);
            }

            serializer.write(object);

            return out.toBytes("UTF-8");
        } finally {
            out.close();
        }
    }

    public static final String toJSONString(Object object, boolean prettyFormat) throws JSONException {
        if (!prettyFormat) {
            return toJSONString(object);
        }

        return toJSONString(object, SerializerFeature.PrettyFormat);
    }

    // ======================================

    @Override
    public String toString() {
        try {
            return toJSONString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toJSONString() throws JSONException {
        SerializeWriter out = new SerializeWriter();
        try {
            new JSONSerializer(out).write(this);
            return out.toString();
        } finally {
            out.close();
        }
    }

    public void writeJSONString(Appendable appendable) throws JSONException {
        SerializeWriter out = new SerializeWriter();
        try {
            new JSONSerializer(out).write(this);
            appendable.append(out.toString());
        } catch (IOException e) {
            throw new JSONException(e.getMessage(), e);
        } finally {
            out.close();
        }
    }

    // ///////

    public static final Object toJSON(Object javaObject) throws JSONException {
        return toJSON(javaObject, ParserConfig.getGlobalInstance());
    }

    @SuppressWarnings("unchecked")
    public static final Object toJSON(Object javaObject, ParserConfig mapping) throws JSONException {
        if (javaObject == null) {
            return null;
        }

        if (javaObject instanceof JSON) {
            return (JSON) javaObject;
        }

        if (javaObject instanceof Map) {
            Map<Object, Object> map = (Map<Object, Object>) javaObject;

            JSONObject json = new JSONObject(map.size());

            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object key = entry.getKey();
                String jsonKey = TypeUtils.castToString(key);
                Object jsonValue = toJSON(entry.getValue());
                json.put(jsonKey, jsonValue);
            }

            return json;
        }

        if (javaObject instanceof Collection) {
            Collection<Object> collection = (Collection<Object>) javaObject;

            JSONArray array = new JSONArray(collection.size());

            for (Object item : collection) {
                Object jsonValue = toJSON(item);
                array.add(jsonValue);
            }

            return array;
        }

        Class<?> clazz = javaObject.getClass();

        if (clazz.isEnum()) {
            return ((Enum<?>) javaObject).name();
        }

        if (clazz.isArray()) {
            int len = Array.getLength(javaObject);

            JSONArray array = new JSONArray(len);

            for (int i = 0; i < len; ++i) {
                Object item = Array.get(javaObject, i);
                Object jsonValue = toJSON(item);
                array.add(jsonValue);
            }

            return array;
        }

        if (mapping.isPrimitive(clazz)) {
            return javaObject;
        }

        try {
            List<FieldInfo> getters = TypeUtils.computeGetters(clazz, null);

            JSONObject json = new JSONObject(getters.size());

            for (FieldInfo field : getters) {
                Object value = field.get(javaObject);
                Object jsonValue = toJSON(value);

                json.put(field.getName(), jsonValue);
            }

            return json;
        } catch (Exception e) {
            throw new JSONException("toJSON error", e);
        }
    }

    public static final <T> T toJavaObject(JSON json, Class<T> clazz) throws JSONException {
        return TypeUtils.cast(json, clazz, ParserConfig.getGlobalInstance());
    }

    public final static String VERSION = "1.1.28";
}
