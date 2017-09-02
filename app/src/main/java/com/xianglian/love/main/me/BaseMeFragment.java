package com.xianglian.love.main.me;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.json.JSON;
import com.alibaba.json.JSONException;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.callback.Callback;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.xianglian.love.BaseListFragment;
import com.xianglian.love.R;
import com.xianglian.love.config.Config;
import com.xianglian.love.dialog.SelectPicAlertDialog;
import com.xianglian.love.main.home.been.PersonInfo;
import com.xianglian.love.main.home.been.UserDetailEntity;
import com.xianglian.love.model.Album;
import com.xianglian.love.model.AttachmentEntity;
import com.xianglian.love.model.TakePhoto;
import com.xianglian.love.user.AuthenticationActivity;
import com.xianglian.love.user.IntroduceActivity;
import com.xianglian.love.user.LoginActivity;
import com.xianglian.love.user.MarkTagActivity;
import com.xianglian.love.user.adapter.UserInfoEditAdapter;
import com.xianglian.love.user.been.ItemInfo;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.FileOperate;
import com.xianglian.love.utils.PhotoUtils;
import com.xianglian.love.utils.Trace;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanglong on 17/3/11.
 * 我的
 */

public class BaseMeFragment extends BaseListFragment implements BaseQuickAdapter.OnItemClickListener {

    private UserInfoEditAdapter mAdapter;

    private List<ItemInfo> mItemInfo = new ArrayList<>();

    private ItemInfo mEntity;

    //个人介绍 request code
    private static final int REQUEST_CODE_INTRODUCE = 5;

    private TakePhoto mTakePhoto;

    private Album mAlbum;

    private TakePhoto getTakePhoto() {
        if (mTakePhoto == null) {
            mTakePhoto = new TakePhoto(PhotoUtils.PHOTO);
            mTakePhoto.setIntent(AppUtils.getPicturePath(getContext()) + "avatar/");
        }
        return mTakePhoto;
    }

    private Album getAlbum() {
        if (mAlbum == null)
            mAlbum = new Album();
        return mAlbum;
    }


    private void intoDetail() {
        switch (mEntity.getType()) {
            case ItemInfo.SettingType.MY_ALBUM: {
                Intent intent = new Intent(getActivity(), GalleryActivity.class);
                startActivity(intent);
                break;
            }
            case ItemInfo.SettingType.INTRODUCE: {
                Intent intent = IntroduceActivity.getIntent(getContext(), IntroduceActivity.INTRODUCE);
                startActivityForResult(intent, REQUEST_CODE_INTRODUCE);
                break;
            }

            case ItemInfo.SettingType.MY_INFO: {
                Intent intent = new Intent(getActivity(), BaseInfoActivity.class);
                startActivity(intent);
                break;
            }

            case ItemInfo.SettingType.IDENTITY: {
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
                break;
            }

            case ItemInfo.SettingType.HOUSE: {
                break;
            }
            case ItemInfo.SettingType.CAR: {
                break;
            }
            case ItemInfo.SettingType.EXPERIENCE_LOVE: {
                Intent intent = IntroduceActivity.getIntent(getContext(), IntroduceActivity.EXPERIENCE);
                startActivityForResult(intent, REQUEST_CODE_INTRODUCE);
                break;
            }
            case ItemInfo.SettingType.MARK: {
                Intent intent = MarkTagActivity.getIntent(getContext(), MarkTagActivity.MARK);
                startActivity(intent);
                break;
            }
            case ItemInfo.SettingType.HOBBY: {
                Intent intent = MarkTagActivity.getIntent(getContext(), MarkTagActivity.HOBBY);
                startActivity(intent);
                break;
            }
            case ItemInfo.SettingType.SETTING: {

                break;
            }
        }
    }

    public static BaseMeFragment newInstance() {
        return new BaseMeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, null);
        setupRecyclerView(view);
        return view;
    }

    public void setupRecyclerView(View view) {
        super.setupRecyclerView(view);
        mAdapter = new UserInfoEditAdapter(getContext(), mItemInfo);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
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
                mItemInfo.add(info);
                break;
            }
            case ItemInfo.ViewType.PICK_SELECT: {
                setData();
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void setData() {

        //相册
        mItemInfo.add(getInfo(getString(R.string.my_album), ItemInfo.SettingType.MY_ALBUM, null, true));

        //个人简介
        mItemInfo.add(getInfo(getString(R.string.introduce), ItemInfo.SettingType.INTRODUCE, null));

        //个人信息
        mItemInfo.add(getInfo(getString(R.string.my_info), ItemInfo.SettingType.MY_INFO, null));

        //成为会员
        mItemInfo.add(getInfo(getString(R.string.set_vip), ItemInfo.SettingType.SET_VIP, null, true));

        //身份认证
        mItemInfo.add(getInfo(getString(R.string.person_identity), ItemInfo.SettingType.IDENTITY, null));

        //联系方式
        mItemInfo.add(getInfo(getString(R.string.phone), ItemInfo.SettingType.PHONE, null));

        //情感经历
        mItemInfo.add(getInfo(getString(R.string.experience_love), ItemInfo.SettingType.EXPERIENCE_LOVE, null, true));

        //择偶标准
        mItemInfo.add(getInfo(getString(R.string.condition_friend), ItemInfo.SettingType.CHOOSE_FRIEND_STANDARD, null));

        //个人标签
        mItemInfo.add(getInfo(getString(R.string.mark), ItemInfo.SettingType.MARK, null, true));

        //兴趣爱好
        mItemInfo.add(getInfo(getString(R.string.hobby), ItemInfo.SettingType.HOBBY, null));

        //设置
        mItemInfo.add(getInfo(getString(R.string.setting), ItemInfo.SettingType.SETTING, null));

        //客服
        mItemInfo.add(getInfo(getString(R.string.customer_agent), ItemInfo.SettingType.CUSTOMER_AGENT, null));

    }

    public ItemInfo getInfo(String text, int type, PersonInfo info) {
        return getInfo(text, type, info, false);
    }

    public ItemInfo getInfo(String text, int type, PersonInfo info, boolean showLine) {
        ItemInfo data = new ItemInfo();
        data.setViewType(ItemInfo.ViewType.PICK_SELECT);
        data.setText(text);
        data.setType(type);
        data.setInfo(info);
        return data;
    }

    public void changeHead() {
        SelectPicAlertDialog dialog = new SelectPicAlertDialog(getContext(), getString(R.string.set_avt)) {
            @Override
            public void takePhoto() {
                Intent takePhoto = getTakePhoto().setIntent(AppUtils.getPicturePath(getContext()) + "avatar/");
                startActivityForResult(takePhoto, PhotoUtils.PHOTO);
            }

            @Override
            public void useNativeGalley() {
                mAlbum = new Album();
                Intent it = mAlbum.setIntent(AppUtils.getPicturePath(getContext()) + "avatar/",
                        "image/jpeg");
                startActivityForResult(it, PhotoUtils.ALBUM);
            }
        };
        dialog.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtils.PHOTO:
                if (new File(getTakePhoto().getFileName()).exists()) {
                    Intent intent = PhotoUtils.getCropImageIntent(
                            Uri.fromFile(new File(getTakePhoto().getFileName())),
                            Uri.fromFile(new File(AppUtils.getPicturePath(getContext())
                                    + "avatar/thumb_" + getTakePhoto().getName())));
                    if (AppUtils.isIntentAvailable(getContext(), intent)) {
                        startActivityForResult(intent, PhotoUtils.REQUEST_CODE_PHOTO_CUT);
                    } else {
                        AppUtils.showToast(getContext(), getString(R.string.gallery_unable));
                    }
                }
                break;
            case PhotoUtils.ALBUM:
                if (data != null) {
                    ContentResolver resolver = getContext().getContentResolver();
                    // 照片的原始资源地址
                    Uri originalUri = AppUtils.getUri(getContext(), data);
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
                                FileOperate.copyfile(cursor.getString(index), getAlbum().getFileName(),
                                        false);
                                Intent albuIntent = PhotoUtils.getCropImageIntent(
                                        Uri.fromFile(new File(getAlbum().getFileName())),
                                        Uri.fromFile(new File(AppUtils.getPicturePath(getContext())
                                                + "avatar/thumb_" + getAlbum().getName())));
                                startActivityForResult(albuIntent, PhotoUtils.REQUEST_CODE_ALBUM_CUT);
                            } else {
                                AppUtils.showToast(getContext(), getString(R.string.unaccess_pic));
                            }
                        } else {
                            AppUtils.showToast(getContext(), getString(R.string.unaccess_pic));
                        }
                    }
                }
                break;
            case PhotoUtils.REQUEST_CODE_PHOTO_CUT:
                getTakePhoto().getThumbPhoto(AppUtils.getPicturePath(getContext()) + "avatar/",
                        "thumb_" + getTakePhoto().getName());
                AttachmentEntity entity = getTakePhoto().getAttachment();
//                uploadAvatar(entity.getUrl());
                break;
            case PhotoUtils.REQUEST_CODE_ALBUM_CUT:
                if (resultCode != 0) {
//                    uploadAvatar(AppUtils.getPicturePath(getContext()) + "avatar/thumb_" + getAlbum().getName());
                }
                break;
        }
    }

    /**
     * 修改头像
     *
     * @param path
     */
    private void uploadAvatar(String token, String path, String fileName) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(path, fileName, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            Trace.i("qiniu", "Upload Success");
                            //                String url = bmobFile.getFileUrl();
                            //                if (!TextUtils.isEmpty(url)) {
                            //                    updateAvatar(url);
                            //                } else {
                            //                    toast(R.string.text_upload_avatar_fail);
                            //                }
                        } else {
                            Trace.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Trace.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }

    private void doGetTokenRequest(final String id) {
        final String url = Config.PATH + "user/like/" + id;
        Map<String, String> params = new HashMap<>();
        params.put("uid", id);
        OkHttpUtil.getDefault(this).doPostAsync(
                HttpInfo.Builder().setUrl(url).addHeads(getHeader()).addParams(params).build(),
                new Callback() {
                    @Override
                    public void onFailure(HttpInfo info) throws IOException {
                        toast(getString(R.string.request_fail));
                    }

                    @Override
                    public void onSuccess(HttpInfo info) throws IOException {
                        String result = info.getRetDetail();
                        if (result != null) {
                            try {
                                UserDetailEntity userEntity = JSON.parseObject(result, UserDetailEntity.class);
                                if (userEntity == null) return;
                                if (userEntity.getCode() == Config.FAIL) {
                                    toast(TextUtils.isEmpty(userEntity.getMsg()) ?
                                            getString(R.string.request_fail) : userEntity.getMsg());
                                } else {
                                    TextView likeView = (TextView) mRecyclerView.findViewWithTag(id);
                                    if (likeView != null) {
                                        int num = AppUtils.stringToInt(likeView.getText().toString()) + 1;
                                        likeView.setText(num + "");
                                    }
                                    ImageView likeIcon = (ImageView) mRecyclerView.findViewWithTag(id + "_iv");
                                    if (likeIcon != null) {
                                        likeIcon.setImageResource(R.drawable.icon_follow);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!AppUtils.isLogin(getContext())) {
            Intent intent = LoginActivity.getIntent(getContext());
            startActivity(intent);
            return;
        }
        mEntity = mAdapter.getItem(position);
        if (mEntity == null) return;
        if (ItemInfo.ViewType.AVATAR == mEntity.getViewType()) {
            changeHead();
        } else {
            intoDetail();
        }
    }
}
