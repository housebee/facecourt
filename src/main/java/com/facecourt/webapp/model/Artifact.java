package com.facecourt.webapp.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Artifact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, unique = true)
	private String name;

	@Column(nullable = false, unique = true)
	private String desc;

	@ManyToOne(optional=false) 
    @JoinColumn(name="courtId", nullable=false, updatable=false)
	private Court court;

	@ManyToOne(optional=false) 
    @JoinColumn(name="ownerId", nullable=false, updatable=false)
	private User owner;

	@OneToMany(mappedBy = "artifact", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserArtifact> userArtifacts;
	
	public Artifact() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Court getCourt() {
		return court;
	}

	public void setCourt(Court court) {
		this.court = court;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

//	public Set<UserArtifact> getUserArtifacts() {
//		return userArtifacts;
//	}
//
//	public void setUserArtifacts(Set<UserArtifact> userArtifacts) {
//		this.userArtifacts = userArtifacts;
//	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", name=").append(name).append(", desc=").append(desc)
				.append(", owner=").append(owner).append(", court=").append(court).append("]");
		return builder.toString();
	}

}
