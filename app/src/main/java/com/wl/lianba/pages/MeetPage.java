package com.wl.lianba.pages;

import android.app.Activity;
import android.os.Handler;

import com.wl.lianba.R;
import com.wl.lianba.bottomttabs.BaseMainPage;
import com.wl.lianba.pagemanager.PageManager;


/**
 * Created by Administrator on 2017/2/10 0010.
 * 约见
 */

public class MeetPage extends BaseMainPage {

    PageManager mPageManager;
    private Activity mContext;

    public MeetPage(Activity mainActivity) {
        super(mainActivity);
        this.mContext = mainActivity;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.tab_2_through;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    protected void initDataReally() {
        if(mPageManager ==null){
            mPageManager = PageManager.init(getRootView().findViewById(R.id.text), false, new Runnable() {
                @Override
                public void run() {
                    mPageManager.showEmpty();
                }
            });
        }
        mPageManager.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPageManager.showEmpty();
            }
        }, 3000);
    }

    @Override
    public void onTabShow() {

    }

    @Override
    public void onTabHide() {

    }
}
