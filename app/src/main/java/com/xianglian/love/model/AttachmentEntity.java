
package com.xianglian.love.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.xianglian.love.utils.AppUtils;


public class AttachmentEntity implements Parcelable {
    public int imageIndex;// 圈子详情图片的索引
    public Long attachmentId;
    public String name;
    public String size;
    public String type;
    public String url;
    public String urlPre;
    public long msgId;
    public String thumb;
    public int width;
    public int height;
    public String mime;
    public String static_url;
    public String large;
    public String file_name;
    public String image_id;

    public static final String IMAGE_GIF = "image/gif";

    public AttachmentEntity() {
    }

    public Long getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(Long id) {
        this.attachmentId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlPre() {
        return urlPre;
    }

    public void setUrlPre(String urlPre) {
        this.urlPre = urlPre;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mine) {
        this.mime = mine;
    }

    public String getStatic_url() {
        return static_url;
    }

    public void setStatic_url(String static_url) {
        this.static_url = static_url;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof AttachmentEntity))
            return false;
        return attachmentId == ((AttachmentEntity) o).attachmentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.imageIndex);
        dest.writeValue(this.attachmentId);
        dest.writeString(this.name);
        dest.writeString(this.size);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeString(this.urlPre);
        dest.writeLong(this.msgId);
        dest.writeString(this.thumb);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeString(this.mime);
        dest.writeString(this.static_url);
        dest.writeString(this.large);
        dest.writeString(this.file_name);
        dest.writeString(this.image_id);
    }

    protected AttachmentEntity(Parcel in) {
        this.imageIndex = in.readInt();
        this.attachmentId = (Long) in.readValue(Long.class.getClassLoader());
        this.name = in.readString();
        this.size = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.urlPre = in.readString();
        this.msgId = in.readLong();
        this.thumb = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.mime = in.readString();
        this.static_url = in.readString();
        this.large = in.readString();
        this.file_name = in.readString();
        this.image_id = in.readString();
    }

    public static final Creator<AttachmentEntity> CREATOR = new Creator<AttachmentEntity>() {
        @Override
        public AttachmentEntity createFromParcel(Parcel source) {
            return new AttachmentEntity(source);
        }

        @Override
        public AttachmentEntity[] newArray(int size) {
            return new AttachmentEntity[size];
        }
    };

    public static boolean isGif(AttachmentEntity entity) {
        if (entity == null)
            return false;
        if (!TextUtils.isEmpty(entity.getMime()) && entity.getMime().equals(AttachmentEntity.IMAGE_GIF)) {
            return true;
        } else {
            return AppUtils.isGifImage(entity.getUrl());
        }
    }
}
