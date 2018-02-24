package com.facecourt.webapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.Court;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.model.UserArtifact;
import com.facecourt.webapp.model.VoteResultType;
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
		logger.info("find owner = " + owner);

		Court publicCourt = courtDao.findOne(Court.PUBLIC_COURT_ID);
		logger.info("public court = " + publicCourt);

		artifact.setOwner(owner);
		artifact.setCourt(publicCourt);

		UserArtifact userArtifact = new UserArtifact();
		userArtifact.setArtifact(artifact);
		userArtifact.setUser(owner);
		userArtifact.setVoteResult(VoteResultType.UNKNOWN);

		// Artifact result = artifactDao.save(artifact);

		owner.getUserArtifacts().add(userArtifact);
		artifact.getUserArtifacts().add(userArtifact);

		// userDao.save(owner);

		logger.info("\n\nbefore save artifact = " + artifact);
		Artifact result = artifactDao.save(artifact);

		logger.info("saved artifact =  " + result);
		return result;
	}

	/**
	 * Retrieve all artifacts
	 * 
	 * TODO: return only active ones.
	 * 
	 * @return all artifact
	 */
	public List<Artifact> getAllArtifacts() {
		logger.info("getAllArtifacts.");
		List<Artifact> result = artifactDao.findAll();
		logger.info("getAllArtifacts DONE.");
		return result;
	}

	/**
	 * find all artifacts for a user
	 * 
	 * @param userName
	 * @return list of artifacts
	 */
	public List<Artifact> getArtifactsByOwner(String userName) {
		logger.info("getArtifactsByOwner.");
		User owner = userDao.findByUsername(userName);

		List<Artifact> result = artifactDao.findByOwner(owner);

		logger.info("getArtifactsByOwner DONE.");
		return result;
	}
}
