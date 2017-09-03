package com.xianglian.love.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.user.adapter.CommonObjectAdapter;
import com.xianglian.love.utils.UserUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/8.
 * 标签/爱好
 */
public class MarkTagActivity extends BaseUserInfoActivity implements View.OnClickListener {

    private GridView mGridView;

    private TextView mTagMostView;

    private LinearLayout mCompleteView;

    private List<Object> mTagList = new ArrayList<>();

    private List<String> mSelectTagData = new ArrayList<>();

    private CommonObjectAdapter mGridViewAdapter;

    //个人标签
    public static final int MARK = 0;

    //兴趣爱好
    public static final int HOBBY = 1;

    private int mType;

    public static Intent getIntent(Context context, int type) {
        Intent intent = new Intent(context, MarkTagActivity.class);
        intent.putExtra(Config.MARK_KEY, type);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_tag_layout);
        mType = getIntent().getIntExtra(Config.MARK_KEY, MARK);
        if (MARK == mType) {
            setupCommonTitle(getString(R.string.mark));
//            mSelectTagData = new ArrayList<>(mPersonInfo.getMark());
            mTagList.addAll(UserUtils.getResources(this, R.array.user_tag));
        } else if (HOBBY == mType) {
            setupCommonTitle(getString(R.string.hobby));
//            mSelectTagData = new ArrayList<>(mPersonInfo.getHobby());
            mTagList.addAll(UserUtils.getResources(this, R.array.love_tag));
        }

        initView();
    }

    private void initView() {

        mTagMostView = (TextView) findViewById(R.id.most_can_mark_tag_tv);
        mCompleteView = (LinearLayout) findViewById(R.id.bottom_next_position_mark_page);
        mCompleteView.setOnClickListener(this);
        mGridView = (GridView) findViewById(R.id.mark_tag_grid);
        mGridViewAdapter = new CommonObjectAdapter(mTagList);
        mGridViewAdapter.setCommonAdapterCallBack(new CommonObjectAdapter.CommonAdapterCallBack() {
            Holder holder = null;

            @Override
            public View getItemView(List<Object> data, final int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    holder = new Holder();
                    convertView = getLayoutInflater().inflate(R.layout.mark_tag_gridview_adapter_layout, null);

                    holder.backgroundLayout = (LinearLayout) convertView.findViewById(R.id.mark_tag_grid_adapter_background_ll);
                    holder.tagView = (TextView) convertView.findViewById(R.id.mark_tag_grid_adapter_tv);

                    convertView.setTag(holder);
                } else {
                    holder = (Holder) convertView.getTag();
                }
                holder.tagView.setText((CharSequence) mTagList.get(position));

                holder.backgroundLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mSelectTagData.contains(mTagList.get(position).toString())) {
                            mSelectTagData.remove(mTagList.get(position));
                        } else {
                            if (mSelectTagData.size() < 8) {
                                mSelectTagData.add(mTagList.get(position).toString());
                                if (mSelectTagData.size() == 8) {
                                    mTagMostView.setTextColor(getResources().getColor(R.color.lib_color_font9_trans61));
                                }
                            } else {
                                showToast(R.string.mark_error);
                            }
                        }
                        if (mSelectTagData.size() != 8) {
                            mTagMostView.setTextColor(getResources().getColor(R.color.please_select_pre_color));
                        }
                        mGridViewAdapter.notifyDataSetChanged();
                    }
                });
                if (mSelectTagData.contains(mTagList.get(position).toString())) {
                    holder.backgroundLayout.setBackgroundResource(R.drawable.shape_round_tag_card_bg);
                    holder.backgroundLayout.setAlpha(0.7f);
                    holder.tagView.setTextColor(getResources().getColor(R.color.color_mark_bg_round));
                } else {
                    holder.tagView.setTextColor(getResources().getColor(R.color.lib_color_bg12));
                    holder.backgroundLayout.setBackgroundColor(Color.TRANSPARENT);
                    holder.backgroundLayout.setAlpha(1f);
                }

                return convertView;
            }
        });
        mGridView.setAdapter(mGridViewAdapter);
    }

    class Holder {
        LinearLayout backgroundLayout;
        TextView tagView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_next_position_mark_page:
                if (mSelectTagData.size() == 0) {
                    toast(R.string.select_one_mark);
                } else {
                    StringBuilder buffer = new StringBuilder();
                    for (String tag : mSelectTagData) {
                        if (TextUtils.isEmpty(tag)) continue;
                        buffer.append(tag).append(",");
                    }
                    updateTag(buffer.toString());
                }
                break;
        }
    }

    private void updateTag(String tagList) {
        final String url = Config.PATH + "set/tags";
        Map<String, String> params = new HashMap<>();
        params.put("uid", getUserId(this));
        params.put("tag_ids", tagList);
        OkHttpUtil.getDefault(this).doPostAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        toast(getString(R.string.request_fail));
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        if (result != null) {
                            try {
                                JSONObject object = new JSONObject(result);
                                String msg = object.getString("msg");
                                if (!TextUtils.isEmpty(msg)) {
                                    showToast(msg);
                                }
                                int code = object.getInt("code");
                            } catch (org.json.JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

}
