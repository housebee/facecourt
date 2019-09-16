package com.facecourt.webapp.vo;

public class UserVo {

	private String uid;

	private String email;

	private Boolean emailVerified;

	private String displayName;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		return "UserVo [uid=" + uid + ", email=" + email + ", emailVerified=" + emailVerified + ", displayName="
				+ displayName + "]";
	}

}
