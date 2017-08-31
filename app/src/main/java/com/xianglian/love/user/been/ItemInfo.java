package com.xianglian.love.user.been;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xianglian.love.main.home.been.PersonInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 针对 用户信息编辑类
 */
public class ItemInfo implements MultiItemEntity, Parcelable {

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
	public String min_age;
	public String max_age;
	public String min_height;
	public String max_height;
	public String education;
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

	public String work_area_code;
	public String born_area_code;
	public String nationality;
	public String birth_index;

	@Override
	public int getItemType() {
		return viewType;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRightText() {
		return rightText;
	}

	public void setRightText(String rightText) {
		this.rightText = rightText;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public PersonInfo getInfo() {
		return info;
	}

	public void setInfo(PersonInfo info) {
		this.info = info;
	}

	public boolean isShowLine() {
		return showLine;
	}

	public void setShowLine(boolean showLine) {
		this.showLine = showLine;
	}

	public List<List<String>> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<List<String>> subItems) {
		this.subItems = subItems;
	}

	public ItemInfo() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.sex);
		dest.writeString(this.min_age);
		dest.writeString(this.max_age);
		dest.writeString(this.min_height);
		dest.writeString(this.max_height);
		dest.writeString(this.education);
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
		dest.writeString(this.work_area_code);
		dest.writeString(this.born_area_code);
		dest.writeString(this.nationality);
		dest.writeString(this.birth_index);
	}

	protected ItemInfo(Parcel in) {
		this.sex = in.readString();
		this.min_age = in.readString();
		this.max_age = in.readString();
		this.min_height = in.readString();
		this.max_height = in.readString();
		this.education = in.readString();
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
		this.work_area_code = in.readString();
		this.born_area_code = in.readString();
		this.nationality = in.readString();
		this.birth_index = in.readString();
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

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		setParam("sex", sex, buffer);
		setParam("min_age", min_age, buffer);
		setParam("max_age", max_age, buffer);
		setParam("work_area_code", work_area_code, buffer);
		setParam("born_area_code", born_area_code, buffer);
		setParam("min_height", min_height, buffer);
		setParam("max_height", max_height, buffer);
		setParam("education", education, buffer);
		setParam("career", career, buffer);
		setParam("income", income, buffer);
		setParam("expect_marry_date", expect_marry_date, buffer);
		setParam("nationality", nationality, buffer);
		setParam("marriage_status", marriage_status, buffer);
		setParam("birth_index", birth_index, buffer);
		setParam("min_weight", min_weight, buffer);
		setParam("max_weight", max_weight, buffer);
		setParam("has_car", has_car, buffer);
		setParam("has_children", has_children, buffer);
		setParam("has_house", has_house, buffer);
		setParam("is_student", is_student, buffer);
		setParam("vip", vip, buffer);
		return buffer.toString();
	}

	private void setParam(String key, String value, StringBuffer buffer) {
		if (TextUtils.isEmpty(value)) return;
		buffer.append(key).append("=").append(value).append("&");
	}
}
