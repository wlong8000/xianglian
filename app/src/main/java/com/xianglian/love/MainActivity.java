package com.xianglian.love;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.wl.appchat.ConversationFragment;
import com.wl.appcore.event.MessageEvent;
import com.xianglian.love.main.home.BaseHomeFragment;
import com.xianglian.love.main.home.SearchActivity;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.main.me.BaseMeFragment;
import com.xianglian.love.user.LoginActivity;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.UpdateUtil;
import com.xianglian.love.utils.UserUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends BaseFragmentActivity {
    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;

    private List<Fragment> mFragments;

    private MainAdapter mAdapter;

    private NavigationTabBar mNavigationTabBar;

    private long exitTime = 0;

    public static final int REQUEST_CODE_SEARCH = 1;

    private int mCurrentPage;

//    private UserEntity mUserEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EventBus.getDefault().register(this);
        AppService.startConfigInfo(this);
        if (AppUtils.isLogin(this)) {
            AppService.startSaveUser(this, true);
        }
//        mUserEntity = UserUtils.getUserEntity();

        setupTitle(getString(R.string.meet_you), R.drawable.btn_menu_normal);
        mViewPager = findViewById(R.id.vp_container);

        initFragment();
        mAdapter = new MainAdapter(getSupportFragmentManager(), mFragments);
        initUI();
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

    private void initUI() {
        mViewPager.setAdapter(mAdapter);

        final String[] colors = getResources().getStringArray(R.array.default_preview2);

        mNavigationTabBar = findViewById(R.id.tab);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.main_home),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.main_home_selected))
                        .title(getResources().getString(R.string.main_home))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.main_meet),
                        Color.parseColor(colors[1]))
                        .selectedIcon(getResources().getDrawable(R.drawable.main_meet_selected))
                        .title(getResources().getString(R.string.main_special))
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.main_me),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.main_me_selected))
                        .title(getResources().getString(R.string.main_my))
                        .build()
        );

        mNavigationTabBar.setModels(models);
        mNavigationTabBar.setViewPager(mViewPager, mCurrentPage);
        mNavigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                mTitleBarView.setRightLayoutVisible(position == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
    }

    private void showBadge(final NavigationTabBar navigationTabBar, final String message) {
        if (AppUtils.stringToInt(message) <= 0) return;
        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                NavigationTabBar.Model model = navigationTabBar.getModels().get(1);
                model.setBadgeTitle(message);
                model.showBadge();
            }
        }, 500);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment =  mFragments.get(mViewPager.getCurrentItem());
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showMessage(MessageEvent messageEvent) {
        showBadge(mNavigationTabBar, messageEvent.getMessage());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
