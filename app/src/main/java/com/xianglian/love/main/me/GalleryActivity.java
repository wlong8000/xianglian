package com.xianglian.love.main.me;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;

import com.xianglian.love.R;
import com.xianglian.love.ShowPicActivity;
import com.xianglian.love.library.photo.widget.PickConfig;
import com.xianglian.love.library.ucrop.UCrop;
import com.xianglian.love.main.home.been.PhotoInfo;

import com.xianglian.love.user.BaseUserInfoActivity;
import com.xianglian.love.view.AlumView;

import java.util.ArrayList;
import java.util.List;


public class GalleryActivity extends BaseUserInfoActivity implements AlumView.OnItemClickListener {
    //相册封装好的view
    private AlumView mAlumView;

    private static final int MAX_PIC = 9;

    private int mMaxPicCount = MAX_PIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        setupCommonTitle(getString(R.string.my_album));
//        mMaxPicCount = mMaxPicCount - mPersonInfo.getAlbum().size();
        setupViews();
    }

    private void setupViews() {
        mAlumView = (AlumView) findViewById(R.id.layout_alum);
        mAlumView.setOnItemClickListener(this);
        mAlumView.initAdapter(3);
//        addData();
    }

//    private void addData() {
//        addDataByType(PhotoInfo.AlumViewType.ALUM_ADD, null);
//        addAlumData(AppUtils.getPhotoInfo(this));
//        mAlumView.notifyDataSetChanged();
//    }

    private void addAlumData(List<PhotoInfo> paths) {
        if (paths == null) return;
        List<PhotoInfo> items = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            PhotoInfo info = new PhotoInfo();
            info.setViewType(PhotoInfo.AlumViewType.ALUM_COMMON);
            info.setPhoto_url(paths.get(i).getPhoto_url());
            items.add(info);
        }
        mAlumView.addData(items);
        mAlumView.notifyDataSetChanged();

    }

    private void addDataByType(int type, String path) {
        switch (type) {
            case PhotoInfo.AlumViewType.ALUM_ADD: {
                PhotoInfo info = new PhotoInfo();
                info.setViewType(type);
                mAlumView.addData(info);
                break;
            }
            case PhotoInfo.AlumViewType.ALUM_COMMON: {
                PhotoInfo info = new PhotoInfo();
                info.setViewType(PhotoInfo.AlumViewType.ALUM_COMMON);
                info.setPhoto_url(path);
                mAlumView.addData(info);
                break;
            }
        }
    }

    @Override
    public void onItemClick(int position, PhotoInfo info) {
        if (position == 0) {
            if (mMaxPicCount <= 0) {
                showToast(getString(R.string.maxsupport_num, MAX_PIC + ""));
                return;
            }
            int chose_mode = PickConfig.MODE_MULTIP_PICK;
            UCrop.Options options = new UCrop.Options();
            options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
            options.setCompressionQuality(100);
            new PickConfig.Builder(GalleryActivity.this)
                    .isNeedCrop(false)
                    .actionBarColor(getResources().getColor(R.color.lib_color_font8))
                    .statusBarColor(getResources().getColor(R.color.lib_color_font8))
                    .isNeedCamera(true)
                    .isSquareCrop(false)
                    .setUropOptions(options)
                    .maxPickSize(mMaxPicCount)
                    .spanCount(3)
                    .pickMode(chose_mode).build();
        } else {
//            List<PhotoInfo> data = mPersonInfo.getAlbum();
//            if (data == null) return;
//            String[] arr = new String[data.size()];
//            for (int i = 0; i < data.size(); i++) {
//                arr[i] = data.get(i).getPhoto_url();
//            }
//            ShowPicActivity.showPictures(GalleryActivity.this, arr, position - 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PickConfig.PICK_REQUEST_CODE) {
            //在data中返回 选择的图片列表
            List<String> paths = data.getStringArrayListExtra("data");
            String[] arr = new String[paths.size()];
            for (int i = 0; i < paths.size(); i++) {
                arr[i] = paths.get(i);
            }
            uploadPic(arr);
        }
    }

    private void uploadPic(final String[] filePaths) {
//        BmobFile.uploadBatch(filePaths, new UploadBatchListener() {
//            @Override
//            public void onSuccess(List<BmobFile> files, List<String> urls) {
//                if (urls.size() == filePaths.length) {
//                    savePic(AppUtils.getObjectId(GalleryActivity.this), urls);
//                }
//            }
//
//            @Override
//            public void onProgress(int i, int i1, int i2, int i3) {
//
//            }
//
//            @Override
//            public void onError(int statusCode, String errorMsg) {
//                showToast("错误码" + statusCode + ",错误描述：" + errorMsg);
//            }
//        });
    }

    private void savePic(String id, List<String> urls) {
        if (urls == null || urls.size() == 0 || TextUtils.isEmpty(id)) return;

        final List<PhotoInfo> list = new ArrayList<>();
        for (String url : urls) {
            PhotoInfo photo = new PhotoInfo();
            photo.setViewType(PhotoInfo.AlumViewType.ALUM_COMMON);
            photo.setPhoto_url(url);
            list.add(photo);
        }
//        mPersonInfo.getAlbum().addAll(list);
//        mPersonInfo.update(id, new UpdateListener() {
//            @Override
//            public void done(BmobException e) {
//                if (e == null) {
//                    showToast(R.string.upload_success);
//                    mMaxPicCount = mMaxPicCount - list.size();
//                    mAlumView.addData(list);
//                    mAlumView.notifyDataSetChanged();
//                    ACache.get(GalleryActivity.this).put(Config.SAVE_USER_KEY, mPersonInfo);
//                } else {
//                    showToast(R.string.upload_err);
//                }
//            }
//        });
    }
}
