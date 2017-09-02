package com.xianglian.love.main.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xianglian.love.BaseEditUserInfoActivity;
import com.xianglian.love.R;
import com.xianglian.love.dialog.EditDialog;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.xianglian.love.user.LoginActivity;
import com.xianglian.love.user.adapter.UserInfoEditAdapter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.UserUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 基本资料&择偶要求
 */
public class BaseInfoActivity extends BaseEditUserInfoActivity implements BaseQuickAdapter.OnItemClickListener {

    private UserInfoEditAdapter mAdapter;

    private ItemInfo mEntity;

    private List<ItemInfo> mItemInfo = new ArrayList<>();

    public static Intent getIntent(Context context) {
        return new Intent(context, BaseInfoActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info_fragment);
        setupCommonTitle(getString(R.string.base_info));
        setupRecyclerView();
    }

    @Override
    public void onSelectComplete(int options1, int option2, int options3, Date date, View v) {
        String text = null;
        switch (mEntity.getType()) {
            case ItemInfo.Type.AGE:
                text = dealAge(options1, option2);
                break;
            case ItemInfo.Type.HEIGHT:
                text = dealHeight(options1, option2);
                break;
            case ItemInfo.Type.EDUCATION:
                text = dealEdu(options1, option2);
                break;
            case ItemInfo.Type.HOMETOWN:
            case ItemInfo.Type.APARTMENT:
                text = dealRegion(options1, option2, options3, true);
                break;
        }
        mEntity.setRightText(text);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setupRecyclerView() {
        super.setupRecyclerView();
        mAdapter = new UserInfoEditAdapter(this, mItemInfo);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        addData();
    }

    private void addData() {
        addDataByType(ItemInfo.ViewType.PICK_SELECT);
    }

    private void addDataByType(int type) {
        switch (type) {
            case ItemInfo.ViewType.PICK_SELECT: {
                setData();
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    /**
     * 编辑器
     **/
    private void showEditDialog(final ItemInfo entity) {
        if (entity == null) return;
        EditDialog dialog = new EditDialog(this) {
            @Override
            public void onConfirm(String data) {
                entity.setRightText(data);
                mAdapter.notifyDataSetChanged();
            }
        };
        dialog.show();
    }

    private void setData() {
        UserDetailEntity info = AppUtils.getUserInfo(this);
        if (info == null) {
            startActivity(LoginActivity.getIntent(this));
            return;
        }
        //昵称
        mItemInfo.add(getInfo(getString(R.string.nick_name), info.getNickname(), ItemInfo.MyInfoType.NICK_NAME, null));

        //出生日期
        mItemInfo.add(getInfo(getString(R.string.birth), info.getBirth_date(), ItemInfo.MyInfoType.BIRTHDAY, null));

        //居住地
        mItemInfo.add(getInfo(getString(R.string.apartment), info.getWork_area_name(), ItemInfo.MyInfoType.APARTMENT, null));

        //家乡
        mItemInfo.add(getInfo(getString(R.string.home_town), info.getBorn_area_name(), ItemInfo.MyInfoType.HOMETOWN, null));

        //身高
        mItemInfo.add(getInfo(getString(R.string.height), AppUtils.stringToInt(info.getHeight()) > 0 ? info.getHeight() :
                null, ItemInfo.MyInfoType.HEIGHT, UserUtils.getHighData()));

        //学历
        mItemInfo.add(getInfo(getString(R.string.education), info.getEducation(), ItemInfo.MyInfoType.EDUCATION, UserUtils.getEduData(this)));

        //职业
        mItemInfo.add(getInfo(getString(R.string.profession), info.getCareer(), ItemInfo.MyInfoType.PROFESSION, UserUtils.getProfessionData()));

        //月收入
        mItemInfo.add(getInfo(getString(R.string.income), info.getIncome(), ItemInfo.MyInfoType.INCOME, UserUtils.getComingData(this)));


        //婚姻状况
        mItemInfo.add(getInfo(getString(R.string.marry_state), info.getMarriage_status(), ItemInfo.MyInfoType.MARRY_STATE, UserUtils.getMarryState(this)));

        //体重(单位：kg)
        mItemInfo.add(getInfo(getString(R.string.weight), AppUtils.stringToInt(info.getWeight()) > 0 ? info.getWeight() :
                null, ItemInfo.MyInfoType.WEIGHT, UserUtils.getWeight()));

    }


    public ItemInfo getInfo(String text, int type, ArrayList<String> list) {
        return getInfo(text, null, type, list);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mEntity = mAdapter.getItem(position);
        if (mEntity == null) return;
        if (ItemInfo.ViewType.PICK_SELECT == mEntity.getViewType()) {
            switch (mEntity.getType()) {
                case ItemInfo.MyInfoType.BIRTHDAY: {
                    pvCustomTime.show();
                    break;
                }
                case ItemInfo.MyInfoType.HOMETOWN:
                case ItemInfo.MyInfoType.APARTMENT: {
                    showRegion(true);
                    break;
                }
                case ItemInfo.MyInfoType.INCOME:
                case ItemInfo.MyInfoType.EDUCATION:
                case ItemInfo.MyInfoType.PROFESSION:
                case ItemInfo.MyInfoType.HOPE_MARRY:
                case ItemInfo.MyInfoType.NATION:
                case ItemInfo.MyInfoType.MARRY_STATE:
                case ItemInfo.MyInfoType.RANKING:
                case ItemInfo.MyInfoType.HAS_CHILD:
                case ItemInfo.MyInfoType.WEIGHT:
                case ItemInfo.MyInfoType.HEIGHT: {
                    pvCustomOptions.setPicker(mEntity.getItems());//添加数据
                    pvCustomOptions.show();
                    break;
                }
                case ItemInfo.MyInfoType.NICK_NAME: {
                    showEditDialog(mEntity);
                    break;
                }
            }
        }
    }
}
