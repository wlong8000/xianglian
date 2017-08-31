
package com.xianglian.love.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import com.xianglian.love.utils.ImageTools;

import java.io.File;

public class TakePhoto {
    Bitmap bitmap;

    Bitmap newBitmap;

    AttachmentEntity attachment;

    private int REQUEST_CODE;

    private File destination;

    private String path;

    private String fileName;

    private String name;

    private int size;

    public TakePhoto(int requestCode) {
        REQUEST_CODE = requestCode;
    }

    public AttachmentEntity getAttachment() {
        return attachment;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName=fileName;
    }

    public Intent setIntent(String path) {
        this.path = path;
        name = Long.toString(System.currentTimeMillis()) + ".jpg";
        fileName = path + name;
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        destination = new File(fileName);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(destination));
        return intent;
    }

    public Bitmap getThumbPhoto(String path, String name) {
        bitmap = BitmapFactory.decodeFile(path + name);

        float rate = 1;
        float value = 300;
        if (bitmap.getWidth() > value) {
            rate = (float)bitmap.getWidth() / value;
            newBitmap = ImageTools.zoomBitmap(bitmap, bitmap.getWidth() / rate, bitmap.getHeight()
                    / rate, 0);
        } else {
            newBitmap = bitmap;
        }
        ImageTools.savePhotoToSDCard(newBitmap, path, name, 100);
        attachment = new AttachmentEntity();
        attachment.setName(name);
        attachment.setUrl(path + name);
        // Bitmap thumbBitmap = ImageTools.zoomBitmap(newBitmap,
        // newBitmap.getWidth() * 3 / 8,
        // newBitmap.getHeight() * 3 / 8, 0);
        // ImageTools
        // .savePhotoToSDCard(thumbBitmap, path, "thumb_" + name, 50);
        return newBitmap;
    }
}
