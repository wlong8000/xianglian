package com.xianglian.love.main.home.been;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.orhanobut.hawk.Hawk;
import com.wl.appcore.Keys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//    private String nickname;
    private String work_area_name;
    private String born_area_name;
    private String work_area_code;
    private String born_area_code;
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
    private String birthday;
    private String education;
    private String can_leave_message;
    private String mobile;

    private List<PhotoInfo> images;
    private List<PhotoInfo> results;
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

    private String sender_id;
    private String sender_name;
    private String sender_avatar;
    private String receiver_id;
    private String receiver_name;
    private String receiver_avatar;
    private String create_time;
    private boolean show_msg_more;
    private String pic1;
    private String username;

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

//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }

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

    public String getWork_area_code() {
        return work_area_code;
    }

    public void setWork_area_code(String work_area_code) {
        this.work_area_code = work_area_code;
    }

    public String getBorn_area_code() {
        return born_area_code;
    }

    public void setBorn_area_code(String born_area_code) {
        this.born_area_code = born_area_code;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCan_leave_message() {
        return can_leave_message;
    }

    public void setCan_leave_message(String can_leave_message) {
        this.can_leave_message = can_leave_message;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<PhotoInfo> getImages() {
        return images;
    }

    public void setImages(List<PhotoInfo> images) {
        this.images = images;
    }

    public List<PhotoInfo> getResults() {
        return results;
    }

    public void setResults(List<PhotoInfo> results) {
        this.results = results;
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

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public boolean isShow_msg_more() {
        return show_msg_more;
    }

    public void setShow_msg_more(boolean show_msg_more) {
        this.show_msg_more = show_msg_more;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int getItemType() {
        return viewType;
    }

    public UserDetailEntity() {
    }

    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(username)) {
            params.put("username", username);
        }
        if (!TextUtils.isEmpty(work_area_name)) {
            params.put("work_area_name", work_area_name);
        }
        if (!TextUtils.isEmpty(born_area_name)) {
            params.put("born_area_name", born_area_name);
        }

        if (!TextUtils.isEmpty(work_area_code)) {
            params.put("work_area_code", work_area_code);
        }
        if (!TextUtils.isEmpty(born_area_code)) {
            params.put("born_area_code", born_area_code);
        }

        if (!TextUtils.isEmpty(height)) {
            params.put("height", height);
        }
        if (!TextUtils.isEmpty(education)) {
            params.put("education", education);
        }
        if (!TextUtils.isEmpty(career)) {
            params.put("career", career);
        }

        if (!TextUtils.isEmpty(income)) {
            params.put("income", income);
        }
        if (!TextUtils.isEmpty(expect_marry_date)) {
            params.put("expect_marry_date", expect_marry_date);
        }
        if (!TextUtils.isEmpty(nationality)) {
            params.put("nationality", nationality);
        }

        if (!TextUtils.isEmpty(marriage_status)) {
            params.put("marriage_status", marriage_status);
        }
        if (!TextUtils.isEmpty(birth_index)) {
            params.put("birth_index", birth_index);
        }
        if (!TextUtils.isEmpty(has_children)) {
            params.put("has_children", has_children);
        }

        if (!TextUtils.isEmpty(weight)) {
            params.put("weight", weight);
        }
        if (!TextUtils.isEmpty(relationship_desc)) {
            params.put("relationship_desc", relationship_desc);
        }
        if (!TextUtils.isEmpty(mate_preference)) {
            params.put("mate_preference", mate_preference);
        }

        if (!TextUtils.isEmpty(has_car)) {
            params.put("has_car", has_car);
        }
        if (!TextUtils.isEmpty(has_house)) {
            params.put("has_house", has_house);
        }
        if (!TextUtils.isEmpty(birthday)) {
            params.put("birthday", birthday);
        }

        if (!TextUtils.isEmpty(person_intro)) {
            params.put("person_intro", person_intro);
        }
        String sex = Hawk.get(Keys.SEX);
        if (!TextUtils.isEmpty(sex)) {
            params.put("gender", sex);
        }
        return params;

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
        dest.writeString(this.work_area_name);
        dest.writeString(this.born_area_name);
        dest.writeString(this.work_area_code);
        dest.writeString(this.born_area_code);
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
        dest.writeString(this.birthday);
        dest.writeString(this.education);
        dest.writeString(this.can_leave_message);
        dest.writeString(this.mobile);
        dest.writeList(this.images);
        dest.writeList(this.results);
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
        dest.writeString(this.sender_id);
        dest.writeString(this.sender_name);
        dest.writeString(this.sender_avatar);
        dest.writeString(this.receiver_id);
        dest.writeString(this.receiver_name);
        dest.writeString(this.receiver_avatar);
        dest.writeString(this.create_time);
        dest.writeByte(this.show_msg_more ? (byte) 1 : (byte) 0);
        dest.writeString(this.pic1);
        dest.writeString(this.username);
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
        this.work_area_name = in.readString();
        this.born_area_name = in.readString();
        this.work_area_code = in.readString();
        this.born_area_code = in.readString();
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
        this.birthday = in.readString();
        this.education = in.readString();
        this.can_leave_message = in.readString();
        this.mobile = in.readString();
        this.images = new ArrayList<>();
        in.readList(this.images, PhotoInfo.class.getClassLoader());
        this.results = new ArrayList<>();
        in.readList(this.results, PhotoInfo.class.getClassLoader());
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
        this.sender_id = in.readString();
        this.sender_name = in.readString();
        this.sender_avatar = in.readString();
        this.receiver_id = in.readString();
        this.receiver_name = in.readString();
        this.receiver_avatar = in.readString();
        this.create_time = in.readString();
        this.show_msg_more = in.readByte() != 0;
        this.pic1 = in.readString();
        this.username = in.readString();
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
