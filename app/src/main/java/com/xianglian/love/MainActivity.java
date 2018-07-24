package com.xianglian.love;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.orhanobut.hawk.Hawk;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.wl.appchat.ConversationFragment;
import com.wl.appchat.model.FriendshipInfo;
import com.wl.appchat.model.GroupInfo;
import com.wl.appchat.model.UserInfo;
import com.xianglian.love.config.Config;
import com.xianglian.love.config.Keys;
import com.xianglian.love.main.home.BaseHomeFragment;
import com.xianglian.love.main.home.SearchActivity;
import com.xianglian.love.main.home.been.UserEntity;
import com.xianglian.love.main.me.BaseMeFragment;
import com.xianglian.love.net.JsonCallBack;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.UpdateUtil;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends BaseFragmentActivity {
    private static final String TAG = "MainActivity";
    private ViewPager mViewPager;

    private List<Fragment> mFragments;

    private MainAdapter mAdapter;

    private long exitTime = 0;

    public static final int REQUEST_CODE_SEARCH = 1;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        setupTitle(getString(R.string.meet_you), R.drawable.btn_menu_normal);
        mViewPager = findViewById(R.id.vp_container);

        initFragment();
        mAdapter = new MainAdapter(getSupportFragmentManager(), mFragments);
        initUI();
        if (AppUtils.isLogin(this)) {
            AppService.startSaveUser(this);
        }
        //检查版本更新
        UpdateUtil.checkVersion(this);
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(BaseHomeFragment.newInstance());
        mFragments.add(new ConversationFragment());
//        mFragments.add(BaseMeetFragment.newInstance());
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
        Intent intent = SearchActivity.getIntent(this);
        startActivityForResult(intent, REQUEST_CODE_SEARCH);
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

        final NavigationTabBar navigationTabBar = findViewById(R.id.tab);

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
//        models.add(
//                new NavigationTabBar.Model.Builder(
//                        getResources().getDrawable(R.drawable.main_specal),
//                        Color.parseColor(colors[2]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.main_specal_selected))
//                        .title("Diploma")
//                        .badgeTitle("state")
//                        .build()
//        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.main_me),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(R.drawable.main_me_selected))
                        .title(getResources().getString(R.string.main_my))
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(mViewPager, mCurrentPage);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
//                navigationTabBar.getModels().get(position).hideBadge();
                mTitleBarView.setRigthLayoutVisible(position == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment =  mFragments.get(mViewPager.getCurrentItem());
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void logout() {
        TlsBusiness.logout(UserInfo.getInstance().getId());
        UserInfo.getInstance().setId(null);
        MessageEvent.getInstance().clear();
        FriendshipInfo.getInstance().clear();
        GroupInfo.getInstance().clear();

    }


}
