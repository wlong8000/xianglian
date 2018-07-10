package com.xianglian.love.net;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;

/**
 * Created by wanglong on 18/7/8.
 */

public abstract class JsonCallBack<T> extends AbsCallback<T> {

    private GsonCovert<T> gsonCovert;

    public JsonCallBack(Class<T> clazz) {
        gsonCovert = new GsonCovert<>(new Gson(), clazz);
    }

    @Override
    public T convertResponse(okhttp3.Response response) throws Throwable {
        return gsonCovert.convertResponse(response);
    }
}
