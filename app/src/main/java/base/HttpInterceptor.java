package base;
import com.okhttplib.HttpInfo;
import com.okhttplib.interceptor.ExceptionInterceptor;
import com.okhttplib.interceptor.ResultInterceptor;
import com.xianglian.love.utils.Trace;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Http拦截器
 * 1、请求结果统一预处理拦截器
 * 2、请求链路异常信息拦截器
 *
 * @author zhousf
 */
public class HttpInterceptor {
    public static final String TAG = "HttpInterceptor";

    /**
     * 请求结果统一预处理拦截器
     * 该拦截器会对所有网络请求返回结果进行预处理并修改
     */
    public static ResultInterceptor ResultInterceptor = new ResultInterceptor() {
        @Override
        public HttpInfo intercept(HttpInfo info) throws Exception {
            //请求结果预处理：可以进行GSon过滤与解析
            openRequestLog(info);
            return info;
        }
    };

    private static void openRequestLog(HttpInfo info) throws JSONException {
        Map<String, String> header = info.getHeads();
        JSONObject obj = new JSONObject();
        obj.put("url", info.getUrl());
        if (header != null) {
            JSONArray array = new JSONArray();
            for (String key : header.keySet()) {
                String value = header.get(key);
                JSONObject o = new JSONObject();
                o.put(key, value);
                array.put(o);
            }
            obj.put("header", array);
        }

        Map<String, String> params = info.getParams();
        if (params != null) {
            JSONArray array = new JSONArray();
            for (String key : params.keySet()) {
                String value = params.get(key);
                JSONObject o = new JSONObject();
                o.put(key, value);
                array.put(o);
            }
            obj.put("params", array);
        }
        obj.put("response", info.getRetDetail());
        obj.put("response_code", info.getNetCode());
        Trace.d(TAG, "request log = " + obj.toString());
    }

    /**
     * 请求链路异常信息拦截器
     * 该拦截器会发送网络请求时链路异常信息进行拦截处理
     */
    public static ExceptionInterceptor ExceptionInterceptor = new ExceptionInterceptor() {
        @Override
        public HttpInfo intercept(HttpInfo info) throws Exception {
            String result = info.getRetDetail();
            switch (info.getRetCode()) {
                case HttpInfo.NonNetwork:
                    info.setRetDetail("网络中断：" + result);
                    break;
                case HttpInfo.CheckURL:
                    info.setRetDetail("网络地址错误[" + info.getNetCode() + "]：" + result);
                    break;
                case HttpInfo.CheckNet:
                    info.setRetDetail("请检查网络连接是否正常[" + info.getNetCode() + "]：" + result);
                    break;
                case HttpInfo.ProtocolException:
                    info.setRetDetail("协议类型错误[" + info.getNetCode() + "]：" + result);
                    break;
                case HttpInfo.ConnectionTimeOut:
                    info.setRetDetail("连接超时：" + result);
                    break;
                case HttpInfo.WriteAndReadTimeOut:
                    info.setRetDetail("读写超时：" + result);
                    break;
                case HttpInfo.ConnectionInterruption:
                    info.setRetDetail("连接中断：" + result);
                    break;
            }
            return info;
        }
    };


}
