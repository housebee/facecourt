package com.facecourt.webapp.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * Used for many-to-many ID
 * 
 * @author shaopeng.sun
 *
 */
@Embeddable
public class UserArtifactKey implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "userid")
	private Long userId;

	@Column(name = "artifactid")
	private Long artifactId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(Long artifactId) {
		this.artifactId = artifactId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		UserArtifactKey that = (UserArtifactKey) o;
		return Objects.equals(userId, that.userId) && Objects.equals(artifactId, that.artifactId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(userId, artifactId);
	}

	@Override
	public String toString() {
		return "UserArtifactKey [userId=" + userId + ", artifactId=" + artifactId + "]";
	}
	
};
