package com.facecourt.webapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Artifact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String title;

	@Column(name = "description")
	private String desc;

	@OneToMany(mappedBy = "artifact")
	private Set<UserArtifact> userArtifacts = new HashSet<>();

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

	public Set<UserArtifact> getUserArtifacts() {
		return userArtifacts;
	}

	public void setUserArtifacts(Set<UserArtifact> userArtifacts) {
		this.userArtifacts = userArtifacts;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Artifact [id=").append(id).append(", title=").append(title).append(", desc=").append(desc)
				.append("]");
		return builder.toString();
	}
}
