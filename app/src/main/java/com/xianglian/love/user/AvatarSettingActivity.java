package com.xianglian.love.user;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xianglian.love.R;
import com.xianglian.love.view.CutPictureAty;

import java.io.File;


/**
 * Created by Administrator on 2017/3/8.
 * 设置头像
 */
public class AvatarSettingActivity extends BaseUserInfoActivity implements View.OnClickListener {

    //头像
//    @InjectView(R.id.head_img)
    SimpleDraweeView mAvatarView;

    //个人简介
//    @InjectView(R.id.center_personal_resume_et)
    EditText mPersonalInfo;

    private PopupWindow mPopupWindow;

    private final int CAMERA_WITH_DATA = 1;

    /**
     * 本地图片选取标志
     */
    private static final int FLAG_CHOOSE_IMG = 2;

    /**
     * 截取结束标志
     */
    private static final int FLAG_MODIFY_FINISH = 3;

    public static final String TMP_PATH = "temp.jpg";

    public static final String PATH_KEY = "path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_personal_image_layout);
//        ButterKnife.inject(this);
        initView();
    }

    private void initView() {
        setupTitle(R.string.set_avatar);
        mPersonalInfo.setOnClickListener(this);
        mAvatarView.setOnClickListener(this);
        findViewById(R.id.bottom_complete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //头像
            case R.id.head_img:
                setupAvatarPop();
                break;
            //完成
            case R.id.bottom_complete:
                saveUserInfo();
                break;
            case R.id.bottom_rl_cancel_select_photo:
                if (mPopupWindow != null && mPopupWindow.isShowing())
                    mPopupWindow.dismiss();
                break;
            case R.id.cancel_select_photo_take_photo:
                //拍照
                startCamera();
                break;
            case R.id.cancel_select_photo_package:
                //相册获取
                startAlbum();
                break;
        }
    }

    /**
     * 上传头像
     *
     * @param path
     */
    private void uploadAvatar(String path) {
//        final BmobFile bmobFile = new BmobFile(new File(path));
//        bmobFile.uploadblock(new UploadFileListener() {
//            @Override
//            public void done(BmobException e) {
//                String url = bmobFile.getFileUrl();
//                if (!TextUtils.isEmpty(url)) {
//                    toast(R.string.text_upload_avatar_success);
//                    mAvatarView.setImageURI(url);
//                    mPersonInfo.setAvatar(url);
//                } else {
//                    toast(R.string.text_upload_avatar_fail);
//                }
//            }
//        });
    }

    /**
     * 保存用户信息
     */
    private void saveUserInfo() {
//        mPersonInfo.save(new SaveListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    toast(R.string.text_save_user_info_success);
//                    startActivity(new Intent(AvatarSettingActivity.this, MainActivity.class));
//                    finish();
//                } else {
//                    toast(R.string.text_save_user_info_fail);
//                    loge(e);
//                }
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FLAG_CHOOSE_IMG && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                if (!TextUtils.isEmpty(uri.getAuthority())) {
                    Cursor cursor = getContentResolver().query(uri,
                            new String[]{MediaStore.Images.Media.DATA},
                            null, null, null);
                    if (null == cursor) {
                        toast(R.string.empty_pic);
                        return;
                    }
                    cursor.moveToFirst();
                    String path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    cursor.close();

                    Intent intent = new Intent(this, CutPictureAty.class);
                    intent.putExtra(PATH_KEY, path);
                    startActivityForResult(intent, FLAG_MODIFY_FINISH);
                } else {
                    Intent intent = new Intent(this, CutPictureAty.class);
                    intent.putExtra(PATH_KEY, uri.getPath());
                    startActivityForResult(intent, FLAG_MODIFY_FINISH);
                }
            }
        } else if (requestCode == FLAG_MODIFY_FINISH && resultCode == RESULT_OK) {
            if (data != null) {
                final String path = data.getStringExtra(PATH_KEY);
                uploadAvatar(path);
            }
        }
        switch (requestCode) {
            case CAMERA_WITH_DATA:
                // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + TMP_PATH);
                break;
        }
    }

    // 裁剪图片的Activity
    private void startCropImageActivity(String path) {
        Intent intent = new Intent(this, CutPictureAty.class);
        intent.putExtra("path", path);
        startActivityForResult(intent, FLAG_MODIFY_FINISH);
    }

    private void startAlbum() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, FLAG_CHOOSE_IMG);
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), TMP_PATH)));
        startActivityForResult(intent, CAMERA_WITH_DATA);
    }

    private void setupAvatarPop() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.get_image_from_camera_or_photopackage_poplayout, null);

        view.findViewById(R.id.cancel_select_photo_take_photo).setOnClickListener(this);
        view.findViewById(R.id.cancel_select_photo_package).setOnClickListener(this);
        view.findViewById(R.id.bottom_rl_cancel_select_photo).setOnClickListener(this);

        mPopupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        mPopupWindow.update();
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setTouchable(true);
        WindowManager windowManager = getWindowManager();
        int width = windowManager.getDefaultDisplay().getWidth();
        if (!mPopupWindow.isShowing()) {
            mPopupWindow.showAsDropDown(mTitleBarView, width, 0);
        }
        setBackgroundAlpha(0.2f);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams windowManagerLay = getWindow().getAttributes();
        windowManagerLay.alpha = bgAlpha;
        getWindow().setAttributes(windowManagerLay);
    }

}
