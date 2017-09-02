package com.xianglian.love.main.home.been;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanglong on 17/8/19.
 */

public class UserDetailEntity implements Parcelable, MultiItemEntity {
    /**
     * 详情页 分类
     */
    public interface ViewType {
        /**
         * 头部信息
         */
        int TOP_INFO = 0;

        /**
         * 个人简介
         */
        int INTRODUCE = 1;

        /**
         * 情感经历
         */
        int EXPERIENCE_EMOTION = 2;

        /**
         * 兴趣爱好
         */
        int FAVORITE = 3;

        /**
         * 个人标签
         */
        int MARK = 4;

        /**
         * 相册
         */
        int ALBUM = 5;

        /**
         * 基本资料
         */
        int BASE_INFO = 6;

        /**
         * 留言
         */
        int LEAVE_MESSAGE = 7;

        /**
         * 标题
         */
        int TITLE = 8;
    }
    private String msg;
    private int code;
    private String error;
    private int viewType;

    private UserDetailEntity result;
    private String person_id;
    private String account;
    private boolean identity_verified;
    private String nickname;
    private String work_area_name;
    private String born_area_name;
    private String age;
    private String height;
    private String career;
    private String income;
    private String person_intro;
    private String like;
    private String expect_marry_date;
    private String nationality;
    private String marriage_status;
    private String birth_index;
    private String has_children;
    private String weight;
    private String avatar;
    private String vip;
    private String has_car;
    private String has_house;
    private String relationship_desc;
    private String mate_preference;
    private String constellation;
    private String birth_date;
    private String education;
    private String can_leave_message;

    private List<PhotoInfo> albums;
    private String id;
    private String photo_url;

    private List<UserDetailEntity> tags;
    private String tag_name;

    private List<UserDetailEntity> interests;
    private String interest_id;
    private String interest_name;

    private List<UserDetailEntity> contact_result;
    private String type_id;
    private String type_name;
    private String status;
    private String content;

    private List<MessageEntity> messages;
    private String messages_count;
    private String is_like;

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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public UserDetailEntity getResult() {
        return result;
    }

    public void setResult(UserDetailEntity result) {
        this.result = result;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isIdentity_verified() {
        return identity_verified;
    }

    public void setIdentity_verified(boolean identity_verified) {
        this.identity_verified = identity_verified;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getPerson_intro() {
        return person_intro;
    }

    public void setPerson_intro(String person_intro) {
        this.person_intro = person_intro;
    }

    public String getExpect_marry_date() {
        return expect_marry_date;
    }

    public void setExpect_marry_date(String expect_marry_date) {
        this.expect_marry_date = expect_marry_date;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMarriage_status() {
        return marriage_status;
    }

    public void setMarriage_status(String marriage_status) {
        this.marriage_status = marriage_status;
    }

    public String getBirth_index() {
        return birth_index;
    }

    public void setBirth_index(String birth_index) {
        this.birth_index = birth_index;
    }

    public String getHas_children() {
        return has_children;
    }

    public void setHas_children(String has_children) {
        this.has_children = has_children;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getHas_car() {
        return has_car;
    }

    public void setHas_car(String has_car) {
        this.has_car = has_car;
    }

    public String getHas_house() {
        return has_house;
    }

    public void setHas_house(String has_house) {
        this.has_house = has_house;
    }

    public String getRelationship_desc() {
        return relationship_desc;
    }

    public void setRelationship_desc(String relationship_desc) {
        this.relationship_desc = relationship_desc;
    }

    public String getMate_preference() {
        return mate_preference;
    }

    public void setMate_preference(String mate_preference) {
        this.mate_preference = mate_preference;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getCan_leave_message() {
        return can_leave_message;
    }

    public void setCan_leave_message(String can_leave_message) {
        this.can_leave_message = can_leave_message;
    }

    public List<PhotoInfo> getAlbums() {
        return albums;
    }

    public void setAlbums(List<PhotoInfo> albums) {
        this.albums = albums;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public List<UserDetailEntity> getTags() {
        return tags;
    }

    public void setTags(List<UserDetailEntity> tags) {
        this.tags = tags;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public List<UserDetailEntity> getInterests() {
        return interests;
    }

    public void setInterests(List<UserDetailEntity> interests) {
        this.interests = interests;
    }

    public String getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(String interest_id) {
        this.interest_id = interest_id;
    }

    public String getInterest_name() {
        return interest_name;
    }

    public void setInterest_name(String interest_name) {
        this.interest_name = interest_name;
    }

    public List<UserDetailEntity> getContact_result() {
        return contact_result;
    }

    public void setContact_result(List<UserDetailEntity> contact_result) {
        this.contact_result = contact_result;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getStatus() {
        return status;
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

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MessageEntity> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageEntity> messages) {
        this.messages = messages;
    }

    public String getMessages_count() {
        return messages_count;
    }

    public void setMessages_count(String messages_count) {
        this.messages_count = messages_count;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public int getItemType() {
        return viewType;
    }

    public UserDetailEntity() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.msg);
        dest.writeInt(this.code);
        dest.writeString(this.error);
        dest.writeInt(this.viewType);
        dest.writeParcelable(this.result, flags);
        dest.writeString(this.person_id);
        dest.writeString(this.account);
        dest.writeByte(this.identity_verified ? (byte) 1 : (byte) 0);
        dest.writeString(this.nickname);
        dest.writeString(this.work_area_name);
        dest.writeString(this.born_area_name);
        dest.writeString(this.age);
        dest.writeString(this.height);
        dest.writeString(this.career);
        dest.writeString(this.income);
        dest.writeString(this.person_intro);
        dest.writeString(this.like);
        dest.writeString(this.expect_marry_date);
        dest.writeString(this.nationality);
        dest.writeString(this.marriage_status);
        dest.writeString(this.birth_index);
        dest.writeString(this.has_children);
        dest.writeString(this.weight);
        dest.writeString(this.avatar);
        dest.writeString(this.vip);
        dest.writeString(this.has_car);
        dest.writeString(this.has_house);
        dest.writeString(this.relationship_desc);
        dest.writeString(this.mate_preference);
        dest.writeString(this.constellation);
        dest.writeString(this.birth_date);
        dest.writeString(this.education);
        dest.writeString(this.can_leave_message);
        dest.writeList(this.albums);
        dest.writeString(this.id);
        dest.writeString(this.photo_url);
        dest.writeTypedList(this.tags);
        dest.writeString(this.tag_name);
        dest.writeTypedList(this.interests);
        dest.writeString(this.interest_id);
        dest.writeString(this.interest_name);
        dest.writeTypedList(this.contact_result);
        dest.writeString(this.type_id);
        dest.writeString(this.type_name);
        dest.writeString(this.status);
        dest.writeString(this.content);
        dest.writeTypedList(this.messages);
        dest.writeString(this.messages_count);
        dest.writeString(this.is_like);
    }

    protected UserDetailEntity(Parcel in) {
        this.msg = in.readString();
        this.code = in.readInt();
        this.error = in.readString();
        this.viewType = in.readInt();
        this.result = in.readParcelable(UserDetailEntity.class.getClassLoader());
        this.person_id = in.readString();
        this.account = in.readString();
        this.identity_verified = in.readByte() != 0;
        this.nickname = in.readString();
        this.work_area_name = in.readString();
        this.born_area_name = in.readString();
        this.age = in.readString();
        this.height = in.readString();
        this.career = in.readString();
        this.income = in.readString();
        this.person_intro = in.readString();
        this.like = in.readString();
        this.expect_marry_date = in.readString();
        this.nationality = in.readString();
        this.marriage_status = in.readString();
        this.birth_index = in.readString();
        this.has_children = in.readString();
        this.weight = in.readString();
        this.avatar = in.readString();
        this.vip = in.readString();
        this.has_car = in.readString();
        this.has_house = in.readString();
        this.relationship_desc = in.readString();
        this.mate_preference = in.readString();
        this.constellation = in.readString();
        this.birth_date = in.readString();
        this.education = in.readString();
        this.can_leave_message = in.readString();
        this.albums = new ArrayList<PhotoInfo>();
        in.readList(this.albums, PhotoInfo.class.getClassLoader());
        this.id = in.readString();
        this.photo_url = in.readString();
        this.tags = in.createTypedArrayList(UserDetailEntity.CREATOR);
        this.tag_name = in.readString();
        this.interests = in.createTypedArrayList(UserDetailEntity.CREATOR);
        this.interest_id = in.readString();
        this.interest_name = in.readString();
        this.contact_result = in.createTypedArrayList(UserDetailEntity.CREATOR);
        this.type_id = in.readString();
        this.type_name = in.readString();
        this.status = in.readString();
        this.content = in.readString();
        this.messages = in.createTypedArrayList(MessageEntity.CREATOR);
        this.messages_count = in.readString();
        this.is_like = in.readString();
    }

    public static final Creator<UserDetailEntity> CREATOR = new Creator<UserDetailEntity>() {
        @Override
        public UserDetailEntity createFromParcel(Parcel source) {
            return new UserDetailEntity(source);
        }

        @Override
        public UserDetailEntity[] newArray(int size) {
            return new UserDetailEntity[size];
        }
    };
}
