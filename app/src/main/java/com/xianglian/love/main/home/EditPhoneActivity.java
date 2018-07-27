package com.xianglian.love.main.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.gson.Gson;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.xianglian.love.BaseListActivity;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.user.adapter.UserInfoEditAdapter;
import com.xianglian.love.user.been.Contacts;
import com.xianglian.love.user.been.ItemInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wl on 2017/8/4.
 * 联系方式
 */
public class EditPhoneActivity extends BaseListActivity {

    private UserInfoEditAdapter mAdapter;

    private List<ItemInfo> mItemInfo = new ArrayList<>();

    public static Intent getIntent(Context context) {
        return new Intent(context, EditPhoneActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info_fragment);
        setupTitle(getString(R.string.phone), getString(R.string.save));
        setupRecyclerView();
        doRequest();
    }

    public void setupRecyclerView() {
        super.setupRecyclerView();
        mAdapter = new UserInfoEditAdapter(this, mItemInfo);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(loadingView);
    }

    @Override
    public void onRefresh(boolean refresh) {

    }

    private void addData(List<Contacts> phones) {
        addDataByType(ItemInfo.ViewType.TOAST);
        addDataByType(ItemInfo.ViewType.TOAST2);
        addDataByType(ItemInfo.ViewType.PHONE, phones);
        mAdapter.notifyDataSetChanged();
    }

    private void addDataByType(int type) {
        addDataByType(type, null);
    }

    private void addDataByType(int type, List<Contacts> phones) {
        switch (type) {
            case ItemInfo.ViewType.TOAST:
            case ItemInfo.ViewType.TOAST2: {
                mItemInfo.add(getInfo(type));
                break;
            }
            case ItemInfo.ViewType.PHONE: {
                ItemInfo info = getInfo(type);
                info.phoneType = ItemInfo.PhoneType.WEI_XIN;
                if (phones != null && phones.size() > 0) {
                    info.contacts = phones.get(0);
                }
                mItemInfo.add(info);

                ItemInfo info2 = getInfo(type);
                info2.phoneType = ItemInfo.PhoneType.QQ;
                if (phones != null && phones.size() > 1) {
                    info.contacts = phones.get(1);
                }
                mItemInfo.add(info2);
                break;
            }
        }
    }

    @Override
    public void rightClick() {
        EditText qq = mRecyclerView.findViewWithTag(ItemInfo.PhoneType.QQ);
        EditText weiXin = mRecyclerView.findViewWithTag(ItemInfo.PhoneType.WEI_XIN);
        String qqText = qq.getText().toString();
        String weiXinText = weiXin.getText().toString();
        if (TextUtils.isEmpty(qqText) && TextUtils.isEmpty(weiXinText)) {
            showToast(R.string.phone_change_null);
        } else{
            doRequest(qqText, weiXinText);
        }
    }

    public ItemInfo getInfo(int type) {
        ItemInfo data = new ItemInfo();
        data.setViewType(type);
        return data;
    }

    public void doRequest(String qq, String weiXin) {
        dialogShow();
        final String url = Config.PATH + "user/set/contact";
        Map<String, String> params = new HashMap<>();
        params.put("uid", getUserId(this));

        if (!TextUtils.isEmpty(qq)) {
            params.put("type", getString(R.string.qq));
            params.put("content", qq);
        }

//        if (!TextUtils.isEmpty(weiXin)) {
//            params.put("type", "wei_xin");
//            params.put("content", weiXin);
//        }

        OkHttpUtil.getDefault(this).doPostAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        toast(getString(R.string.request_fail));
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        dialogDisMiss();
                        String result = info.getRetDetail();
                        if (!TextUtils.isEmpty(result)) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                int code = jsonObject.getInt("code");
                                String msg = jsonObject.getString("msg");
                                if (!TextUtils.isEmpty(msg)) showToast(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    public void doRequest() {
        final String url = Config.PATH + "user/contacts?uid=" + getUserId(this);

        OkHttpUtil.getDefault(this).doGetAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        toast(getString(R.string.request_fail));
                        finish();
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        if (!TextUtils.isEmpty(result)) {
                            try {
                                Gson gson = new Gson();
                                Contacts data = gson.fromJson(result, Contacts.class);
//                                Contacts data = JSON.parseObject(result, Contacts.class);
                                if (data != null) {
                                    Contacts msg = data.getMsg();
                                    if (msg != null) {
                                        List<Contacts> list = msg.getContacts();
                                        if (list != null && list.size() > 0) {
                                            addData(list);
                                        } else {
                                            addData(null);
                                        }
                                    }
                                } else {
                                    toast(getString(R.string.request_fail));
                                    finish();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

}
