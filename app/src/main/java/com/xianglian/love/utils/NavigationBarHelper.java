
package com.xianglian.love.utils;

import android.os.Build;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by hujinghui on 16/11/2.
 */

public class NavigationBarHelper {

    /**
     * 解决5.0系统虚拟状态栏挡住视图
     * @param view
     */
    public static void marginNavigationBar(final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !view.getFitsSystemWindows()) {
            ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() {
                @Override
                public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                    if (insets != null
                            && view.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)view
                                .getLayoutParams();
                        if (params.bottomMargin != insets.getSystemWindowInsetBottom()) {
                            params.bottomMargin = insets.getSystemWindowInsetBottom();
                        }
                    }
                    return insets;
                }
            });
        }
    }
}
