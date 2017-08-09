package com.wl.lianba.main.me;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wl.lianba.BaseFragment;
import com.wl.lianba.R;
import com.wl.lianba.dialog.SelectPicAlertDialog;
import com.wl.lianba.main.home.been.PersonInfo;
import com.wl.lianba.main.me.adapter.MineAdapter;
import com.wl.lianba.model.Album;
import com.wl.lianba.model.AttachmentEntity;
import com.wl.lianba.model.TakePhoto;
import com.wl.lianba.user.AuthenticationActivity;
import com.wl.lianba.user.IntroduceActivity;
import com.wl.lianba.user.MarkTagActivity;
import com.wl.lianba.user.been.ItemInfo;
import com.wl.lianba.utils.AppUtils;
import com.wl.lianba.utils.CommonLinearLayoutManager;
import com.wl.lianba.utils.FileOperate;
import com.wl.lianba.utils.PhotoUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by wanglong on 17/3/11.
 * 我的
 */

public class BaseMeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

    private CommonLinearLayoutManager mLayoutManager;

    private MineAdapter mAdapter;

    private ItemInfo mEntity;

    //个人介绍 request code
    private final int REQUEST_CODE_INTRODUCE = 5;

    private TakePhoto mTakePhoto;

    private Album mAlbum;

    private View.OnClickListener itemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = mRecyclerView.getChildAdapterPosition(view);
            mEntity = mAdapter.getItem(position);
            if (mEntity == null) return;
            if (ItemInfo.ViewType.AVATAR == mEntity.getViewType()) {
                changeHead();
            } else {
                intoDetail();
            }

        }
    };

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
        BaseMeFragment fragment = new BaseMeFragment();
        return fragment;
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

    private void setupRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new CommonLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MineAdapter(getContext(), itemClickListener);
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
                mAdapter.getInfo().add(info);
                break;
            }
            case ItemInfo.ViewType.PICK_SELECT: {
                mAdapter.getInfo().addAll(getData());
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private List<ItemInfo> getData() {
        List<ItemInfo> data = new ArrayList<>();

        //相册
        data.add(getInfo(getString(R.string.my_album), ItemInfo.SettingType.MY_ALBUM, null, true));

        //个人简介
        data.add(getInfo(getString(R.string.introduce), ItemInfo.SettingType.INTRODUCE, null));

        //个人信息
        data.add(getInfo(getString(R.string.my_info), ItemInfo.SettingType.MY_INFO, null));

        //成为会员
        data.add(getInfo(getString(R.string.set_vip), ItemInfo.SettingType.SET_VIP, null, true));

        //身份认证
        data.add(getInfo(getString(R.string.person_identity), ItemInfo.SettingType.IDENTITY, null));

        //联系方式
        data.add(getInfo(getString(R.string.phone), ItemInfo.SettingType.PHONE, null));

        //情感经历
        data.add(getInfo(getString(R.string.experience_love), ItemInfo.SettingType.EXPERIENCE_LOVE, null, true));

        //择偶标准
        data.add(getInfo(getString(R.string.condition_friend), ItemInfo.SettingType.CHOOSE_FRIEND_STANDARD, null));

        //个人标签
        data.add(getInfo(getString(R.string.mark), ItemInfo.SettingType.MARK, null, true));

        //兴趣爱好
        data.add(getInfo(getString(R.string.hobby), ItemInfo.SettingType.HOBBY, null));

        //设置
        data.add(getInfo(getString(R.string.setting), ItemInfo.SettingType.SETTING, null));

        //客服
        data.add(getInfo(getString(R.string.customer_agent), ItemInfo.SettingType.CUSTOMER_AGENT, null));

        return data;

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
                uploadAvatar(entity.getUrl());
                break;
            case PhotoUtils.REQUEST_CODE_ALBUM_CUT:
                if (resultCode != 0) {
                    uploadAvatar(AppUtils.getPicturePath(getContext()) + "avatar/thumb_" + getAlbum().getName());
                }
                break;
        }
    }

    /**
     * 修改头像
     *
     * @param path
     */
    private void uploadAvatar(String path) {
//        final BmobFile bmobFile = new BmobFile(new File(path));
//
//        bmobFile.uploadblock(new UploadFileListener() {
//            @Override
//            public void done(BmobException e) {
//                String url = bmobFile.getFileUrl();
//                if (!TextUtils.isEmpty(url)) {
//                    updateAvatar(url);
//                } else {
//                    toast(R.string.text_upload_avatar_fail);
//                }
//            }
//        });
    }

    private void updateAvatar(final String path) {
//        if (TextUtils.isEmpty(path)) return;
//        final PersonInfo info = new PersonInfo();
//        info.setAvatar(path);
//        info.update(AppUtils.getObjectId(getContext()), new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    toast(R.string.text_update_avatar_success);
//                    PersonInfo info = AppUtils.getPersonInfo(getContext());
//                    info.setAvatar(path);
//                    ACache.get(getContext()).put(Config.SAVE_USER_KEY, info);
//                    mAdapter.notifyDataSetChanged();
//                } else {
//                    toast(R.string.text_upload_avatar_fail);
//                }
//            }
//        });
    }
}
