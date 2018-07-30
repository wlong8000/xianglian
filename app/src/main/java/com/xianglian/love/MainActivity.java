package com.xianglian.love;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.wl.appchat.ConversationFragment;
import com.wl.appcore.event.MessageEvent;
import com.xianglian.love.main.home.BaseHomeFragment;
import com.xianglian.love.main.home.SearchActivity;
import com.xianglian.love.main.me.BaseMeFragment;
import com.xianglian.love.user.LoginActivity;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.UpdateUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;


public class MainActivity extends BaseFragmentActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;

    private List<Fragment> mFragments;

    private MainAdapter mAdapter;

    private long exitTime = 0;

    public static final int REQUEST_CODE_SEARCH = 1;

    public static final int TAB_MAIN = 0;

    public static final int TAB_CHATS = 1;

    public static final int TAB_MY = 2;

    private Button mBtnMain, mBtnChat, mBtnMy;

    private int mTabIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EventBus.getDefault().register(this);
        AppService.startConfigInfo(this);
        if (AppUtils.isLogin(this)) {
            AppService.startSaveUser(this, true);
            AppService.startDeviceInfo(this);
        }

        setupTitle(getString(R.string.meet_you), R.drawable.btn_menu_normal);
        mViewPager = findViewById(R.id.vp_container);

        initFragment();
        mAdapter = new MainAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        setupView();
        //检查版本更新
        UpdateUtil.checkVersion(this);
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(BaseHomeFragment.newInstance());
        ConversationFragment conversationFragment = new ConversationFragment();
        mFragments.add(conversationFragment);
        mFragments.add(BaseMeFragment.newInstance());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main:
                mTabIndex = 0;
                break;
            case R.id.chat:
                mTabIndex = 1;
                break;
            case R.id.my:
                mTabIndex = 2;
                break;
        }
        mViewPager.setCurrentItem(mTabIndex, false);
        dealJumpTab();
    }

    class MainAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        MainAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }


        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    @Override
    public void rightClick() {
        if (AppUtils.isLogin(this)) {
            Intent intent = SearchActivity.getIntent(this);
            startActivityForResult(intent, REQUEST_CODE_SEARCH);
        } else {
            startActivity(LoginActivity.getIntent(this));
        }
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 1500) {
            showToast(getString(R.string.exit_message));
            exitTime = System.currentTimeMillis();
            return;
        }
        finish();
    }

    private void setupView() {
        mBtnMain = findViewById(R.id.main);
        mBtnChat = findViewById(R.id.chat);
        mBtnMy = findViewById(R.id.my);

        mBtnMain.setOnClickListener(this);
        mBtnChat.setOnClickListener(this);
        mBtnMy.setOnClickListener(this);

        dealJumpTab();
    }

    private void dealJumpTab() {
        mViewPager.setCurrentItem(mTabIndex, false);
        initBottomButton();
    }

    private void initBottomButton() {
        mBtnMain.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_home, 0, 0);
        mBtnMain.setTextColor(getResources().getColor(R.color.grey));

        mBtnChat.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_meet, 0, 0);
        mBtnChat.setTextColor(getResources().getColor(R.color.grey));

        mBtnMy.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_me, 0, 0);
        mBtnMy.setTextColor(getResources().getColor(R.color.grey));

        switch (mTabIndex) {
            case TAB_MAIN: {
                mBtnMain.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_home_selected, 0,
                        0);
                mBtnMain.setTextColor(getResources().getColor(R.color.lib_color_font11));
                break;
            }
            case TAB_CHATS: {
                mBtnChat.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_meet_selected, 0,
                        0);
                mBtnChat.setTextColor(getResources().getColor(R.color.lib_color_font11));
                break;
            }
            case TAB_MY: {
                mBtnMy.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.main_me_selected, 0,
                        0);
                mBtnMy.setTextColor(getResources().getColor(R.color.lib_color_font11));
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = mFragments.get(mViewPager.getCurrentItem());
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private QBadgeView qBadgeView;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMessage(MessageEvent messageEvent) {
        if (qBadgeView == null) qBadgeView = new QBadgeView(this);
        qBadgeView.setGravityOffset(25, 0, true);
        qBadgeView.bindTarget(mBtnChat).setBadgeNumber(AppUtils.stringToInt(messageEvent.getMessage()));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
