package com.facecourt.webapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Model object - Artifact
 * 
 * @author suns
 *
 */
@Entity
public class Artifact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String desc;

	@Column(name = "totalpos")
	private String totalPos;

	@Column(name = "totalneg")
	private String totalNeg;

	@Column(name = "status")
	private Boolean status;

	@OneToMany(mappedBy = "artifact")
	private Set<UserArtifact> userArtifacts = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "ownerid")
	private User owner;

	@ManyToOne
	@JoinColumn(name = "courtid")
	private Court court;

	public Artifact() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Court getCourt() {
		return court;
	}

	public void setCourt(Court court) {
		this.court = court;
	}

	public Set<UserArtifact> getUserArtifacts() {
		return userArtifacts;
	}

	public void setUserArtifacts(Set<UserArtifact> userArtifacts) {
		this.userArtifacts = userArtifacts;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getTotalPos() {
		return totalPos;
	}

	public void setTotalPos(String totalPos) {
		this.totalPos = totalPos;
	}

	public String getTotalNeg() {
		return totalNeg;
	}

	public void setTotalNeg(String totalNeg) {
		this.totalNeg = totalNeg;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Artifact [id=").append(id).append(", title=").append(title).append(", desc=").append(desc)
				.append(", status=").append(status).append(", totalPos=").append(totalPos).append(", totalNeg=")
				.append(totalNeg).append("]");
		return builder.toString();
	}
}
