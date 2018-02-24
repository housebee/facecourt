package com.facecourt.webapp.ui;

/**
 * UI form object
 * 
 *  TODO: move to VO package.
 * 
 * @author suns
 *
 */
public class CaseForm {

	private long id;

	private String caseTitle;

	private String caseText;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCaseTitle() {
		return caseTitle;
	}

	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}

	public String getCaseText() {
		return caseText;
	}

	public void setCaseText(String caseText) {
		this.caseText = caseText;
	}

}
