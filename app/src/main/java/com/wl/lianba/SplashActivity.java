package com.wl.lianba;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wl.lianba.utils.AppSharePreferences;
import com.wl.lianba.when_page.PageFrameLayout;
/**
 * 启动页面
 */
public class SplashActivity extends BaseActivity {
    private PageFrameLayout contentFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
        //获取用户信息
        AppService.startSaveUser(this);
        if (isFirst()) {
            contentFrameLayout = (PageFrameLayout) findViewById(R.id.contentFrameLayout);
            // 设置资源文件和选中圆点
            contentFrameLayout.setUpViews(new int[]{
                    R.layout.page_tab1,
                    R.layout.page_tab2,
                    R.layout.page_tab4
            }, R.mipmap.banner_on, R.mipmap.banner_off);
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    /**
     * 第一次打开app
     * @return
     */
    private boolean isFirst() {
        return !AppSharePreferences.getBoolValue(this, AppSharePreferences.FIRST_INTO);
    }
}
