package com.facecourt.webapp.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facecourt.webapp.model.Artifact;

public interface ArtifactDao extends JpaRepository<Artifact, Long> {

}
