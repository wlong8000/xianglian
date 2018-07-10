package com.xianglian.love.user;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

//import com.qiniu.android.http.ResponseInfo;
//import com.qiniu.android.storage.UpCompletionHandler;
//import com.qiniu.android.storage.UploadManager;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.dialog.FirstChooseDialog;
import com.xianglian.love.dialog.LocationSettingDialog;
import com.xianglian.love.dialog.SelectPicAlertDialog;
import com.xianglian.love.model.Album;
import com.xianglian.love.model.AttachmentEntity;
import com.xianglian.love.model.TakePhoto;
import com.xianglian.love.user.adapter.UserInfoEditAdapter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.CommonLinearLayoutManager;
import com.xianglian.love.utils.FileOperate;
import com.xianglian.love.utils.UserUtils;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by Administrator on 2017/3/8.
 * 用户信息填写
 */
public class UserInfoEditActivity extends BaseUserInfoActivity implements View.OnClickListener {


    @InjectView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @InjectView(R.id.bottom_complete)
    TextView mCompleteView;

    private CommonLinearLayoutManager mLayoutManager;

    private UserInfoEditAdapter mAdapter;

    private TakePhoto mFunction;

    private Album mAlbum;

    private final int PHOTO = 1;

    private final int ALBUM = 2;

    private final static int REQUEST_CODE_PHOTO_CUT = 3;

    private final static int REQUEST_CODE_ALBUM_CUT = 4;

    //个人介绍 request code
    private final int REQUEST_CODE_INTRODUCE = 5;

    private ItemInfo mEntity;

    private int mType = -1;

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = mRecyclerView.getChildAdapterPosition(view);
            mEntity = mAdapter.getItem(position);
            if (mEntity == null) return;
            mType = mEntity.getType();
            if (ItemInfo.ViewType.PICK_SELECT == mEntity.getViewType()) {
                switch (mType) {
                    case ItemInfo.Type.INTRODUCE: {
                        Intent intent = new Intent(UserInfoEditActivity.this, IntroduceActivity.class);
                        startActivityForResult(intent, REQUEST_CODE_INTRODUCE);
                        break;
                    }
                    case ItemInfo.Type.BIRTHDAY: {
                        showDateDialog(mEntity);
                        break;
                    }
                    case ItemInfo.Type.HOMETOWN:
                    case ItemInfo.Type.APARTMENT: {
                        showOptions(mEntity);
                        break;
                    }
                    case ItemInfo.Type.INCOME:
                    case ItemInfo.Type.EDUCATION:
                    case ItemInfo.Type.PROFESSION:
                    case ItemInfo.Type.HEIGHT: {
                        showBottomDialog(mEntity);
                        break;
                    }
                }
            } else if (ItemInfo.ViewType.AVATAR == mEntity.getViewType()) {
                Intent intent = getCropImageIntent(
                        Uri.fromFile(new File(AppUtils.getPicturePath(UserInfoEditActivity.this) + "avatar/")),
                        Uri.fromFile(new File(AppUtils.getPicturePath(UserInfoEditActivity.this) + "avatar/")));
                if (AppUtils.isIntentAvailable(UserInfoEditActivity.this, intent)) {
                    changeHead(view);
                }
            }
        }
    };

    /**
     * 保存用户信息
     */
    private void saveUserInfo() {
//        mPersonInfo.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    toast(R.string.text_save_user_info_success);
//                    AppSharePreferences.saveBoolValue(UserInfoEditActivity.this, AppSharePreferences.USER_INFO, true);
//                    startActivity(new Intent(UserInfoEditActivity.this, MainActivity.class));
//                    finish();
//                } else {
//                    toast(R.string.text_save_user_info_fail);
//                    loge(e);
//                }
//            }
//        });
    }


    public void changeHead(View v) {
        SelectPicAlertDialog dialog = new SelectPicAlertDialog(this, getString(R.string.set_avt)) {
            @Override
            public void takePhoto() {
                mFunction = new TakePhoto(PHOTO);
                Intent takePhoto = mFunction.setIntent(AppUtils.getPicturePath(UserInfoEditActivity.this) + "avatar/");
                startActivityForResult(takePhoto, PHOTO);
            }

            @Override
            public void useNativeGalley() {
                mAlbum = new Album();
                Intent it = mAlbum.setIntent(AppUtils.getPicturePath(UserInfoEditActivity.this) + "avatar/",
                        "image/jpeg");
                startActivityForResult(it, ALBUM);
            }
        };
        dialog.show();

    }

    private Intent getCropImageIntent(Uri fromPhotoUri, Uri toPhotoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(fromPhotoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, toPhotoUri);
        return intent;
    }

    /**
     * 单项选择
     *
     */
    private void showBottomDialog(final ItemInfo entity) {
        if (entity == null || entity.getItems() == null) return;
        FirstChooseDialog dialog = new FirstChooseDialog(this, entity.getItems()) {
            @Override
            public void onConfirm(String data) {
                entity.setRightText(data);
                mAdapter.notifyDataSetChanged();
                saveDate(data);
            }
        };
        dialog.show();
    }

    /**
     * 时间选择
     */
    private void showDateDialog(final ItemInfo entity) {
//        Util.alertTimerPicker(this, TimePickerView.Type.YEAR_MONTH_DAY, "yyyy-MM-dd", new Util.TimerPickerCallBack() {
//            @Override
//            public void onTimeSelect(String date) {
//                if (TextUtils.isEmpty(date)) return;
//                entity.setRightText(date);
//                mAdapter.notifyDataSetChanged();
//                saveDate(date);
//            }
//        });
    }

    /**
     * 多项选择
     *
     */
    private void showOptions(final ItemInfo entity) {
        LocationSettingDialog dialog = new LocationSettingDialog(this,
                AppUtils.getRegionFromCache(this)) {
            @Override
            public void onConfirm(String region, int position) {
                entity.setRightText(region);
                mAdapter.notifyDataSetChanged();
                saveDate(region);
            }
        };
        dialog.show();
    }

    private void saveDate(String data) {
        if (TextUtils.isEmpty(data)) return;
        switch (mType) {
            case ItemInfo.Type.INTRODUCE: {
                break;
            }
            case ItemInfo.Type.BIRTHDAY: {
                mPersonInfo.setBirthday(data);
                break;
            }
            case ItemInfo.Type.APARTMENT: {
                mPersonInfo.setApartment(data);
                break;
            }
            case ItemInfo.Type.HOMETOWN: {
                mPersonInfo.setNative_place(data);
                break;
            }
            case ItemInfo.Type.HEIGHT: {
                try {
                    int height = Integer.parseInt(data);
                    mPersonInfo.setHeight(height);
                } catch (NumberFormatException e) {
                    mPersonInfo.setHeight(0);
                    e.printStackTrace();
                }
                break;
            }
            case ItemInfo.Type.EDUCATION: {
                mPersonInfo.setEducation(data);
                break;
            }
            case ItemInfo.Type.PROFESSION: {
                mPersonInfo.setJobs(data);
                break;
            }
            case ItemInfo.Type.INCOME: {
                mPersonInfo.setIncome(data);
                break;
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info_activity);
        ButterKnife.inject(this);
        initView();
        mCompleteView.setOnClickListener(this);
    }

    private void initView() {
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mLayoutManager = new CommonLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new UserInfoEditAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);

        addData();
    }

    private void addData() {
        addDataByType(ItemInfo.ViewType.AVATAR);
        addDataByType(ItemInfo.ViewType.PICK_SELECT);

    }

    private void addDataByType(int type) {
        switch (type) {
            case ItemInfo.ViewType.AVATAR: {
                ItemInfo info = new ItemInfo();
                info.setViewType(type);
//                mAdapter.getInfo().add(info);
                break;
            }
            case ItemInfo.ViewType.PICK_SELECT: {
//                mAdapter.getInfo().addAll(getData());
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bottom_complete:
                saveUserInfo();
                break;
        }
    }

    private List<ItemInfo> getData() {
        List<ItemInfo> data = new ArrayList<>();

        //添加简介
        ItemInfo introduce = new ItemInfo();
        introduce.setViewType(ItemInfo.ViewType.PICK_SELECT);
        introduce.setText(getString(R.string.introduce));
        introduce.setRightText(getString(R.string.please_write_introduce));
        introduce.setScheme("love://user/edit/introduce");
        introduce.setType(ItemInfo.Type.INTRODUCE);
        data.add(introduce);

        //出生日期
        data.add(getInfo(getString(R.string.birth), ItemInfo.Type.BIRTHDAY, null));

        //居住地
        data.add(getInfo(getString(R.string.apartment), ItemInfo.Type.APARTMENT, null));

        //家乡
        data.add(getInfo(getString(R.string.home_town), ItemInfo.Type.HOMETOWN, null));

        //身高
        data.add(getInfo(getString(R.string.height), ItemInfo.Type.HEIGHT, UserUtils.getHighData()));

        //学历
        data.add(getInfo(getString(R.string.education), ItemInfo.Type.EDUCATION, UserUtils.getEduData(this)));

        //职业
        data.add(getInfo(getString(R.string.profession), ItemInfo.Type.PROFESSION, UserUtils.getProfessionData()));

        //收入
        data.add(getInfo(getString(R.string.income), ItemInfo.Type.INCOME, UserUtils.getComingData(this)));

        return data;

    }

    public ItemInfo getInfo(String text, int type, List<String> list) {
        return getInfo(text, null, type, list);
    }

    /**
     * @param text
     * @param rightText
     * @param type      0默认 1：带5dp的分割线
     * @return
     */
    public ItemInfo getInfo(String text, String rightText, int type, List<String> list) {
        if (TextUtils.isEmpty(rightText))
            rightText = getResources().getString(R.string.please_select);
        ItemInfo data = new ItemInfo();
        data.setViewType(ItemInfo.ViewType.PICK_SELECT);
        data.setText(text);
        data.setRightText(rightText);
        data.setType(type);
        data.setItems(list);
        return data;
    }

    /**
     * 上传头像
     *
     * @param path
     */
    private void uploadAvatar(String token, String path, String fileName) {
//        final BmobFile bmobFile = new BmobFile(new File(path));
//        bmobFile.uploadblock(new UploadFileListener() {
//            @Override
//            public void done(BmobException e) {
//                String url = bmobFile.getFileUrl();
//                if (!TextUtils.isEmpty(url)) {
//                    toast(R.string.text_upload_avatar_success);
//                    mEntity.setAvatar(url);
//                    mPersonInfo.setAvatar(url);
//                    mAdapter.notifyDataSetChanged();
//                } else {
//                    toast(R.string.text_upload_avatar_fail);
//                }
//            }
//        });
//        UploadManager uploadManager = new UploadManager();
//        uploadManager.put(path, fileName, token,
//                new UpCompletionHandler() {
//                    @Override
//                    public void complete(String key, ResponseInfo info, JSONObject res) {
//                        //res包含hash、key等信息，具体字段取决于上传策略的设置
//                        if(info.isOK()) {
//                            Log.i("qiniu", "Upload Success");
//                        } else {
//                            Log.i("qiniu", "Upload Fail");
//                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
//                        }
//                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
//                    }
//                }, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO:
                if (mFunction == null) {
                    mFunction = new TakePhoto(PHOTO);
                    mFunction.setIntent(AppUtils.getPicturePath(this) + "avatar/");
                }
                if (new File(mFunction.getFileName()).exists()) {
                    Intent intent = getCropImageIntent(
                            Uri.fromFile(new File(mFunction.getFileName())),
                            Uri.fromFile(new File(AppUtils.getPicturePath(this)
                                    + "avatar/thumb_" + mFunction.getName())));
                    if (AppUtils.isIntentAvailable(this, intent)) {
                        startActivityForResult(intent, REQUEST_CODE_PHOTO_CUT);
                    } else {
                        AppUtils.showToast(this, getString(R.string.gallery_unable));
                    }
                }
                break;
            case ALBUM:
                if (data != null) {
                    ContentResolver resolver = getContentResolver();
                    // 照片的原始资源地址
                    Uri originalUri = AppUtils.getUri(getApplicationContext(), data);
                    if (originalUri != null) {
                        String[] imgs = {
                                MediaStore.Images.Media.DATA
                        };// 将图片URI转换成存储路径
                        Cursor cursor = resolver.query(originalUri, imgs, null, null, null);
                        if (cursor != null) {
                            cursor.moveToFirst();
                            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            if (cursor.getString(index) != null) {
                                if (mAlbum == null) {
                                    mAlbum = new Album();
                                    Intent it = mAlbum.setIntent(AppUtils.getPicturePath(this)
                                            + "avatar/", "image/jpeg");
                                }
                                FileOperate.copyfile(cursor.getString(index), mAlbum.getFileName(),
                                        false);
                                Intent albuIntent = getCropImageIntent(
                                        Uri.fromFile(new File(mAlbum.getFileName())),
                                        Uri.fromFile(new File(AppUtils.getPicturePath(this)
                                                + "avatar/thumb_" + mAlbum.getName())));
                                startActivityForResult(albuIntent, REQUEST_CODE_ALBUM_CUT);
                            } else {
                                AppUtils.showToast(this, getString(R.string.unaccess_pic));
                            }
                        } else {
                            AppUtils.showToast(this, getString(R.string.unaccess_pic));
                        }
                    }
                }
                break;
            case REQUEST_CODE_PHOTO_CUT:
                mFunction.getThumbPhoto(AppUtils.getPicturePath(this) + "avatar/",
                        "thumb_" + mFunction.getName());
                AttachmentEntity entity = mFunction.getAttachment();
//                uploadAvatar(entity.getUrl());
                break;
            case REQUEST_CODE_ALBUM_CUT:
                if (resultCode != 0) {
//                    uploadAvatar(AppUtils.getPicturePath(this) + "avatar/thumb_" + mAlbum.getName());
                }
                break;
            case REQUEST_CODE_INTRODUCE :
                if (data != null) {
                   String introduce =  data.getStringExtra(Config.INTRODUCE_RESULT_KEY);
                    if (!TextUtils.isEmpty(introduce)) {
                        mPersonInfo.setIntroduce(introduce);
                        if (introduce.length() > 10) {
                            introduce = introduce.substring(0, 10) + "...";
                        }
                        mEntity.setRightText(introduce);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                break;
        }
    }
}
