package com.facecourt.webapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

public abstract class BaseEntity<ID> {
	@Column(name = "creation_time", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date creationTime;

	@Column(name = "modification_time", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date modificationTime;

	@Version
	private long version;

	public abstract ID getId();

	public Date getCreationTime() {
		return creationTime;
	}

	public Date getModificationTime() {
		return modificationTime;
	}

	public long getVersion() {
		return version;
	}

	@PrePersist
	public void prePersist() {
		Date now = new Date();
		this.creationTime = now;
		this.modificationTime = now;
	}

	@PreUpdate
	public void preUpdate() {
		this.modificationTime = new Date();
	}
}
