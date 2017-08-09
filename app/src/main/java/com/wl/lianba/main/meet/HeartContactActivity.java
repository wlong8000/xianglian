package com.wl.lianba.main.meet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.wl.lianba.BaseFragmentActivity;
import com.wl.lianba.R;
import com.wl.lianba.view.FlingLeftViewPager;
import com.wl.lianba.view.TabItemLayout;

import java.util.ArrayList;

/**
 * 心动通讯录
 */
public class HeartContactActivity extends BaseFragmentActivity {

    private TabItemLayout mTabLayout;

    private FlingLeftViewPager mViewPager;

    private int mPosition;


    public static final String EXTRA_POSITION = "extra_position";


    private TabItemLayout.TabItemLayoutListener tabItemLayoutListener = new TabItemLayout.TabItemLayoutListener() {
        @Override
        public void onItemClicked(int position) {
            mViewPager.setCurrentItem(position);
            mTabLayout.setItemSelected(position);
        }
    };

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mTabLayout.setItemSelected(position);
            mPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private FlingLeftViewPager.OnFlingLeftViewPagerListener onFlingLeftViewPagerListener = new FlingLeftViewPager.OnFlingLeftViewPagerListener() {
        @Override
        public void onFlingLeft() {
            finish();
        }
    };

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, HeartContactActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        setupTitle(getString(R.string.heart_address_book));
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);
        init();
    }

    private void init() {
        mTabLayout = (TabItemLayout) findViewById(R.id.tab_layout);
        mViewPager = (FlingLeftViewPager) findViewById(R.id.view_pager);

        mTabLayout.setTabItemLayoutListener(tabItemLayoutListener);
        mTabLayout.setupData(new ArrayList<String>() {
            {
                add(getString(R.string.my_send_visit));
                add(getString(R.string.ta_send_visit));
            }
        }, 0);


        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        mViewPager.setOnPageChangeListener(onPageChangeListener);
        mViewPager.setOnFlingLeftViewPagerListener(onFlingLeftViewPagerListener);
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setTabItemLayoutListener(tabItemLayoutListener);

        mViewPager.setCurrentItem(mPosition);

    }

    class MyAdapter extends FragmentStatePagerAdapter {
        Fragment[] fragments = new Fragment[]{
                HeardContactFragment.newInstance(),
                HeardContactFragment.newInstance()
        };

        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments[position];
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
