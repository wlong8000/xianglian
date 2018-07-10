package com.xianglian.love.net;

import android.util.Log;

import com.google.gson.Gson;
import com.lzy.okgo.convert.Converter;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by txl on 2018/6/27.
 */

public class GsonCovert<T> implements Converter<T> {

    private Gson gson;

    private Class<T> type;

    public GsonCovert(Gson gson, Class<T> type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody responseBody = response.body();
        if (responseBody == null) {
            return null;
        }
        String str = responseBody.string();
        Log.i("GsonCovert", str);
        Log.i("GsonCovert", "type:" + type);
        T result = gson.fromJson(str, type);
        Log.i("GsonCovert result:", result.toString());
        return result;
    }
}
