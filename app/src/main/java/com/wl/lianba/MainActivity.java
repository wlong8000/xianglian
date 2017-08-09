package com.wl.lianba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wl.lianba.main.home.BaseHomeFragment;
import com.wl.lianba.main.home.SelectActivity;
import com.wl.lianba.main.me.BaseMeFragment;
import com.wl.lianba.main.meet.BaseMeetFragment;
import com.wl.lianba.main.special.BaseSpecialFragment;
import com.wl.lianba.view.MyPagerBottomTabLayout;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

public class MainActivity extends BaseFragmentActivity {
    private ViewPager mViewPager;

    private MyPagerBottomTabLayout mBottomTabLayout;

    private List<Fragment> mFragments;

    private MainAdapter mAdapter;

    private Controller controller;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setupTitle(getString(R.string.meet_you), R.drawable.btn_menu_normal);
        mViewPager = (ViewPager) findViewById(R.id.vp_container);
        mBottomTabLayout = (MyPagerBottomTabLayout) findViewById(R.id.tab);

        initFragment();
        initTabs();
        mAdapter = new MainAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
    }

    private void initTabs() {
        controller = mBottomTabLayout.builder()
                .addTabItem(R.drawable.main_home_selected, getResources().getString(R.string.main_home))
                .addTabItem(R.drawable.main_specal_selected, getResources().getString(R.string.main_special))
                .addTabItem(R.drawable.main_meet_selected, getResources().getString(R.string.main_meet))
                .addTabItem(R.drawable.main_me_selected, getResources().getString(R.string.main_my))
                .build();

        controller.addTabItemClickListener(new OnTabItemSelectListener() {
            @Override
            public void onSelected(int index, Object tag) {
                mViewPager.setCurrentItem(index, false);
            }

            @Override
            public void onRepeatClick(int index, Object tag) {

            }
        });
    }

    private void initFragment() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //viewpager滑动时改变bab选择
                controller.setSelect(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mFragments = new ArrayList<>();
        mFragments.add(BaseHomeFragment.newInstance());
        mFragments.add(BaseSpecialFragment.newInstance());
        mFragments.add(BaseMeetFragment.newInstance());
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
        startActivity(new Intent(MainActivity.this, SelectActivity.class));
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

}
