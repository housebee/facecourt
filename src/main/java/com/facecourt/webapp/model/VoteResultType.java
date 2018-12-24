package com.facecourt.webapp.model;

/**
 * Type of the Vote,
 * 
 * @author suns
 */
public enum VoteResultType {
	POSITIVE (0), NEGATIVE(1), UNKNOWN(2);
	
	private final int code;
	
	VoteResultType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
