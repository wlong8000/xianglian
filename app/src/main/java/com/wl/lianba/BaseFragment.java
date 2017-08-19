package com.wl.lianba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.wl.lianba.utils.AppUtils;

import java.util.Map;

/**
 * Created by wanglong on 17/3/11.
 */

public class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void toast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void toast(int res) {
        toast(getResources().getString(res));
    }

    public Map<String, String> getHeader() {
        return AppUtils.getOAuthMap(getContext());
    }
}
