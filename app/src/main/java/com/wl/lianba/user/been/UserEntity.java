
package com.wl.lianba.user.been;

import android.os.Parcel;
import android.os.Parcelable;

public class UserEntity implements Parcelable {
    private String msg;
    private int code;

    private UserEntity result;
    private String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserEntity getResult() {
        return result;
    }

    public void setResult(UserEntity result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeInt(this.code);
        dest.writeParcelable(this.result, flags);
        dest.writeString(this.token);
    }

    public UserEntity() {
    }

    protected UserEntity(Parcel in) {
        this.msg = in.readString();
        this.code = in.readInt();
        this.result = in.readParcelable(UserEntity.class.getClassLoader());
        this.token = in.readString();
    }

    public static final Creator<UserEntity> CREATOR = new Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };
}
