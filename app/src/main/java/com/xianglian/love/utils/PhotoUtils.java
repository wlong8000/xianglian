package com.xianglian.love.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.xianglian.love.R;
import com.xianglian.love.dialog.SelectPicAlertDialog;
import com.xianglian.love.model.Album;
import com.xianglian.love.model.TakePhoto;

/**
 * Created by wanglong on 17/4/3.
 */

public class PhotoUtils {
    public final static int PHOTO = 1;

    public final static int ALBUM = 2;

    public final static int REQUEST_CODE_PHOTO_CUT = 3;

    public final static int REQUEST_CODE_ALBUM_CUT = 4;

    public static void changeHead(final Activity context, final TakePhoto function, final Album album) {
        SelectPicAlertDialog dialog = new SelectPicAlertDialog(context, context.getString(R.string.set_avt)) {
            @Override
            public void takePhoto() {
                Intent takePhoto = function.setIntent(AppUtils.getPicturePath(context) + "avatar/");
                context.startActivityForResult(takePhoto, PHOTO);
            }

            @Override
            public void useNativeGalley() {
                Intent it = album.setIntent(AppUtils.getPicturePath(context) + "avatar/",
                        "image/jpeg");
                context.startActivityForResult(it, ALBUM);
            }
        };
        dialog.show();

    }

    public static Intent getCropImageIntent(Uri fromPhotoUri, Uri toPhotoUri) {
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
}
