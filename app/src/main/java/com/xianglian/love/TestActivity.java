package com.xianglian.love;

import android.os.Bundle;
import android.view.View;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.xianglian.love.config.Config;
import com.xianglian.love.utils.Trace;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        findViewById(R.id.bt1).setOnClickListener(this);
        findViewById(R.id.bt2).setOnClickListener(this);
        findViewById(R.id.bt3).setOnClickListener(this);
        findViewById(R.id.bt4).setOnClickListener(this);
        findViewById(R.id.bt5).setOnClickListener(this);
    }

    private void doGoods() {
        String url = Config.PATH + "goods/";
        OkHttpUtil.getDefault(this).doGetAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        Trace.d("TAG", "result fail == " + result);
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        Trace.d("TAG", "result success == " + result);

                    }
                });
    }

    private void doLogin(String userName, String passWord) {
        String url = Config.PATH + "user/login";
        Map<String, String> params = new HashMap<>();
        params.put("mobile", userName);
        params.put("password", passWord);
        OkHttpUtil.getDefault(this).doPostAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        String result = info.getRetDetail();
                        Trace.d("TAG", "result fail == " + result);
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        Trace.d("TAG", "result success == " + result);

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                doGoods();
                break;
            case R.id.bt2:
                break;
            case R.id.bt3:
                break;
            case R.id.bt4:
                break;
            case R.id.bt5:
                break;
        }
    }
}
