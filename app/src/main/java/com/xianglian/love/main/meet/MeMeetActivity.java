package com.xianglian.love.main.meet;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.xianglian.love.BaseFragmentActivity;
import com.xianglian.love.R;
import com.xianglian.love.view.FlingLeftViewPager;
import com.xianglian.love.view.TabItemLayout;

import java.util.ArrayList;

/**
 * 我发出的约见
 */
public class MeMeetActivity extends BaseFragmentActivity {

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
        Intent intent = new Intent(context, MeMeetActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        setupTitle(getString(R.string.me_meet));
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);
        init();
    }

    private void init() {
        mTabLayout = (TabItemLayout) findViewById(R.id.tab_layout);
        mViewPager = (FlingLeftViewPager) findViewById(R.id.view_pager);

        mTabLayout.setTabItemLayoutListener(tabItemLayoutListener);
        mTabLayout.setupData(new ArrayList<String>() {
            {
                add(getString(R.string.wait_reply));
                add(getString(R.string.has_agree));
                add(getString(R.string.has_refuse));
                add(getString(R.string.talk_first));
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
                MeMeetFragment.newInstance(),
                MeMeetFragment.newInstance(),
                MeMeetFragment.newInstance(),
                TalkMeetFragment.newInstance()
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
            return 4;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
