package com.alibaba.json.serializer;

public interface NameFilter {

    String process(Object source, String name, Object value);
}
