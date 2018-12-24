package com.facecourt.webapp.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.Court;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.model.UserArtifact;
import com.facecourt.webapp.model.UserArtifactKey;
import com.facecourt.webapp.model.VoteResultType;
import com.facecourt.webapp.persist.ArtifactDao;
import com.facecourt.webapp.persist.CourtDao;
import com.facecourt.webapp.persist.UserArtifactDao;
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

	@Autowired
	private UserArtifactDao userArtifactDao;

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

		// Court publicCourt = courtDao.findOne(Court.PUBLIC_COURT_ID); // problem with
		// spring-boot 2
		Court publicCourt = courtDao.getOne(Court.PUBLIC_COURT_ID);
		logger.info("public court = " + publicCourt);

		artifact.setOwner(owner);
		// TODO: currently set all artifacts to only open court
		artifact.setCourt(publicCourt);
		artifact.setStatus(Boolean.TRUE);
		artifact.setTotalPos(Long.valueOf(0));
		artifact.setTotalNeg(Long.valueOf(0));
		logger.info("before saving artifact = " + artifact);

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

		for (Artifact art : result) {
			logger.info("\nResult: " + art);
		}
		return result;
	}

	/**
	 * Get all artifacts and vote results.
	 * 
	 * @return all artifacts
	 */
	public List<Artifact> getAllArtifactsAndVotes() {
		logger.info("getAllArtifactsAndVotes.");

		List<Artifact> result = artifactDao.findAll();
		for (Artifact artifact : result) {
			// TODO: improve performance later with pagination.
			Map<Long, Long> voteResultMap = getArtifactVoteNum(artifact);
			if (voteResultMap != null) {
				Long posVote = voteResultMap.get(Long.valueOf(VoteResultType.POSITIVE.getCode()));
				Long negVote = voteResultMap.get(Long.valueOf(VoteResultType.NEGATIVE.getCode()));
				artifact.setTotalPos(posVote == null ? Long.valueOf(0) : posVote);
				artifact.setTotalNeg(negVote == null ? Long.valueOf(0) : negVote);
			}
			logger.info(" all artifact : " + artifact);
		}

		logger.info("getAllArtifacts DONE.");
		return result;
	}

	/**
	 * Get Artifact By Id
	 * 
	 * @param artifactId
	 * @return Artifact
	 */
	public Artifact getArtifactById(Long artifactId) {
		logger.info("getArtifactById. id = " + artifactId);
		Artifact artifact = artifactDao.findOne(artifactId);
		logger.info("getArtifactById. artifact = " + artifact);
		return artifact;
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

	/**
	 * User votes for an artifact.
	 * 
	 * @param userName
	 * @param artifactId
	 * @param voteResult
	 */
	public UserArtifact voteArtifact(String userName, Long artifactId, VoteResultType voteResult) {
		logger.info("Enter Service.voteArtifact. userName = " + userName + " artifactId = " + artifactId
				+ " voteResult = " + voteResult);
		User currentUser = userDao.findByUsername(userName);

		Long userId = currentUser.getId();

		UserArtifactKey userArtifactKey = new UserArtifactKey();
		userArtifactKey.setUserId(userId);
		userArtifactKey.setArtifactId(artifactId);

		UserArtifact userArtifact = userArtifactDao.findByUserArtifactKey(userArtifactKey);

		if (userArtifact == null) {
			// new
			userArtifact = new UserArtifact();
			userArtifact.setUserArtifactKey(userArtifactKey);
			userArtifact.setVoteResult(voteResult);

			userArtifactDao.save(userArtifact);
			logger.info("saved a new userArtifact vote : " + userArtifact);
		} else {
			// existing
			userArtifact.setVoteResult(voteResult);

			userArtifactDao.save(userArtifact);
			logger.info("update existing userArtifact vote : " + userArtifact);
		}

		logger.info("Completed voteArtifact.");
		return userArtifact;
	}

	/**
	 * Get an artifacts' current vote POS/NEG total numbers.
	 * 
	 * @param artifact
	 * @return
	 */
	public Map<Long, Long> getArtifactVoteNum(Artifact artifact) {
		logger.info("Start getUserPosVoteNum. " + artifact);

		List<Object[]> voteResult = userArtifactDao.findArtifactVoteNum(artifact.getId());

		Map<Long, Long> result = null;
		if (voteResult != null && !voteResult.isEmpty()) {
			result = new HashMap<Long, Long>();
			for (Object[] object : voteResult) {
				Integer voteType = (Integer) object[0];
				String totalVote = ((BigInteger) object[1]).toString();
				result.put(new Long(voteType), new Long(totalVote));
			}
		}

		logger.info("End getUserPosVoteNum.");
		return result;
	}
}
