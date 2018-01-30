package com.facecourt.webapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserArtifact")
public class UserArtifact implements Serializable {

	/**
	 * generated
	 */
	private static final long serialVersionUID = -1146528530689566499L;

	@Id
	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "artifactId", referencedColumnName = "id")
	private Artifact artifact;

	@Column(name = "voteResult", nullable = false)
	private VoteResultType voteResult;

	public UserArtifact() {
		super();
	}

	public VoteResultType getVoteResult() {
		return voteResult;
	}

	public void setVoteResult(VoteResultType voteResult) {
		this.voteResult = voteResult;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Artifact getArtifact() {
		return artifact;
	}

	public void setArtifact(Artifact artifact) {
		this.artifact = artifact;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("UserArtifact [user=").append(user).append(",artifact=").append(artifact).append(", voteRsult=")
				.append(voteResult).append("]");
		return builder.toString();
	}

}
