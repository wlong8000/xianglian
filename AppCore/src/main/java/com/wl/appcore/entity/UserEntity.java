package com.wl.appcore.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanglong on 17/8/19.
 */

public class UserEntity implements Serializable, MultiItemEntity {
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
//    private String nickname;
    private String work_area;
    private String age;
    private String height;
    private String birthday;
    private String weight;
    private String work_area_name;
    private String born_area_name;

    private String user_sign;
    private String username;

    private int education;
    private int id;

    private String gender;

    private String black_user;
    private String member_level;
    private String member_end_date;
    private String mobile;

    private int is_top;
    private int position;

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

    public String getUser_sign() {
        return user_sign;
    }

    public void setUser_sign(String user_sign) {
        this.user_sign = user_sign;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBlack_user() {
        return black_user;
    }

    public void setBlack_user(String black_user) {
        this.black_user = black_user;
    }

    public String getMember_level() {
        return member_level;
    }

    public void setMember_level(String member_level) {
        this.member_level = member_level;
    }

    public String getMember_end_date() {
        return member_end_date;
    }

    public void setMember_end_date(String member_end_date) {
        this.member_end_date = member_end_date;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIs_top() {
        return is_top;
    }

    public void setIs_top(int is_top) {
        this.is_top = is_top;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * 职业 (0, '未透露'), (1, "在校学生"), (2, "私营业主"), (3, "农业劳动者"), (4, "企业职工"), (5, "政府机关/事业单位"), (6, "自由职业")
     */
    private int career;
    /**
     * 收入 (0, "5k以下"),(1, "5K-8k"), (2, "8k-12k"), (3, "12k-20k"), (4, "20k-30k"), (5, "30k以上")
     */
    private int income;
    private String person_intro;
    private String relationship_desc;
    private int marriage_status;
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

//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }

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

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
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

    public int getMarriage_status() {
        return marriage_status;
    }

    public void setMarriage_status(int marriage_status) {
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
}
