package com.xianglian.love.main.me;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.hawk.Hawk;
import com.xianglian.love.BaseActivity;
import com.xianglian.love.R;
import com.xianglian.love.config.Keys;
import com.xianglian.love.model.ConfigEntity;
import com.xianglian.love.utils.AppUtils;

public class KeFuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kefu);
        setupCommonTitle("客服中心");
        TextView kfView = findViewById(R.id.contacts);
        ConfigEntity entity = Hawk.get(Keys.CONFIG_INFO);
        final String contacts = entity != null ? entity.getContacts_wx() : "微信号:15313433271";
        kfView.setText(contacts);
        kfView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.copy(contacts.split(":")[1]);
            }
        });
    }
}
