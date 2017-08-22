package com.wl.lianba.main.home.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wanglong on 17/8/22.
 */

public class MessageEntity implements Parcelable {
    private String id;
    private String sender_id;
    private String sender_name;
    private String sender_avatar;
    private String receiver_id;
    private String receiver_name;
    private String receiver_avatar;
    private String content;
    private String create_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_avatar() {
        return sender_avatar;
    }

    public void setSender_avatar(String sender_avatar) {
        this.sender_avatar = sender_avatar;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_avatar() {
        return receiver_avatar;
    }

    public void setReceiver_avatar(String receiver_avatar) {
        this.receiver_avatar = receiver_avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.sender_id);
        dest.writeString(this.sender_name);
        dest.writeString(this.sender_avatar);
        dest.writeString(this.receiver_id);
        dest.writeString(this.receiver_name);
        dest.writeString(this.receiver_avatar);
        dest.writeString(this.content);
        dest.writeString(this.create_time);
    }

    public MessageEntity() {
    }

    protected MessageEntity(Parcel in) {
        this.id = in.readString();
        this.sender_id = in.readString();
        this.sender_name = in.readString();
        this.sender_avatar = in.readString();
        this.receiver_id = in.readString();
        this.receiver_name = in.readString();
        this.receiver_avatar = in.readString();
        this.content = in.readString();
        this.create_time = in.readString();
    }

    public static final Parcelable.Creator<MessageEntity> CREATOR = new Parcelable.Creator<MessageEntity>() {
        @Override
        public MessageEntity createFromParcel(Parcel source) {
            return new MessageEntity(source);
        }

        @Override
        public MessageEntity[] newArray(int size) {
            return new MessageEntity[size];
        }
    };
}
