package com.xianglian.love.user.been;

import android.os.Parcel;
import android.os.Parcelable;

import com.xianglian.love.main.home.been.UserDetailEntity;

import java.util.List;

/**
 * Created by wanglong on 17/8/20.
 */

public class OwnerEntity implements Parcelable {
    private int code;
    private String msg;
    private String error;

    private OwnerEntity result;
    private OwnerEntity user_info;
    private String token;

    private String uid;
    private String account;
    private UserDetailEntity profile;
    private List<OwnerEntity> albums;
    private String photo_url;
    private String upload_time;
    private String deleted;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public OwnerEntity getResult() {
        return result;
    }

    public void setResult(OwnerEntity result) {
        this.result = result;
    }

    public OwnerEntity getUser_info() {
        return user_info;
    }

    public void setUser_info(OwnerEntity user_info) {
        this.user_info = user_info;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public UserDetailEntity getProfile() {
        return profile;
    }

    public void setProfile(UserDetailEntity profile) {
        this.profile = profile;
    }

    public List<OwnerEntity> getAlbums() {
        return albums;
    }

    public void setAlbums(List<OwnerEntity> albums) {
        this.albums = albums;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }


    public OwnerEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
        dest.writeString(this.error);
        dest.writeParcelable(this.result, flags);
        dest.writeParcelable(this.user_info, flags);
        dest.writeString(this.token);
        dest.writeString(this.uid);
        dest.writeString(this.account);
        dest.writeParcelable(this.profile, flags);
        dest.writeTypedList(this.albums);
        dest.writeString(this.photo_url);
        dest.writeString(this.upload_time);
        dest.writeString(this.deleted);
    }

    protected OwnerEntity(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
        this.error = in.readString();
        this.result = in.readParcelable(OwnerEntity.class.getClassLoader());
        this.user_info = in.readParcelable(OwnerEntity.class.getClassLoader());
        this.token = in.readString();
        this.uid = in.readString();
        this.account = in.readString();
        this.profile = in.readParcelable(UserDetailEntity.class.getClassLoader());
        this.albums = in.createTypedArrayList(OwnerEntity.CREATOR);
        this.photo_url = in.readString();
        this.upload_time = in.readString();
        this.deleted = in.readString();
    }

    public static final Creator<OwnerEntity> CREATOR = new Creator<OwnerEntity>() {
        @Override
        public OwnerEntity createFromParcel(Parcel source) {
            return new OwnerEntity(source);
        }

        @Override
        public OwnerEntity[] newArray(int size) {
            return new OwnerEntity[size];
        }
    };
}
