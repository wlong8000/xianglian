
package com.wl.lianba.model;

import android.content.Intent;
import android.provider.MediaStore;

public class Album {
    private String path;

    private String fileName;

    private String name;

    public Album() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Intent setIntent(String path, String type) {
        this.path = path;
        name = Long.toString(System.currentTimeMillis()) + ".jpg";
        fileName = path + name;
        Intent intent = new Intent(Intent.ACTION_PICK);
        return intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, type);
    }
}
