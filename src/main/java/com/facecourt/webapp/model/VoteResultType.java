package com.facecourt.webapp.model;

/**
 * Type of the Vote,
 * 
 * @author suns
 */
public enum VoteResultType {
	NEGATIVE(-1), UNKNOWN(0), POSITIVE (1);
	
	private final int code;
	
	VoteResultType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
}
