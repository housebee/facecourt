package com.facecourt.webapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "User")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name="user_generator", sequenceName = "SEQUENCE_USER", initialValue=100, allocationSize=50)
	private Long id;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	private String password;

	@Column(name = "firstname")
	private String firstName;

	@Column(name = "lastname")
	private String lastName;

	@Column(name = "emailverified")
	private Boolean emailVerified;

	@Column(name = "providertype")
	private UserProviderType providerType;

	@Column(name = "active")
	private Boolean active;

	// private Set<String> roles = new HashSet<>();

//	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@Transient
	private Set<UserArtifact> userArtifacts = new HashSet<>();

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<UserArtifact> getUserArtifacts() {
		return userArtifacts;
	}

	public void setUserArtifacts(Set<UserArtifact> userArtifacts) {
		this.userArtifacts = userArtifacts;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public UserProviderType getProviderType() {
		return providerType;
	}

	public void setProviderType(UserProviderType providerType) {
		this.providerType = providerType;
	}

	public Boolean getActive() {
		return active;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", username=").append(username).append(", password=")
				.append(password).append("]");
		return builder.toString();
	}

}
