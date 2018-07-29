package com.xianglian.love.user.been;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xianglian.love.main.home.been.PersonInfo;
import com.xianglian.love.utils.AppUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.DateUtils2;

/**
 * 针对 用户信息编辑类
 */
public class ItemInfo implements MultiItemEntity, Parcelable {
	private Map<String, String> params = new HashMap<>();

	private String text;
	private String rightText;
	private String scheme;
	private String avatar;
	private int type;
	private int viewType;
	private boolean showLine;
	private PersonInfo info;
	private List<String> items;
	private List<List<String>> subItems;

	public String sex;
	private String min_age;
	private String max_age;
	private String min_height;
	private String max_height;
	public String education;
	public int min_education;
	public int max_education;
	public String career;
	public String income;
	public String expect_marry_date;
	public String marriage_status;
	public String min_weight;
	public String max_weight;
	public String has_car;
	public String has_children;
	public String has_house;
	public String is_student;
	public String vip;
	public String username;

	public String work_area_code;
	public String born_area_code;

	public String work_area_name;
	public String born_area_name;

	public String nationality;
	public String birth_index;
	public int phoneType;
	public Contacts contacts;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRightText() {
		return rightText;
	}

	public void setRightText(String rightText) {
		this.rightText = rightText;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public boolean isShowLine() {
		return showLine;
	}

	public void setShowLine(boolean showLine) {
		this.showLine = showLine;
	}

	public PersonInfo getInfo() {
		return info;
	}

	public void setInfo(PersonInfo info) {
		this.info = info;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public List<List<String>> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<List<String>> subItems) {
		this.subItems = subItems;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMin_age() {
		return min_age;
	}

	public void setMin_age(String min_age) {
		this.min_age = min_age;
	}

	public String getMax_age() {
		return max_age;
	}

	public void setMax_age(String max_age) {
		this.max_age = max_age;
	}

	public String getMin_height() {
		return min_height;
	}

	public void setMin_height(String min_height) {
		this.min_height = min_height;
	}

	public String getMax_height() {
		return max_height;
	}

	public void setMax_height(String max_height) {
		this.max_height = max_height;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public int getMin_education() {
		return min_education;
	}

	public void setMin_education(int min_education) {
		this.min_education = min_education;
	}

	public int getMax_education() {
		return max_education;
	}

	public void setMax_education(int max_education) {
		this.max_education = max_education;
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

	public String getExpect_marry_date() {
		return expect_marry_date;
	}

	public void setExpect_marry_date(String expect_marry_date) {
		this.expect_marry_date = expect_marry_date;
	}

	public String getMarriage_status() {
		return marriage_status;
	}

	public void setMarriage_status(String marriage_status) {
		this.marriage_status = marriage_status;
	}

	public String getMin_weight() {
		return min_weight;
	}

	public void setMin_weight(String min_weight) {
		this.min_weight = min_weight;
	}

	public String getMax_weight() {
		return max_weight;
	}

	public void setMax_weight(String max_weight) {
		this.max_weight = max_weight;
	}

	public String getHas_car() {
		return has_car;
	}

	public void setHas_car(String has_car) {
		this.has_car = has_car;
	}

	public String getHas_children() {
		return has_children;
	}

	public void setHas_children(String has_children) {
		this.has_children = has_children;
	}

	public String getHas_house() {
		return has_house;
	}

	public void setHas_house(String has_house) {
		this.has_house = has_house;
	}

	public String getIs_student() {
		return is_student;
	}

	public void setIs_student(String is_student) {
		this.is_student = is_student;
	}

	public String getVip() {
		return vip;
	}

	public void setVip(String vip) {
		this.vip = vip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getBirth_index() {
		return birth_index;
	}

	public void setBirth_index(String birth_index) {
		this.birth_index = birth_index;
	}

	public int getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(int phoneType) {
		this.phoneType = phoneType;
	}

	public Contacts getContacts() {
		return contacts;
	}

	public void setContacts(Contacts contacts) {
		this.contacts = contacts;
	}

	public ItemInfo() {

	}

	@Override
	public int getItemType() {
		return viewType;
	}


	public Map<String, String> toMap() {
		params.clear();
		if (AppUtils.stringToInt(min_age) >= 0) {
			params.put("max_age", DateUtils2.getBirthday(min_age) + "");
		}
		if (AppUtils.stringToInt(max_age) > 0) {
			params.put("min_age", DateUtils2.getBirthday(max_age) + "");
		}

		if (AppUtils.stringToInt(min_height) >= 0) {
			params.put("min_height", min_height);
		}
		if (AppUtils.stringToInt(max_height) > 0) {
			params.put("max_height", max_height);
		}

		if (min_education >= 0) {
			params.put("min_education", String.valueOf(min_education));
		}

		if (max_education > 0) {
			params.put("max_education", String.valueOf(max_education));
		}

		if (!TextUtils.isEmpty(work_area_code) && work_area_code.length() > 4) {
			params.put("work_area_code", work_area_code.substring(0, 4));
		}

		if (!TextUtils.isEmpty(born_area_code) && born_area_code.length() > 4) {
			params.put("born_area_code", born_area_code.substring(0, 4));
		}

		return params;
	}

	/**
	 * 个人信息编辑页 分类
	 */
	public interface ViewType {
		/**
		 * 头像
		 */
		int AVATAR = 0;

		/**
		 * 选择用户相应信息
		 */
		int PICK_SELECT = 1;

		/**
		 * 完成
		 */
		int COMPLETE = 2;

		/**
		 * 提示
		 */
		int TOAST = 3;

		/**
		 * 提示
		 */
		int TOAST2 = 4;

		/**
		 * 联系方式
		 */
		int PHONE = 5;
	}

	public interface Type {

		/**
		 * 个人简介
		 */
		int INTRODUCE = 1;

		/**
		 * 出生日期
		 */
		int BIRTHDAY = 2;

		/**
		 * 居住地(工作所在地）
		 */
		int APARTMENT = 3;

		/**
		 * 家乡
		 */
		int HOMETOWN = 4;

		/**
		 * 身高
		 */
		int HEIGHT = 5;

		/**
		 * 学历
		 */
		int EDUCATION = 6;

		/**
		 * 职业
		 */
		int PROFESSION = 7;

		/**
		 * 收入
		 */
		int INCOME = 8;

		/**
		 * 年龄
		 */
		int AGE = 9;
	}

	/**
	 * 设置
	 */
	public interface SettingType {

		/**
		 * 我的相册
		 */
		int MY_ALBUM = 1;

		/**
		 * 个人简介
		 */
		int INTRODUCE = 2;

		/**
		 * 个人信息
		 */
		int MY_INFO = 3;

		/**
		 * 身份认证
		 */
		int IDENTITY = 4;

		/**
		 * 是否购车
		 */
		int CAR = 5;

		/**
		 * 是否购房
		 */
		int HOUSE = 6;

		/**
		 * 情感经历
		 */
		int EXPERIENCE_LOVE = 7;

		/**
		 * 个人标签
		 */
		int MARK = 8;

		/**
		 * 兴趣爱好
		 */
		int HOBBY = 9;

		/**
		 * 设置
		 */
		int SETTING = 10;

		/**
		 * 成为会员
		 */
		int SET_VIP = 11;

		/**
		 * 联系方式
		 */
		int PHONE = 12;

		/**
		 * 择偶标准
		 */
		int CHOOSE_FRIEND_STANDARD = 13;

		/**
		 * 客服
		 */
		int CUSTOMER_AGENT = 14;

		/**
		 * 退出账号
		 */
		int EXIT_COUNT = 15;
	}

	/**
	 * 约见
	 */
	public interface MeetType {

		/**
		 * 有人想约我
		 */
		int AT_ME = 1;

		/**
		 * 我发出去的约见
		 */
		int ME_AT = 2;

		/**
		 * 最近来访
		 */
		int LAST_COME = 3;

		/**
		 * 心动记录
		 */
		int HEART_RECORD = 4;

		/**
		 * 心动通讯录
		 */
		int HEART_ADDRESS_BOOK = 5;
	}

	/**
	 * 我的个人信息
	 */
	public interface MyInfoType {
		/**
		 * 昵称
		 */
		int NICK_NAME = 1;

		/**
		 * 出生日期
		 */
		int BIRTHDAY = 2;

		/**
		 * 工作生活在
		 */
		int APARTMENT = 3;

		/**
		 * 出生地(籍贯)
		 */
		int HOMETOWN = 4;

		/**
		 * 身高
		 */
		int HEIGHT = 5;

		/**
		 * 学历
		 */
		int EDUCATION = 6;

		/**
		 * 职业
		 */
		int PROFESSION = 7;

		/**
		 * 年收入
		 */
		int INCOME = 8;


		/**
		 * 期望结婚时间
		 */
		int HOPE_MARRY = 9;

		/**
		 * 民族
		 */
		int NATION = 10;

		/**
		 * 婚姻状况
		 */
		int MARRY_STATE = 11;

		/**
		 * 家中排行
		 */
		int RANKING = 12;

		/**
		 * 有无子女
		 */
		int HAS_CHILD = 13;

		/**
		 * 体重
		 */
		int WEIGHT = 14;
	}

	/**
	 * 联系方式
	 */
	public interface PhoneType {
		int QQ = 0;
		int WEI_XIN = 1;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.params.size());
		for (Map.Entry<String, String> entry : this.params.entrySet()) {
			dest.writeString(entry.getKey());
			dest.writeString(entry.getValue());
		}
		dest.writeString(this.text);
		dest.writeString(this.rightText);
		dest.writeString(this.scheme);
		dest.writeString(this.avatar);
		dest.writeInt(this.type);
		dest.writeInt(this.viewType);
		dest.writeByte(this.showLine ? (byte) 1 : (byte) 0);
		dest.writeParcelable(this.info, flags);
		dest.writeStringList(this.items);
		dest.writeList(this.subItems);
		dest.writeString(this.sex);
		dest.writeString(this.min_age);
		dest.writeString(this.max_age);
		dest.writeString(this.min_height);
		dest.writeString(this.max_height);
		dest.writeString(this.education);
		dest.writeInt(this.min_education);
		dest.writeInt(this.max_education);
		dest.writeString(this.career);
		dest.writeString(this.income);
		dest.writeString(this.expect_marry_date);
		dest.writeString(this.marriage_status);
		dest.writeString(this.min_weight);
		dest.writeString(this.max_weight);
		dest.writeString(this.has_car);
		dest.writeString(this.has_children);
		dest.writeString(this.has_house);
		dest.writeString(this.is_student);
		dest.writeString(this.vip);
		dest.writeString(this.username);
		dest.writeString(this.work_area_code);
		dest.writeString(this.born_area_code);
		dest.writeString(this.work_area_name);
		dest.writeString(this.born_area_name);
		dest.writeString(this.nationality);
		dest.writeString(this.birth_index);
		dest.writeInt(this.phoneType);
		dest.writeParcelable(this.contacts, flags);
	}

	protected ItemInfo(Parcel in) {
		int paramsSize = in.readInt();
		this.params = new HashMap<String, String>(paramsSize);
		for (int i = 0; i < paramsSize; i++) {
			String key = in.readString();
			String value = in.readString();
			this.params.put(key, value);
		}
		this.text = in.readString();
		this.rightText = in.readString();
		this.scheme = in.readString();
		this.avatar = in.readString();
		this.type = in.readInt();
		this.viewType = in.readInt();
		this.showLine = in.readByte() != 0;
		this.info = in.readParcelable(PersonInfo.class.getClassLoader());
		this.items = in.createStringArrayList();
		this.subItems = new ArrayList<>();
		this.sex = in.readString();
		this.min_age = in.readString();
		this.max_age = in.readString();
		this.min_height = in.readString();
		this.max_height = in.readString();
		this.education = in.readString();
		this.min_education = in.readInt();
		this.max_education = in.readInt();
		this.career = in.readString();
		this.income = in.readString();
		this.expect_marry_date = in.readString();
		this.marriage_status = in.readString();
		this.min_weight = in.readString();
		this.max_weight = in.readString();
		this.has_car = in.readString();
		this.has_children = in.readString();
		this.has_house = in.readString();
		this.is_student = in.readString();
		this.vip = in.readString();
		this.username = in.readString();
		this.work_area_code = in.readString();
		this.born_area_code = in.readString();
		this.work_area_name = in.readString();
		this.born_area_name = in.readString();
		this.nationality = in.readString();
		this.birth_index = in.readString();
		this.phoneType = in.readInt();
		this.contacts = in.readParcelable(Contacts.class.getClassLoader());
	}

	public static final Creator<ItemInfo> CREATOR = new Creator<ItemInfo>() {
		@Override
		public ItemInfo createFromParcel(Parcel source) {
			return new ItemInfo(source);
		}

		@Override
		public ItemInfo[] newArray(int size) {
			return new ItemInfo[size];
		}
	};
}
