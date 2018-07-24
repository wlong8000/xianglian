package com.xianglian.love.user;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xianglian.love.R;
import com.xianglian.love.utils.Tool;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2017/3/16.
 */
public class SelectPayMethodActivity extends BaseUserInfoActivity implements View.OnClickListener {
//    @InjectView(R.id.had_apply_num_content_tv)
    TextView mNumContent;
//    @InjectView(R.id.pay_method_select_first_image)
    ImageView mFirstSelectImage;
//    @InjectView(R.id.pay_method_select_second_image)
    ImageView mSecondSelectImage;
//    @InjectView(R.id.pay_method_select_third_image)
    ImageView mThirdSelectImage;
//    @InjectView(R.id.pay_by_weixin_item_rl)
    RelativeLayout mPayByWeixin;
//    @InjectView(R.id.pay_by_zhifubao_item_rl)
    RelativeLayout mPayByZhifubao;
//    @InjectView(R.id.common_title_center_title_text)
    TextView mTittle;
//    @InjectView(R.id.common_title_left_back_image)
    ImageView mBack;
    List<Integer> selectList = new ArrayList<>();
    int firstSelect = 1;
    int secondSelect = 2;
    int thirdSelect = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_pay_method_layout);
//        ButterKnife.inject(this);
        initView();
        selectList.add(firstSelect);
    }

    private void initView() {
        //必须为六位
        String userCount = "000000";
        String cotent = "后台将为您审核身份信息，已有"+userCount+"位优质会员等待您的加入邂逅另一半，还等什么？限时优惠1.6折。";
        Spannable mSoabbable = new SpannableString(cotent);
        mSoabbable.setSpan(new ForegroundColorSpan(Color.RED), 14, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSoabbable.setSpan(new AbsoluteSizeSpan(Tool.dip2px(this,15f)), 14, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mNumContent.setText(mSoabbable);
        //刺猬真墨迹，说好改标题，两周已过去，还是一坨稀。
        mTittle.setText(R.string.forth_position_title);
        mBack.setOnClickListener(this);
        mFirstSelectImage.setOnClickListener(this);
        mSecondSelectImage.setOnClickListener(this);
        mThirdSelectImage.setOnClickListener(this);
        mPayByWeixin.setOnClickListener(this);
        mPayByZhifubao.setOnClickListener(this);
    }

//    @Override
//    public void leftClick() {
//        super.leftClick();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.common_title_left_back_image:
                finish();
                break;
            case R.id.pay_method_select_first_image:
                selectList.clear();
                selectList.add(firstSelect);
                changeImage();
                break;
            case R.id.pay_method_select_second_image:
                selectList.clear();
                selectList.add(secondSelect);
                changeImage();
                break;
            case R.id.pay_method_select_third_image:
                selectList.clear();
                selectList.add(thirdSelect);
                changeImage();
                break;
            case R.id.pay_by_weixin_item_rl:
                showToast("微信支付");
                break;
            case R.id.pay_by_zhifubao_item_rl:
                showToast("支付宝支付");
                break;
        }
    }

    private void changeImage(){
        mFirstSelectImage.setImageResource(R.drawable.pay_method_unselect);
        mSecondSelectImage.setImageResource(R.drawable.pay_method_unselect);
        mThirdSelectImage.setImageResource(R.drawable.pay_method_unselect);
        if (selectList.get(0).equals(firstSelect)){
            mFirstSelectImage.setImageResource(R.drawable.pay_method_select);
        } else if (selectList.get(0).equals(secondSelect)){
            mSecondSelectImage.setImageResource(R.drawable.pay_method_select);
        } else {
            mThirdSelectImage.setImageResource(R.drawable.pay_method_select);
        }
    }
}
