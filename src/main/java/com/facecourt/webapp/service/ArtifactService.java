package com.facecourt.webapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.Court;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.persist.ArtifactDao;
import com.facecourt.webapp.persist.CourtDao;
import com.facecourt.webapp.persist.UserDao;

@Service
public class ArtifactService {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(ArtifactService.class);

	@Autowired
	private ArtifactDao artifactDao;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	private CourtDao courtDao;

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
	public Artifact createArtifact(Artifact artifact, String userName) {
		logger.info("create artifact = " + artifact + " by user = " + userName);

		User owner = userDao.findByUsername(userName);
		
		Court publicCourt = courtDao.findOne(Court.PUBLIC_COURT_ID);
		logger.info("public court = " + publicCourt);

//		artifact.setOwner(owner);
//		artifact.setCourt(publicCourt);

		logger.info("\n\nbefore save artifact = " + artifact);
		Artifact result = artifactDao.save(artifact);
		
		logger.info("saved artifact =  " + result);
		return result;
	}
}
