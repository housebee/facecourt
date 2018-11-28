package com.facecourt.webapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "userartifact")
public class UserArtifact implements Serializable {

	private static final long serialVersionUID = -1146528530689566499L;

	@EmbeddedId
	private UserArtifactKey userArtifactKey;

	@Column(name = "voteresult", nullable = false)
	private VoteResultType voteResult;

	public UserArtifact() {
		super();
	}

	public UserArtifactKey getUserArtifactKey() {
		return userArtifactKey;
	}

	public void setUserArtifactKey(UserArtifactKey userArtifactKey) {
		this.userArtifactKey = userArtifactKey;
	}

	public VoteResultType getVoteResult() {
		return voteResult;
	}

	public void setVoteResult(VoteResultType voteResult) {
		this.voteResult = voteResult;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("[UserId=").append(userArtifactKey.getUserId()).append(", artifactId=")
				.append(userArtifactKey.getArtifactId()).append(", Vote=").append(voteResult).append("]");
		return builder.toString();
	}

}
