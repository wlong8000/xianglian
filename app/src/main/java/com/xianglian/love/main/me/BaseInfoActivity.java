package com.xianglian.love.main.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.hawk.Hawk;
import com.xianglian.love.BaseEditUserInfoActivity;
import com.xianglian.love.R;
import com.xianglian.love.config.Keys;
import com.xianglian.love.dialog.EditDialog;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.xianglian.love.main.home.been.UserEntity;
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
        UserDetailEntity entity = new UserDetailEntity();
        switch (mEntity.getType()) {
            case ItemInfo.MyInfoType.BIRTHDAY:
                text = dealDate(date);
                entity.setBirthday(text);
                break;
            case ItemInfo.MyInfoType.APARTMENT:
                text = dealRegion(options1, option2, options3, true);
                entity.setWork_area_code(mItem.work_area_code);
                entity.setWork_area_name(mItem.work_area_name);
                break;
            case ItemInfo.MyInfoType.HOMETOWN:
                text = dealRegion(options1, option2, options3, true);
                entity.setBorn_area_code(mItem.born_area_code);
                entity.setBorn_area_name(mItem.born_area_name);
                break;
            case ItemInfo.MyInfoType.HEIGHT:
                text = dealHeight(options1);
                entity.setHeight(text);
                break;
            case ItemInfo.MyInfoType.EDUCATION:
                text = dealEdu(options1);
                entity.setEducation(mItem.education);
                break;
            case ItemInfo.MyInfoType.PROFESSION:
                text = dealProfession(options1);
                entity.setCareer(mItem.career);
                break;
            case ItemInfo.MyInfoType.INCOME:
                text = dealIncome(options1);
                entity.setIncome(mItem.income);
                break;
            case ItemInfo.MyInfoType.MARRY_STATE:
                text = dealMarryState(options1);
                entity.setMarriage_status(mItem.marriage_status);
                break;
            case ItemInfo.MyInfoType.WEIGHT:
                text = dealWeight(options1);
                entity.setWeight(text);
                break;
        }
        if (TextUtils.isEmpty(text)) return;
        doRequest(entity, text);
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
    private void showEditDialog() {
        if (mEntity == null) return;
        EditDialog dialog = new EditDialog(this) {
            @Override
            public void onConfirm(String data) {
                UserDetailEntity entity1 = new UserDetailEntity();
                entity1.setNickname(data);
                doRequest(entity1, data);
            }
        };
        dialog.show();
    }

    private void setData() {
        UserEntity info = Hawk.get(Keys.USER_INFO);
        if (info == null) info = new UserEntity();
        //昵称
        mItemInfo.add(getInfo(getString(R.string.nick_name), info.getNickname(), ItemInfo.MyInfoType.NICK_NAME, null));

        //出生日期
        mItemInfo.add(getInfo(getString(R.string.birth), info.getBirthday(), ItemInfo.MyInfoType.BIRTHDAY, null));

        //居住地
        mItemInfo.add(getInfo(getString(R.string.apartment), info.getWork_area_name(), ItemInfo.MyInfoType.APARTMENT, null));

        //家乡
        mItemInfo.add(getInfo(getString(R.string.home_town), info.getBorn_area_name(), ItemInfo.MyInfoType.HOMETOWN, null));

        //身高
        mItemInfo.add(getInfo(getString(R.string.height), AppUtils.stringToFloat(info.getHeight()) > 0 ? AppUtils.stringToFloat(info.getHeight()) + "" :
                null, ItemInfo.MyInfoType.HEIGHT, UserUtils.getHighData()));

        //学历
        mItemInfo.add(getInfo(getString(R.string.education), UserUtils.getEdu(info.getEducation()), ItemInfo.MyInfoType.EDUCATION, UserUtils.getEduData(this)));

        //职业
        mItemInfo.add(getInfo(getString(R.string.profession), UserUtils.getCareer(info.getCareer()), ItemInfo.MyInfoType.PROFESSION, UserUtils.getProfessionData()));

        //月收入
        mItemInfo.add(getInfo(getString(R.string.income), UserUtils.getIncome(info.getIncome()), ItemInfo.MyInfoType.INCOME, UserUtils.getComingData(this)));


        //婚姻状况
        mItemInfo.add(getInfo(getString(R.string.marry_state), UserUtils.getMarry(info.getMarriage_status()), ItemInfo.MyInfoType.MARRY_STATE, UserUtils.getMarryState(this)));

        //体重(单位：kg)
        mItemInfo.add(getInfo(getString(R.string.weight), AppUtils.stringToFloat(info.getWeight()) > 0 ? AppUtils.stringToFloat(info.getWeight()) + "" :
                null, ItemInfo.MyInfoType.WEIGHT, UserUtils.getWeight()));

    }


    public ItemInfo getInfo(String text, int type, ArrayList<String> list) {
        return getInfo(text, null, type, list);
    }

    @Override
    public void onRequestSuccess(String response, int type, String data) {
        switch (type) {
            case ItemInfo.MyInfoType.NICK_NAME:
                parseData("nickname", data);
                break;
            case ItemInfo.MyInfoType.BIRTHDAY:
                parseData("birth_date", data);
                break;
            case ItemInfo.MyInfoType.APARTMENT:
                parseData("work_area_name", mItem.work_area_name);
                break;
            case ItemInfo.MyInfoType.HOMETOWN:
                parseData("born_area_name", mItem.born_area_name);
                break;
            case ItemInfo.MyInfoType.HEIGHT:
                parseData("height", data);
                break;
            case ItemInfo.MyInfoType.EDUCATION:
                parseData("education", data);
                break;
            case ItemInfo.MyInfoType.PROFESSION:
                parseData("career", data);
                break;
            case ItemInfo.MyInfoType.INCOME:
                parseData("income", data);
                break;
            case ItemInfo.MyInfoType.MARRY_STATE:
                parseData("marriage_status", data);
                break;
            case ItemInfo.MyInfoType.WEIGHT:
                parseData("weight", data);
                break;
            case ItemInfo.MyInfoType.RANKING:
                parseData("birth_index", data);
                break;
        }
        mEntity.setRightText(data);
        mAdapter.notifyDataSetChanged();
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
                    showRegion(false);
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
                    showEditDialog();
                    break;
                }
            }
        }
    }
}
