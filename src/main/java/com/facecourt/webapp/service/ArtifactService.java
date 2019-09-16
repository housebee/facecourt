package com.facecourt.webapp.service;

import java.math.BigInteger;
import java.util.ArrayList;
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

		List<Artifact> result = new ArrayList<Artifact>();
		
		List<Object[]> allArtifactList = this.artifactDao.findAllWithCount();
		allArtifactList.stream().forEach((record) -> {
			Artifact artifact = new Artifact();
			
			artifact.setId((Long.valueOf((int)record[0])));
			artifact.setTitle((String)record[1]);
			artifact.setDesc((String)record[2]);
			artifact.setTotalPos(((BigInteger)record[3]).longValue());
			artifact.setTotalNeg(((BigInteger)record[4]).longValue());
			
			result.add(artifact);
		});

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
	
	public Artifact updateArtifact(Artifact artifact) {
		logger.info("updateArtifact. artifact = " + artifact);
		
		Artifact existing = artifactDao.getOne(artifact.getId());
		
		existing.setTitle(artifact.getTitle());
		existing.setDesc(artifact.getDesc());
		
		Artifact updatedArtifact = artifactDao.save(existing);
		
		logger.info("updateArtifact.");
		return updatedArtifact;
	}
	
	public void deleteArtifactById(Long artifactId) {
		logger.info("deleteArtifactById. artifactId = " + artifactId);
		
		artifactDao.delete(artifactId);
		
		logger.info("deleteArtifactById.");
	}

	/**
	 * User votes for an artifact.
	 * 
	 * @param userName
	 * @param artifactId
	 * @param voteResult
	 */
	public void voteArtifact(String userName, Long artifactId, VoteResultType voteResult) {
		logger.info("Enter Service.voteArtifact. userName = " + userName + " artifactId = " + artifactId
				+ " voteResult = " + voteResult);
		User currentUser = userDao.findByUsername(userName);

		Long userId = currentUser.getId();

		this.voteArtifact(userId, artifactId, voteResult);
	}
	
	public void voteArtifact(Long userId, Long artifactId, VoteResultType voteResult) {
		logger.info("Enter Service.voteArtifact. userId = " + userId + " artifactId = " + artifactId
				+ " voteResult = " + voteResult);

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
		
		logger.info("Completed voteArtifact. " + userArtifact);
	}
	
	public Artifact findArtifactWithVotes(Long artifactId) {
		Artifact artifact = null;

		List<Object[]> found = artifactDao.findArtifactWithCount(artifactId);
		
//		found.stream().forEach((record) -> {
//			artifact.setId((Long.valueOf((int)record[0])));
//			artifact.setTitle((String)record[1]);
//			artifact.setDesc((String)record[2]);
//			artifact.setTotalPos(((BigInteger)record[3]).longValue());
//			artifact.setTotalNeg(((BigInteger)record[4]).longValue());
//			logger.info("Found artifact with votes: " + artifact);
//		});
		
		for (Object[] record : found) {
			artifact = new Artifact();
			artifact.setId((Long.valueOf((int)record[0])));
			artifact.setTitle((String)record[1]);
			artifact.setDesc((String)record[2]);
			artifact.setTotalPos(((BigInteger)record[3]).longValue());
			artifact.setTotalNeg(((BigInteger)record[4]).longValue());
			logger.info("Found artifact with votes: " + artifact);
			break; // should be only one found.
		}

		return artifact;
	}

	/**
	 * Get an artifacts' current vote POS/NEG total numbers.
	 * 
	 * @param artifact
	 * @return
	 */
	public Map<Long, Long> getArtifactVoteNum(Long artifactId) {
		logger.info("Start getUserPosVoteNum. " + artifactId);

		List<Object[]> voteResult = userArtifactDao.findArtifactVoteNum(artifactId);

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
	
	public Artifact getArtifactVoteNumbers(String artifactId) {
		logger.info("Start getArtifactVotes. " + artifactId);
		List<Object[]> results = userArtifactDao.findArtifactVoteNum(Long.valueOf(artifactId));
	
		Artifact artifact = new Artifact();
		
		for(Object[] result: results) {
			Long voteResult = (Long) result[0];
			Long total = (Long) result[1];
			if(Long.valueOf(0).equals(voteResult)) {
				artifact.setTotalNeg(total);
			} else if (Long.valueOf(2).equals(total)) {
				artifact.setTotalPos(total);
			}
		}
		
		return artifact;
	}
	
	public User createUser(User user) {
		User savedUser = this.userDao.save(user);
		return savedUser;
	}
	
	public User getUserByUsername(String username) {
		User user = this.userDao.findByUsername(username);
		return user;
	}
	
	public void getAll() {
		this.artifactDao.findAllWithCount();
	}
}
