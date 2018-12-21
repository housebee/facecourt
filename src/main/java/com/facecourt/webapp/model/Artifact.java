package com.facecourt.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * Model object - Artifact
 * 
 * @author suns
 *
 */
@Entity
public class Artifact {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "artifact_generator")
	@SequenceGenerator(name = "artifact_generator", sequenceName = "SEQUENCE_ARTIFACT", initialValue = 100, allocationSize = 50)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String desc;

	@Column(name = "totalpos")
	private Long totalPos;

	@Column(name = "totalneg")
	private Long totalNeg;

	@Column(name = "status")
	private Boolean status;

	// @OneToMany(mappedBy = "artifact")
	// private Set<UserArtifact> userArtifacts = new HashSet<>();

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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getTotalPos() {
		return totalPos;
	}

	public void setTotalPos(Long totalPos) {
		this.totalPos = totalPos;
	}

	public Long getTotalNeg() {
		return totalNeg;
	}

	public void setTotalNeg(Long totalNeg) {
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
