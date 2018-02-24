package com.facecourt.webapp.persist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.User;

public interface ArtifactDao extends JpaRepository<Artifact, Long> {

	/**
	 * Find all artifacts by owner
	 * 
	 * @param owner
	 * @return owner created artifacts
	 */
	List<Artifact> findByOwner(User owner);

}
