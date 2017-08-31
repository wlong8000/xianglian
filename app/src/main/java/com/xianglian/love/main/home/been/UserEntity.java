package com.xianglian.love.main.home.been;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by wanglong on 17/8/19.
 */

public class UserEntity implements Parcelable, MultiItemEntity {
    public static final int TYPE_NORMAL = 1;
    private String msg;
    private int code;
    private int viewType;

    private UserEntity result;
    private int total;
    private String error;

    private List<UserEntity> person_list;
    private String uid;
    private String account;
    /**
     * 是否认证 0 未认证 1 已认证
     */
    private int identity_verified;
    private String nickname;
    private String work_area;
    private String age;
    private String height;
    /**
     * 职业 (0, '未透露'), (1, "在校学生"), (2, "私营业主"), (3, "农业劳动者"), (4, "企业职工"), (5, "政府机关/事业单位"), (6, "自由职业")
     */
    private int career;
    /**
     * 收入 (0, '未透露'), (1, "3k以下"), (2, "3k-5k"), (3, "58k"), (4, "8k-12k"), (5, "12k-20k"), (6, "20k-30k"), (7, "30k以上")
     */
    private String income;
    private String person_intro;
    private int like;
    private String avatar;
    /**
     * 类型 1 条目 2 广告
     */
    private int type;

    private UserEntity content;
    private String url;
    private String name;
    private String img;
    private String desc;
    private boolean is_like;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<UserEntity> getPerson_list() {
        return person_list;
    }

    public void setPerson_list(List<UserEntity> person_list) {
        this.person_list = person_list;
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

    public int getIdentity_verified() {
        return identity_verified;
    }

    public void setIdentity_verified(int identity_verified) {
        this.identity_verified = identity_verified;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getWork_area() {
        return work_area;
    }

    public void setWork_area(String work_area) {
        this.work_area = work_area;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getCareer() {
        return career;
    }

    public void setCareer(int career) {
        this.career = career;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getPerson_intro() {
        return person_intro;
    }

    public void setPerson_intro(String person_intro) {
        this.person_intro = person_intro;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserEntity getContent() {
        return content;
    }

    public void setContent(UserEntity content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean is_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public UserEntity() {
    }

    @Override
    public int getItemType() {
        return viewType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeInt(this.code);
        dest.writeInt(this.viewType);
        dest.writeParcelable(this.result, flags);
        dest.writeInt(this.total);
        dest.writeString(this.error);
        dest.writeTypedList(this.person_list);
        dest.writeString(this.uid);
        dest.writeString(this.account);
        dest.writeInt(this.identity_verified);
        dest.writeString(this.nickname);
        dest.writeString(this.work_area);
        dest.writeString(this.age);
        dest.writeString(this.height);
        dest.writeInt(this.career);
        dest.writeString(this.income);
        dest.writeString(this.person_intro);
        dest.writeInt(this.like);
        dest.writeString(this.avatar);
        dest.writeInt(this.type);
        dest.writeParcelable(this.content, flags);
        dest.writeString(this.url);
        dest.writeString(this.name);
        dest.writeString(this.img);
        dest.writeString(this.desc);
        dest.writeByte(this.is_like ? (byte) 1 : (byte) 0);
    }

    protected UserEntity(Parcel in) {
        this.msg = in.readString();
        this.code = in.readInt();
        this.viewType = in.readInt();
        this.result = in.readParcelable(UserEntity.class.getClassLoader());
        this.total = in.readInt();
        this.error = in.readString();
        this.person_list = in.createTypedArrayList(UserEntity.CREATOR);
        this.uid = in.readString();
        this.account = in.readString();
        this.identity_verified = in.readInt();
        this.nickname = in.readString();
        this.work_area = in.readString();
        this.age = in.readString();
        this.height = in.readString();
        this.career = in.readInt();
        this.income = in.readString();
        this.person_intro = in.readString();
        this.like = in.readInt();
        this.avatar = in.readString();
        this.type = in.readInt();
        this.content = in.readParcelable(UserEntity.class.getClassLoader());
        this.url = in.readString();
        this.name = in.readString();
        this.img = in.readString();
        this.desc = in.readString();
        this.is_like = in.readByte() != 0;
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
