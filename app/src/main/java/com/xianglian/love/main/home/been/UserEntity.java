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

    private int count;
    private String error;
    private String previous;
    private String next;

    private List<UserEntity> results;
    private String uid;
    private String account;
    private String token;
    /**
     * 是否认证 0 未认证 1 已认证
     */
    private int identity_verified;
    private String nickname;
    private String work_area;
    private String age;
    private String height;
    private String birthday;
    private String weight;
    private String work_area_name;
    private String born_area_name;

    private int education;
    private int id;

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWork_area_name() {
        return work_area_name;
    }

    public void setWork_area_name(String work_area_name) {
        this.work_area_name = work_area_name;
    }

    public String getBorn_area_name() {
        return born_area_name;
    }

    public void setBorn_area_name(String born_area_name) {
        this.born_area_name = born_area_name;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_like() {
        return is_like;
    }

    /**
     * 职业 (0, '未透露'), (1, "在校学生"), (2, "私营业主"), (3, "农业劳动者"), (4, "企业职工"), (5, "政府机关/事业单位"), (6, "自由职业")
     */
    private int career;
    /**
     * 收入 (0, '未透露'), (1, "3k以下"), (2, "3k-5k"), (3, "58k"), (4, "8k-12k"), (5, "12k-20k"), (6, "20k-30k"), (7, "30k以上")
     */
    private String income;
    private String person_intro;
    private String relationship_desc;
    private String marriage_status;
    private String mate_preference;
    private String like;
    private String pic1;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<UserEntity> getResults() {
        return results;
    }

    public void setResults(List<UserEntity> results) {
        this.results = results;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getRelationship_desc() {
        return relationship_desc;
    }

    public void setRelationship_desc(String relationship_desc) {
        this.relationship_desc = relationship_desc;
    }

    public String getMarriage_status() {
        return marriage_status;
    }

    public void setMarriage_status(String marriage_status) {
        this.marriage_status = marriage_status;
    }

    public String getMate_preference() {
        return mate_preference;
    }

    public void setMate_preference(String mate_preference) {
        this.mate_preference = mate_preference;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
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

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
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
        dest.writeInt(this.count);
        dest.writeString(this.error);
        dest.writeString(this.previous);
        dest.writeString(this.next);
        dest.writeTypedList(this.results);
        dest.writeString(this.uid);
        dest.writeString(this.account);
        dest.writeString(this.token);
        dest.writeInt(this.identity_verified);
        dest.writeString(this.nickname);
        dest.writeString(this.work_area);
        dest.writeString(this.age);
        dest.writeString(this.height);
        dest.writeString(this.birthday);
        dest.writeString(this.weight);
        dest.writeString(this.work_area_name);
        dest.writeString(this.born_area_name);
        dest.writeInt(this.education);
        dest.writeInt(this.id);
        dest.writeInt(this.career);
        dest.writeString(this.income);
        dest.writeString(this.person_intro);
        dest.writeString(this.relationship_desc);
        dest.writeString(this.marriage_status);
        dest.writeString(this.mate_preference);
        dest.writeString(this.like);
        dest.writeString(this.pic1);
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
        this.count = in.readInt();
        this.error = in.readString();
        this.previous = in.readString();
        this.next = in.readString();
        this.results = in.createTypedArrayList(UserEntity.CREATOR);
        this.uid = in.readString();
        this.account = in.readString();
        this.token = in.readString();
        this.identity_verified = in.readInt();
        this.nickname = in.readString();
        this.work_area = in.readString();
        this.age = in.readString();
        this.height = in.readString();
        this.birthday = in.readString();
        this.weight = in.readString();
        this.work_area_name = in.readString();
        this.born_area_name = in.readString();
        this.education = in.readInt();
        this.id = in.readInt();
        this.career = in.readInt();
        this.income = in.readString();
        this.person_intro = in.readString();
        this.relationship_desc = in.readString();
        this.marriage_status = in.readString();
        this.mate_preference = in.readString();
        this.like = in.readString();
        this.pic1 = in.readString();
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
