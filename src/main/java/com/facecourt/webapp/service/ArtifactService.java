package com.facecourt.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.persist.ArtifactRepository;

public class ArtifactService {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(ArtifactService.class);

	@Autowired
	private ArtifactRepository artifactRepository;

	public ArtifactService() {
		super();
	}

	/**
	 * Create a artifact
	 * 
	 * @param artifact
	 * @param owner
	 * @return
	 */
	public Artifact createArtifact(Artifact artifact, User owner) {
		logger.info("create artifact = " + artifact);
		artifact.setOwner(owner);

		Artifact result = artifactRepository.save(artifact);

		logger.info("saved artifact" + artifact);
		return result;
	}
}
