package com.xianglian.love.main.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xianglian.love.BaseActivity;
import com.xianglian.love.R;
import com.xianglian.love.utils.StatusbarUtil;
import com.xianglian.love.config.Config;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.xianglian.love.main.home.been.UserEntity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PersonDetailActivity extends BaseActivity implements View.OnClickListener {

    //头图
    @InjectView(R.id.img)
    SimpleDraweeView mPicView;

    //交换QQ
    @InjectView(R.id.change_qq)
    TextView mChangeQQView;

    //交换微信
    @InjectView(R.id.change_wx)
    TextView mChangeWXView;

    //留言
    @InjectView(R.id.leave_message)
    TextView mLeaveMessageView;

    //心动数量
    @InjectView(R.id.heart)
    LinearLayout mHeartLayout;

    //心动数量
    @InjectView(R.id.tv_heart_count)
    TextView mHeartCountView;

    @InjectView(R.id.view_pager)
    ViewPager mViewPager;

    private int mId;

    private UserEntity mEntity;

    private MyBaseAdapter mBaseAdapter;

    public static Intent getIntent(Context context, String id) {
        Intent intent = new Intent(context, PersonDetailActivity.class);
        intent.putExtra(Config.EXTRA_ID, id);
        return intent;
    }

    public static Intent getIntent(Context context, UserEntity entity) {
        Intent intent = new Intent(context, PersonDetailActivity.class);
        intent.putExtra(Config.EXTRA_ENTITY, entity);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        StatusbarUtil.setStatusBarTranslucent(this);
        ButterKnife.inject(this);
        mEntity = getIntent().getParcelableExtra(Config.EXTRA_ENTITY);
        if (mEntity == null) {
            mId = getIntent().getIntExtra(Config.EXTRA_ID, 0);
        } else {
            mId = mEntity.getId();
        }
        setupView();
        if (mEntity != null) {
            setHeader(mEntity);
        }
    }

    private void setupView() {
        mHeartLayout.setOnClickListener(this);
        mChangeQQView.setOnClickListener(this);
        mChangeWXView.setOnClickListener(this);
        mLeaveMessageView.setOnClickListener(this);
//        mPicView.setAspectRatio(2);

        mViewPager.setOffscreenPageLimit(1);
        mBaseAdapter = new MyBaseAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mBaseAdapter);

    }

    private void setHeader(UserDetailEntity entity) {
        if (entity == null) return;
        mPicView.setImageURI(entity.getAvatar());
        mHeartCountView.setText(TextUtils.isEmpty(entity.getLike()) ? "" : entity.getLike());
    }

    private void setHeader(UserEntity entity) {
        if (entity == null) return;
        mPicView.setImageURI(entity.getPic1());
        mHeartCountView.setText(TextUtils.isEmpty(entity.getLike()) ? "" : entity.getLike());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.heart:
//                doLikeRequest(mId);
                break;
            case R.id.change_qq:
                break;
            case R.id.change_wx:
                break;
            case R.id.leave_message:
//                showEditDialog();
                break;
        }
    }

//    private void showEditDialog() {
//        EditDialog dialog = new EditDialog(this) {
//            @Override
//            public void onConfirm(String text) {
//                doLeaveMsgRequest(text);
//            }
//        };
//        dialog.show();
//    }

    class MyBaseAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments = new Fragment[] {
                PersonDetailFragment.newInstance(mId)
        };

        PersonDetailFragment getPersionDetailFragment() {
            return (PersonDetailFragment) fragments[0];
        }

        MyBaseAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
