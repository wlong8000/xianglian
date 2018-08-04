package com.xianglian.love.main.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.hawk.Hawk;
import com.wl.appcore.utils.AppUtils2;
import com.xianglian.love.BaseEditUserInfoActivity;
import com.xianglian.love.R;
import com.wl.appcore.Keys;
import com.xianglian.love.dialog.EditDialog;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.user.adapter.UserInfoEditAdapter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.UserUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import base.OkDialog;

/**
 * 基本资料
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
                text = dealRegion(options1, option2, options3, false);
                entity.setWork_area_code(mItem.work_area_code);
                entity.setWork_area_name(mItem.work_area_name);
                break;
            case ItemInfo.MyInfoType.HOMETOWN:
                text = dealRegion(options1, option2, options3, false);
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
            case ItemInfo.MyInfoType.NATION:
                text = dealNationality(options1);
                entity.setNationality(mItem.nationality);
                break;
            case ItemInfo.MyInfoType.BROTHER_STATE:
                text = dealBrotherState(options1);
                entity.setBrother_state(mItem.brother_state);
                break;
            case ItemInfo.MyInfoType.CONSTELLATION:
                text = dealConstellation(options1);
                entity.setConstellation(mItem.constellation);
                break;
            case ItemInfo.MyInfoType.PARENT_WORK:
                text = dealParentWork(options1);
                entity.setParent_work(mItem.parent_work);
                break;
            case ItemInfo.MyInfoType.HOPE_MARRY:
                text = dealHopeMarry(options1);
                entity.setExpect_marry_time(mItem.expect_marry_time);
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

    @Override
    public void onRefresh(boolean refresh) {

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
                entity1.setUsername(data);
                doRequest(entity1, data);
            }
        };
        dialog.show();
    }

    private void setData() {
        UserEntity info = Hawk.get(Keys.USER_INFO);
        if (info == null) info = new UserEntity();
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
        mItemInfo.add(getInfo(getString(R.string.education), UserUtils.getEdu(getInt(info.getEducation())), ItemInfo.MyInfoType.EDUCATION, UserUtils.getEduList()));

        //职业
        mItemInfo.add(getInfo(getString(R.string.profession), UserUtils.getProfession(getInt(info.getCareer())), ItemInfo.MyInfoType.PROFESSION, UserUtils.getProfessions()));

        //月收入
        mItemInfo.add(getInfo(getString(R.string.income), UserUtils.getInCome(getInt(info.getIncome())), ItemInfo.MyInfoType.INCOME, UserUtils.getInComingList()));

        //星座
        mItemInfo.add(getInfo(getString(R.string.constellation), UserUtils.getConstellation(getInt(info.getConstellation())), ItemInfo.MyInfoType.CONSTELLATION, UserUtils.getConstellations()));

        //婚姻状况
        mItemInfo.add(getInfo(getString(R.string.marry_state), UserUtils.getMarryState(getInt(info.getMarriage_status())), ItemInfo.MyInfoType.MARRY_STATE, UserUtils.getMarryStates()));

        //期望结婚时间
        mItemInfo.add(getInfo(getString(R.string.hope_marry), UserUtils.getHopeMarry(getInt(info.getExpect_marry_time())), ItemInfo.MyInfoType.HOPE_MARRY, UserUtils.getHopeMarries()));

        //民族
        mItemInfo.add(getInfo(getString(R.string.nation), UserUtils.getNation(getInt(info.getNationality())), ItemInfo.MyInfoType.NATION, UserUtils.getNations()));

        //姊妹情况
        mItemInfo.add(getInfo(getString(R.string.brother_state), UserUtils.getBrotherState(getInt(info.getBrother_state())), ItemInfo.MyInfoType.BROTHER_STATE, UserUtils.getBrotherStates()));

        //体重(单位：kg)
        mItemInfo.add(getInfo(getString(R.string.weight), AppUtils.stringToFloat(info.getWeight()) > 0 ? AppUtils.stringToFloat(info.getWeight()) + "" :
                null, ItemInfo.MyInfoType.WEIGHT, UserUtils.getWeight()));

        //父母工作
        mItemInfo.add(getInfo(getString(R.string.parent_work), UserUtils.getParentProfession(getInt(info.getParent_work())), ItemInfo.MyInfoType.PARENT_WORK, UserUtils.getParentProfessions()));

    }


    public ItemInfo getInfo(String text, int type, ArrayList<String> list) {
        return getInfo(text, null, type, list);
    }

    @Override
    public void onRequestSuccess(String response, int type, String data) {
        UserEntity userEntity = Hawk.get(Keys.USER_INFO);
        switch (type) {
//            case ItemInfo.MyInfoType.NICK_NAME:
//                userEntity.setUsername(data);
//                break;
            case ItemInfo.MyInfoType.BIRTHDAY:
                userEntity.setBirthday(data);
                break;
            case ItemInfo.MyInfoType.APARTMENT:
                userEntity.setWork_area_name(mItem.work_area_name);
                break;
            case ItemInfo.MyInfoType.HOMETOWN:
                userEntity.setBorn_area_name(mItem.born_area_name);
                break;
            case ItemInfo.MyInfoType.HEIGHT:
                userEntity.setHeight(data);
                break;
            case ItemInfo.MyInfoType.EDUCATION:
                userEntity.setEducation(data);
                break;
            case ItemInfo.MyInfoType.PROFESSION:
                userEntity.setCareer(data);
                break;
            case ItemInfo.MyInfoType.INCOME:
                userEntity.setIncome(data);
                break;
            case ItemInfo.MyInfoType.MARRY_STATE:
                userEntity.setMarriage_status(data);
                break;
            case ItemInfo.MyInfoType.WEIGHT:
                userEntity.setWeight(data);
                break;
            case ItemInfo.MyInfoType.BROTHER_STATE:
                userEntity.setBrother_state(data);
                break;
            case ItemInfo.MyInfoType.CONSTELLATION:
                userEntity.setConstellation(data);
                break;
            case ItemInfo.MyInfoType.PARENT_WORK:
                userEntity.setParent_work(data);
                break;
            case ItemInfo.MyInfoType.NATION:
                userEntity.setNationality(data);
                break;
        }
        Hawk.put(Keys.USER_INFO, userEntity);
        mEntity.setRightText(data);
        mAdapter.notifyDataSetChanged();
    }

    private int getInt(String data) {
        return AppUtils.stringToInt(data);
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
                case ItemInfo.MyInfoType.NICK_NAME: {
                    showEditDialog();
                    break;
                }
                default: {
                    pvCustomOptions.setPicker(mEntity.getItems());//添加数据
                    pvCustomOptions.show();
                    break;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        showDialog();
    }

    @Override
    public void leftClick() {
        showDialog();
    }

    private void showDialog() {
        String text = AppUtils2.isCompleteData(1);
        if (TextUtils.isEmpty(text)) {
            finish();
            return;
        }
        OkDialog okDialog = new OkDialog(this) {
            @Override
            public void onConfirm(String result) {
                finish();
            }
        };
        okDialog.show();
        okDialog.setTitle("如果要查看别人资料，请填写完整个人基本资料，当前填写不完整，确定退出？");
    }
}
