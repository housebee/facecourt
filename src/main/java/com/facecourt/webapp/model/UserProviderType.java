package com.facecourt.webapp.model;

/**
 * Source Type that user come from,
 * 
 * @author suns
 */
public enum UserProviderType {
	SELF(0), FACEBOOK(1);

	private final int code;

	UserProviderType(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
