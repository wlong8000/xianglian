package com.wl.lianba.user.been;

import java.io.Serializable;

public class MyContacts implements Serializable {

	private static final long serialVersionUID = 7858379067991256765L;
	private Long id;
	private String name;
	private String phoneNo;
	private Long avatarId;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Long getAvatarId() {
		return avatarId;
	}

	public void setAvatarId(Long avatarId) {
		this.avatarId = avatarId;
	}

	@Override
	public String toString() {
		return "Contacts [id=" + id + ", name=" + name + ", phoneNo=" + phoneNo + ", avatarId=" + avatarId + "]";
	}

}
