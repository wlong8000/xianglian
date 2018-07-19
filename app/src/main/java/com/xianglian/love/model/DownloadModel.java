
package com.xianglian.love.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hujinghui on 14-9-19.
 */
public class DownloadModel implements Parcelable {

    public String title;

    public String name;

    public String desc;

    public String url;

    public String notificationTitle;

    public String notificationDesc;

    public String filename;

    public boolean upgrade = false;

    public long downId;

    public String cancel;

    public String confirm;

    public String file_size;

    public String downloads;

    private int is_mandatory;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String fileName;

    public DownloadModel() {
    }

    public DownloadModel(String url, long downId, String filename) {
        this.url = url;
        this.downId = downId;
        this.filename=filename;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getDownloads() {
        return downloads;
    }

    public void setDownloads(String downloads) {
        this.downloads = downloads;
    }
    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public boolean isUpgrade() {
        return upgrade;
    }

    public void setUpgrade(boolean upgrade) {
        this.upgrade = upgrade;
    }

    public String getNotificationDesc() {
        return notificationDesc;
    }

    public void setNotificationDesc(String notificationDesc) {
        this.notificationDesc = notificationDesc;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getDownId() {
        return downId;
    }

    public void setDownId(long downId) {
        this.downId = downId;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIs_mandatory() {
        return is_mandatory;
    }

    public void setIs_mandatory(int is_mandatory) {
        this.is_mandatory = is_mandatory;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.desc);
        dest.writeString(this.url);
        dest.writeString(this.notificationTitle);
        dest.writeString(this.notificationDesc);
        dest.writeString(this.filename);
        dest.writeByte(this.upgrade ? (byte) 1 : (byte) 0);
        dest.writeLong(this.downId);
        dest.writeString(this.cancel);
        dest.writeString(this.confirm);
        dest.writeString(this.file_size);
        dest.writeString(this.downloads);
    }

    protected DownloadModel(Parcel in) {
        this.title = in.readString();
        this.name = in.readString();
        this.desc = in.readString();
        this.url = in.readString();
        this.notificationTitle = in.readString();
        this.notificationDesc = in.readString();
        this.filename = in.readString();
        this.upgrade = in.readByte() != 0;
        this.downId = in.readLong();
        this.cancel = in.readString();
        this.confirm = in.readString();
        this.file_size = in.readString();
        this.downloads = in.readString();
    }

    public static final Creator<DownloadModel> CREATOR = new Creator<DownloadModel>() {
        @Override
        public DownloadModel createFromParcel(Parcel source) {
            return new DownloadModel(source);
        }

        @Override
        public DownloadModel[] newArray(int size) {
            return new DownloadModel[size];
        }
    };
}
