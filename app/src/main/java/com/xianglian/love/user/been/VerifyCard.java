package com.xianglian.love.user.been;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 身份验证
 */
public class VerifyCard implements Parcelable {
	/**
	 * 真实姓名
	 */
	private String real_name;

	/**
	 * 身份证号
	 */
	private String id_card;

	/**
	 * 正面照片
	 */
	private String front_pic;

	/**
	 * 反面照片
	 */
	private String reverse_pic;

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getFront_pic() {
		return front_pic;
	}

	public void setFront_pic(String front_pic) {
		this.front_pic = front_pic;
	}

	public String getReverse_pic() {
		return reverse_pic;
	}

	public void setReverse_pic(String reverse_pic) {
		this.reverse_pic = reverse_pic;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.real_name);
		dest.writeString(this.id_card);
		dest.writeString(this.front_pic);
		dest.writeString(this.reverse_pic);
	}

	public VerifyCard() {
	}

	protected VerifyCard(Parcel in) {
		this.real_name = in.readString();
		this.id_card = in.readString();
		this.front_pic = in.readString();
		this.reverse_pic = in.readString();
	}

	public static final Creator<VerifyCard> CREATOR = new Creator<VerifyCard>() {
		@Override
		public VerifyCard createFromParcel(Parcel source) {
			return new VerifyCard(source);
		}

		@Override
		public VerifyCard[] newArray(int size) {
			return new VerifyCard[size];
		}
	};
}
