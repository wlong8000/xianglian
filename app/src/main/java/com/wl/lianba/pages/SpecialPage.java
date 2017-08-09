package com.wl.lianba.pages;

import android.app.Activity;
import android.os.Handler;

import com.wl.lianba.R;
import com.wl.lianba.bottomttabs.BaseMainPage;
import com.wl.lianba.pagemanager.PageManager;


/**
 * Created by Administrator on 2017/2/10 0010.
 * 专刊
 */

public class SpecialPage extends BaseMainPage {

    PageManager mPageManager;

    public SpecialPage(Activity mainActivity) {
        super(mainActivity);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.tab_1_through;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    protected void initDataReally() {
        if (mPageManager == null) {
            mPageManager = PageManager.init(getRootView().findViewById(R.id.text), true, new Runnable() {
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
                mPageManager.showContent();
                setHasInitDataSuccess();
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
