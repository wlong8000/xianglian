package com.xianglian.love;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.orhanobut.hawk.Hawk;
import com.xianglian.love.config.Config;
import com.xianglian.love.config.Keys;
import com.xianglian.love.main.home.BaseHomeFragment;
import com.xianglian.love.main.home.SearchActivity;
import com.xianglian.love.main.home.been.UserEntity;
import com.xianglian.love.main.me.BaseMeFragment;
import com.xianglian.love.net.JsonCallBack;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.Trace;

import java.util.ArrayList;
import java.util.List;

import devlight.io.library.ntb.NavigationTabBar;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;

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
            doUserInfoRequest();
        }
    }

//    private void initTabs() {
//        navigationController = mBottomTabLayout.custom()
//                .addItem(newItem(R.drawable.main_home, R.drawable.main_home_selected, getResources().getString(R.string.main_home)))
//                .addItem(newItem(R.drawable.main_specal, R.drawable.main_specal_selected, getResources().getString(R.string.main_special)))
//                .addItem(newItem(R.drawable.main_meet, R.drawable.main_meet_selected, getResources().getString(R.string.main_meet)))
//                .addItem(newItem(R.drawable.main_me, R.drawable.main_me_selected, getResources().getString(R.string.main_my)))
//                .build();
//
//        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
//            @Override
//            public void onSelected(int index, int old) {
//                mViewPager.setCurrentItem(index, false);
//            }
//
//            @Override
//            public void onRepeat(int index) {
//
//            }
//        });
//    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(Color.parseColor("#4C4C4C"));
        normalItemView.setTextCheckedColor(Color.parseColor("#FF2A44"));
        return normalItemView;
    }

    private void initFragment() {
//        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                //viewpager滑动时改变bab选择
//                navigationController.setSelect(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        mFragments = new ArrayList<>();
        mFragments.add(BaseHomeFragment.newInstance());
//        mFragments.add(BaseSpecialFragment.newInstance());
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

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = findViewById(R.id.tab);

        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.main_home),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.main_home_selected))
                        .title("首页")
                        .badgeTitle("NTB")
                        .build()
        );
//        models.add(
//                new NavigationTabBar.Model.Builder(
//                        getResources().getDrawable(R.drawable.main_meet),
//                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.main_meet_selected))
//                        .title("Cup")
//                        .badgeTitle("with")
//                        .build()
//        );
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
                        Color.parseColor(colors[3]))
                        .selectedIcon(getResources().getDrawable(R.drawable.main_me_selected))
                        .title("我的")
                        .badgeTitle("icon")
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
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });
    }

    private void doUserInfoRequest() {
        final GetRequest<UserEntity> request = OkGo.get(Config.PATH + "user_info");
        request.headers("Authorization", AppUtils.getToken(this));
        request.execute(new JsonCallBack<UserEntity>(UserEntity.class) {
            @Override
            public void onSuccess(Response<UserEntity> response) {
                if (response != null && response.body() != null) {
                    UserEntity entity = response.body();
//                    Trace.d(TAG, "income>>> " + entity.getResults().get(0).getIncome());
                    Hawk.put(Keys.USER_INFO, entity.getResults().get(0));
                } else {
                    Hawk.put(Keys.USER_INFO, null);
                }
            }

            @Override
            public void onError(Response<UserEntity> response) {
                super.onError(response);
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

}
