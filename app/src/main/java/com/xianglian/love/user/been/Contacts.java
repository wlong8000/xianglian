package com.xianglian.love.user.been;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Contacts implements Parcelable {
	private String code;
	private String error;
	private Contacts result;
	private Contacts msg;
	private List<Contacts> contacts;
	private String uid;
	private String name;
	private String content;

	public Contacts getMsg() {
		return msg;
	}

	public void setMsg(Contacts msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Contacts getResult() {
		return result;
	}

	public void setResult(Contacts result) {
		this.result = result;
	}

	public List<Contacts> getContacts() {
		return contacts;
	}

	public void setContacts(List<Contacts> contacts) {
		this.contacts = contacts;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	public Contacts() {
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.code);
		dest.writeString(this.error);
		dest.writeParcelable(this.result, flags);
		dest.writeParcelable(this.msg, flags);
		dest.writeTypedList(this.contacts);
		dest.writeString(this.uid);
		dest.writeString(this.name);
		dest.writeString(this.content);
	}

	protected Contacts(Parcel in) {
		this.code = in.readString();
		this.error = in.readString();
		this.result = in.readParcelable(Contacts.class.getClassLoader());
		this.msg = in.readParcelable(Contacts.class.getClassLoader());
		this.contacts = in.createTypedArrayList(Contacts.CREATOR);
		this.uid = in.readString();
		this.name = in.readString();
		this.content = in.readString();
	}

	public static final Creator<Contacts> CREATOR = new Creator<Contacts>() {
		@Override
		public Contacts createFromParcel(Parcel source) {
			return new Contacts(source);
		}

		@Override
		public Contacts[] newArray(int size) {
			return new Contacts[size];
		}
	};
}
