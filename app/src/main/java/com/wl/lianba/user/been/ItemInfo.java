package com.wl.lianba.user.been;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.wl.lianba.main.home.been.PersonInfo;

import java.util.ArrayList;

/**
 * 针对 用户信息编辑类
 */
public class ItemInfo implements MultiItemEntity {

	private String text;
	private String rightText;
	private String scheme;
	private String avatar;
	private int type;
	private int viewType;
	private boolean showLine;
	private PersonInfo info;
	private ArrayList<String> items;

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

	public ArrayList<String> getItems() {
		return items;
	}

	public void setItems(ArrayList<String> items) {
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
}
