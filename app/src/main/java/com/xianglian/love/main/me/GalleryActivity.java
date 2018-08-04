package com.xianglian.love.main.me;

import android.content.Intent;
import android.os.Bundle;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.orhanobut.hawk.Hawk;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.wl.appcore.Keys;
import com.xianglian.love.R;
import com.xianglian.love.ShowPicActivity;
import com.xianglian.love.config.Config;
import com.xianglian.love.main.home.been.PhotoInfo;

import com.xianglian.love.main.home.been.UserDetailEntity;
import com.wl.appcore.entity.UserEntity;
import com.xianglian.love.net.JsonCallBack;
import com.xianglian.love.net.MD5Util;
import com.xianglian.love.user.BaseUserInfoActivity;
import com.xianglian.love.utils.AppUtils;
import com.xianglian.love.utils.Trace;
import com.xianglian.love.view.AlumView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class GalleryActivity extends BaseUserInfoActivity implements AlumView.OnItemClickListener {
    //相册封装好的view
    private AlumView mAlumView;

    private static final int MAX_PIC = 3;

    private int mMaxPicCount = MAX_PIC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        setupCommonTitle(getString(R.string.my_album));
        setupViews();
    }

    private void setupViews() {
        mAlumView = (AlumView) findViewById(R.id.layout_alum);
        mAlumView.setOnItemClickListener(this);
        mAlumView.initAdapter(3);
        doRequest();
    }

    private void addData(List<PhotoInfo> albums) {
        if (albums != null && !albums.isEmpty()) {
            addAlumData(albums);
        }
        if (mMaxPicCount > 0) {
            addDataByType(PhotoInfo.AlumViewType.ALUM_ADD, null);
        }
        mAlumView.notifyDataSetChanged();
    }

    private void addAlumData(List<PhotoInfo> paths) {
        if (paths == null) return;
        List<PhotoInfo> items = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            PhotoInfo info = new PhotoInfo();
            info.setViewType(PhotoInfo.AlumViewType.ALUM_COMMON);
            info.setImage_url(paths.get(i).getImage_url());
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
                info.setImage_url(path);
                mAlumView.addData(info);
                break;
            }
        }
    }

    private void doRequest() {
        dialogShow();
        GetRequest<UserDetailEntity> request = OkGo.get(Config.PATH + "qn_token/");
        request.headers("Authorization", AppUtils.getToken(this));
        request.execute(new JsonCallBack<UserDetailEntity>(UserDetailEntity.class) {
            @Override
            public void onSuccess(Response<UserDetailEntity> response) {
                dialogDisMiss();
                UserDetailEntity entity = response.body();
                if (entity == null || entity.getResults() == null) {
                    addData(null);
                    return;
                }
                mMaxPicCount = mMaxPicCount - entity.getResults().size();
                addData(entity.getResults());
            }

            @Override
            public void onError(Response<UserDetailEntity> response) {
                super.onError(response);
                dialogDisMiss();
                showToast(getResources().getString(R.string.please_check_network));
//                addData(null);
            }
        });
    }

    @Override
    public void onItemClick(int position, PhotoInfo info) {
        if (info == null) return;
        if (info.getViewType() == PhotoInfo.AlumViewType.ALUM_ADD) {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())
                    .compress(true)
                    .maxSelectNum(mMaxPicCount)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        } else {
            List<PhotoInfo> data = mPersonInfo.getAlbum();
            if (data == null) return;
            String[] arr = new String[data.size()];
            for (int i = 0; i < data.size(); i++) {
                arr[i] = data.get(i).getImage_url();
            }
            ShowPicActivity.showPictures(GalleryActivity.this, arr, position - 1);
        }
    }

    private List<PhotoInfo> mLocalImages;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    mLocalImages = new ArrayList<>();
                    for (LocalMedia media: selectList) {
                        PhotoInfo photoInfo = new PhotoInfo();
                        photoInfo.setViewType(PhotoInfo.AlumViewType.ALUM_COMMON);
                        photoInfo.setImage_url(media.getCompressPath());
                        mLocalImages.add(photoInfo);
                    }
                    dialogShow();
                    getQnToken(mLocalImages.get(0).getImage_url());
                    mAlumView.addData(mLocalImages);
                    List<PhotoInfo> list = mAlumView.getData();
                    if (list.size() > 3) list = list.subList(0, 3);
                    mAlumView.setData(list);
                    mAlumView.notifyDataSetChanged();
                    mMaxPicCount = mMaxPicCount - mLocalImages.size();
                    break;
            }
        }
    }

    private void getQnToken(final String path) {
        PostRequest<UserEntity> request = OkGo.post(Config.PATH + "qn_token/");
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss", Locale.getDefault());
        final String date = df.format(new Date());
        String text = path + System.currentTimeMillis();
        final String fileName = date + "/" + MD5Util.encrypt(text);
        request.headers("Authorization", AppUtils.getToken(this));
        request.params("file_name", fileName);
        request.params("type", "1");
        request.execute(new JsonCallBack<UserEntity>(UserEntity.class) {
            @Override
            public void onSuccess(Response<UserEntity> response) {
                UserEntity entity = response.body();
                if (entity == null) return;
                String token = entity.getToken();

                Trace.d(TAG, "token : " + token);
                uploadAvatar(token, path, fileName);
            }

            @Override
            public void onError(Response<UserEntity> response) {
                super.onError(response);
                dialogDisMiss();
            }
        });
    }

    private void uploadAvatar(String token, String path, String fileName) {
        UploadManager uploadManager = new UploadManager();
        uploadManager.put(path, fileName, token,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if (info.isOK()) {
                            if (mLocalImages.size() > 0) {
                                mLocalImages.remove(0);
                                if (mLocalImages.size() > 0) {
                                    getQnToken(mLocalImages.get(0).getImage_url());
                                } else {
                                    dialogDisMiss();
                                    UserEntity userEntity = Hawk.get(Keys.USER_INFO);
                                    if (userEntity != null) {
                                        userEntity.setHas_album(true);
                                    }
                                }
                            }
                            Trace.i("qiniu", "Upload Success");
                        } else {
                            Trace.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                        Trace.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                    }
                }, null);
    }
}
