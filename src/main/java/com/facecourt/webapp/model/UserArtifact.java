package com.facecourt.webapp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "userartifact")
public class UserArtifact implements Serializable {

	private static final long serialVersionUID = -1146528530689566499L;

	@Id
	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;

	@Id
	@ManyToOne
	@JoinColumn(name = "artifactid")
	private Artifact artifact;

	@Column(name = "voteresult", nullable = false)
	private VoteResultType voteResult;

	public UserArtifact() {
		super();
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

	public VoteResultType getVoteResult() {
		return voteResult;
	}

	public void setVoteResult(VoteResultType voteResult) {
		this.voteResult = voteResult;
	}

}
