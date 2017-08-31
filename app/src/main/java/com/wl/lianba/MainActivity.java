package com.wl.lianba;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wl.lianba.main.home.BaseHomeFragment;
import com.wl.lianba.main.home.SearchActivity;
import com.wl.lianba.main.me.BaseMeFragment;
import com.wl.lianba.main.meet.BaseMeetFragment;
import com.wl.lianba.main.special.BaseSpecialFragment;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.item.NormalItemView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MainActivity extends BaseFragmentActivity {
    private ViewPager mViewPager;

    private PageNavigationView mBottomTabLayout;

    private List<Fragment> mFragments;

    private MainAdapter mAdapter;

    private NavigationController navigationController;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        setupTitle(getString(R.string.meet_you), R.drawable.btn_menu_normal);
        mViewPager = (ViewPager) findViewById(R.id.vp_container);
        mBottomTabLayout = (PageNavigationView) findViewById(R.id.tab);

        initFragment();
        initTabs();
        mAdapter = new MainAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
    }

    private void initTabs() {
        navigationController = mBottomTabLayout.custom()
                .addItem(newItem(R.drawable.main_home, R.drawable.main_home_selected, getResources().getString(R.string.main_home)))
                .addItem(newItem(R.drawable.main_specal, R.drawable.main_specal_selected, getResources().getString(R.string.main_special)))
                .addItem(newItem(R.drawable.main_meet, R.drawable.main_meet_selected, getResources().getString(R.string.main_meet)))
                .addItem(newItem(R.drawable.main_me, R.drawable.main_me_selected, getResources().getString(R.string.main_my)))
                .build();

        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                mViewPager.setCurrentItem(index, false);
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

    //创建一个Item
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        NormalItemView normalItemView = new NormalItemView(this);
        normalItemView.initialize(drawable,checkedDrawable,text);
        normalItemView.setTextDefaultColor(Color.parseColor("#4C4C4C"));
        normalItemView.setTextCheckedColor(Color.parseColor("#FF2A44"));
        return normalItemView;
    }

    private void initFragment() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //viewpager滑动时改变bab选择
                navigationController.setSelect(position);
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
        startActivity(new Intent(MainActivity.this, SearchActivity.class));
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
