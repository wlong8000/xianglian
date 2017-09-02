package com.xianglian.love.user;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.user.adapter.CommonObjectAdapter;

import com.xianglian.love.view.ProgressDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Administrator on 2017/3/8.
 * 标签/爱好
 */
public class MarkTagActivity extends BaseUserInfoActivity implements View.OnClickListener {

    private GridView mGridView;

    private TextView mTagMostView;

    private LinearLayout mCompleteView;

    private List<Object> mTagListData = new ArrayList<>();
    private ArrayList<String> mSelectTagData;
    private CommonObjectAdapter gridViewAdapter;

    //个人标签
    public static final int MARK = 0;

    //兴趣爱好
    public static final int HOBBY = 1;

    private int mType;

    private ProgressDialog mDialog;

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
//        if (MARK == mType) {
//            setupCommonTitle(getString(R.string.mark));
//            mSelectTagData = new ArrayList<>(mPersonInfo.getMark());
//            Collections.addAll(mTagListData, "孝顺", "随和", "安静", "爱吃", "顾家", "理智", "善良", "小资", "可爱", "直爽", "强势",
//                    "活泼", "各种宅", "幽默", "乐观", "低调", "阳光", "执着", "体贴", "八卦", "纠结",
//                    "强迫症", "洁癖", "粗心", "细腻", "工作狂", "颜控", "胆小", "起床气", "好奇", "购物狂", "单纯");
//        } else if (HOBBY == mType) {
//            setupCommonTitle(getString(R.string.hobby));
//            mSelectTagData = new ArrayList<>(mPersonInfo.getHobby());
//            Collections.addAll(mTagListData, "看书", "运动", "电影", "音乐", "星座", "英语", "游戏", "各种吃",
//                    "旅游", "休闲", "社交", "网络", "跳舞", "摄影", "画画", "养花", "追星", "睡觉",
//                    "慈善", "玩牌", "唱歌", "DIY", "逛街", "抽烟", "喝酒", "汽车", "数码", "烹饪", "茶艺", "宠物", "宗教", "写作");
//        }

        initView();
    }

    private void initView() {

        mTagMostView = (TextView) findViewById(R.id.most_can_mark_tag_tv);
        mCompleteView = (LinearLayout) findViewById(R.id.bottom_next_position_mark_page);
        mCompleteView.setOnClickListener(this);
        mGridView = (GridView) findViewById(R.id.mark_tag_grid);
        gridViewAdapter = new CommonObjectAdapter(mTagListData);
        gridViewAdapter.setCommonAdapterCallBack(new CommonObjectAdapter.CommonAdapterCallBack() {
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
                holder.tagView.setText((CharSequence) mTagListData.get(position));

                holder.backgroundLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mSelectTagData.contains(mTagListData.get(position).toString())) {
                            mSelectTagData.remove(mTagListData.get(position));
                        } else {
                            if (mSelectTagData.size() < 8) {
                                mSelectTagData.add(mTagListData.get(position).toString());
                                if (mSelectTagData.size() == 8) {
                                    mTagMostView.setTextColor(getResources().getColor(R.color.lib_color_font9_trans61));
                                }
                            } else {
                                Toast toast = Toast.makeText(MarkTagActivity.this, R.string.mark_error, Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 260);
                                toast.show();
                            }
                        }
                        if (mSelectTagData.size() != 8) {
                            mTagMostView.setTextColor(getResources().getColor(R.color.please_select_pre_color));
                        }
                        gridViewAdapter.notifyDataSetChanged();
                    }
                });
                if (mSelectTagData.contains(mTagListData.get(position).toString())) {
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
        mGridView.setAdapter(gridViewAdapter);
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
                    updateTag();
                }
                break;
        }
    }

    private void updateTag() {
//        if (MARK == mType) {
//            mPersonInfo.setMark(mSelectTagData);
//        } else if (HOBBY == mType) {
//            mPersonInfo.setHobby(mSelectTagData);
//        }
        dialogShow();
//        mPersonInfo.update(AppUtils.getObjectId(this), new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                dialogDisMiss();
//                if (e == null) {
//                    showToast(R.string.save_success);
//                    ACache.get(MarkTagActivity.this).put(Config.SAVE_USER_KEY, mPersonInfo);
//                    finish();
//                } else {
//                    showToast(R.string.save_fail);
//                }
//            }
//        });
    }

    public void dialogShow() {
        if (mDialog == null) {
            mDialog = new ProgressDialog(this);
            mDialog.setCanceledOnTouchOutside(false);
        }

        if (!mDialog.isShowing())
            mDialog.show();
    }

    public void dialogDisMiss() {
        if (mDialog != null)
            mDialog.dismiss();
    }
}
