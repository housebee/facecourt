package com.facecourt.webapp.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.model.VoteResultType;
import com.facecourt.webapp.service.ArtifactService;
import com.facecourt.webapp.util.AppUtil;
import com.facecourt.webapp.vo.UserVo;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AppRestController {
	// logger
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ArtifactService artifactService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String test() {
		logger.info("hello.........");
		return "Hello, Shaopeng Sun";
	}

	/**
	 * Add a new user
	 * 
	 * @param userVo
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public UserVo newUser(@RequestBody UserVo userVo) {
		logger.info("new user... \n: " + userVo);
		User user = null;

		String email = userVo == null ? null : userVo.getEmail();
		if (StringUtils.isNotBlank(email)) {
			// save a user to repo
			user = this.artifactService.getUserByUsername(email);
			if (user == null) {
				user = new User();
				user.setUsername(email);
				user.setPassword("sugar");

				user = this.artifactService.createUser(user);
				logger.info("created user: " + user);
			}

			userVo.setUid(user.getId().toString());
		} else {
			logger.error("Email is required for new user.");
		}

		return userVo;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public UserVo getUserByUsername(@RequestParam String userName) {
		logger.info("get user by username... \n: " + userName);
		UserVo result = null;

		if (StringUtils.isNotBlank(userName)) {
			User user = this.artifactService.getUserByUsername(userName);
			if (user != null) {
				result = new UserVo();
				result.setEmail(user.getUsername());
			}
		}

		logger.info("get user by username. Found = " + result);
		return result;
	}

	/**
	 * currently only use userName as ID, NOTE: there is '/' at the end of URL to
	 * support email as id.
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/users/{id}/", method = RequestMethod.GET)
	public UserVo getUserById(@PathVariable String id) {
		logger.info("get user by uid... \n: " + id);
		UserVo result = null;

		if (StringUtils.isNotBlank(id)) {
			User user = this.artifactService.getUserByUsername(id);
			if (user != null) {
				result = new UserVo();
				result.setEmail(user.getUsername());
			}
		}

		return result;
	}

	@RequestMapping(value = "/vote/{userId}/{artId}/{vote}", method = RequestMethod.GET)
	public Artifact vote(@PathVariable String userId, @PathVariable String artId, @PathVariable String vote) {
		logger.info("vote... userId = " + userId + " artId = " + artId + " vote: " + vote);

		Long artifactId = Long.valueOf(artId);
		Integer voteValue = Integer.valueOf(vote);

		VoteResultType voteResult = VoteResultType.UNKNOWN;
		if (Integer.valueOf(1).equals(voteValue)) {
			voteResult = VoteResultType.POSITIVE;
		} else if (Integer.valueOf(-1).equals(voteValue)) {
			voteResult = VoteResultType.NEGATIVE;
		}

		this.artifactService.voteArtifact(userId, artifactId, voteResult);
		Artifact artifact = this.artifactService.findArtifactWithVotes(artifactId);

		return artifact;
	}

	@RequestMapping(value = "/artifacts", method = RequestMethod.GET)
	public List<Artifact> getArtifacts() {
		logger.info("get all artifacts with votes. ");
		List<Artifact> all = artifactService.getAllArtifactsAndVotes();
		logger.info("get all artifacts with votes DONE. ");
		return all;
	}

	@RequestMapping(value = "/artifacts/{id}", method = RequestMethod.GET)
	public Artifact getArtifactById2(@PathVariable(value = "id") String artifactId) {
		logger.info("getArtifactById start. id = " + artifactId);

		if (StringUtils.isEmpty(artifactId)) {
			logger.error("getArtifactById cannot have empty id.");
			return null;
		}

		Artifact artifact = artifactService.findArtifactWithVotes(Long.valueOf(artifactId));

		logger.info("getArtifactById end. " + artifact);
		return artifact;
	}

	@RequestMapping(value = "/artifacts/{userId}/", method = RequestMethod.POST)
	public Artifact createArtifact(@PathVariable(value = "userId") String userId, @RequestBody Artifact artifact) {
		logger.info("createArtifact, artifact = " + artifact);
		Artifact result = this.artifactService.createArtifact(artifact, userId);
		logger.info("createArtifact DONE, artifact = " + artifact);
		return result;
	}

	@RequestMapping(value = "/artifacts", method = RequestMethod.PUT)
	public Artifact updateArtifact(@RequestBody Artifact artifact) {
		logger.info("updateArtifact, artifact = " + artifact);
		Artifact result = this.artifactService.updateArtifact(artifact);
		logger.info("updateArtifact DONE, artifact = " + artifact);
		return result;
	}

	@RequestMapping(value = "/artifacts/{artifactId}", method = RequestMethod.DELETE)
	public void deleteArtifact(@PathVariable(value = "artifactId") String artifactId) {
		logger.info("deleteArtifact, artifactId = " + artifactId);
		this.artifactService.deleteArtifactById(Long.valueOf(artifactId));
		logger.info("deleteArtifact DONE.");
	}

	@RequestMapping(value = "/votePos/{artifactId}", method = RequestMethod.GET)
	public Artifact votePos(@PathVariable(value = "artifactId") String artifactId) {
		logger.info("votePos, artifactId = " + artifactId);

		String currentUserName = AppUtil.INSTANCE.getUserName();

		artifactService.voteArtifact(currentUserName, Long.valueOf(artifactId), VoteResultType.POSITIVE);

		Artifact artifact = artifactService.findArtifactWithVotes(Long.valueOf(artifactId));

		logger.info("after votePos: " + artifact);
		return artifact;
	}

	@RequestMapping(value = "/voteNeg/{artifactId}", method = RequestMethod.GET)
	public Artifact voteNeg(@PathVariable(value = "artifactId") String artifactId) {
		logger.info("voteNeg, artifactId = " + artifactId);

		String currentUserName = AppUtil.INSTANCE.getUserName();

		artifactService.voteArtifact(currentUserName, Long.valueOf(artifactId), VoteResultType.NEGATIVE);

		Artifact artifact = artifactService.findArtifactWithVotes(Long.valueOf(artifactId));

		logger.info("after voteNeg: " + artifact);
		return artifact;
	}

	@RequestMapping(value = "/votePos/{userId}/{artifactId}", method = RequestMethod.GET)
	public Artifact votePos(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "artifactId") String artifactId) {
		logger.info("votePos, userId = " + userId + " artifactId = " + artifactId);

		artifactService.voteArtifact(Long.valueOf(userId), Long.valueOf(artifactId), VoteResultType.POSITIVE);

		Artifact artifact = artifactService.findArtifactWithVotes(Long.valueOf(artifactId));

		logger.info("after votePos: " + artifact);
		return artifact;
	}

	@RequestMapping(value = "/voteNeg/{userId}/{artifactId}", method = RequestMethod.GET)
	public Artifact voteNeg(@PathVariable(value = "userId") String userId,
			@PathVariable(value = "artifactId") String artifactId) {
		logger.info("voteNeg, userId = " + userId + " artifactId = " + artifactId);

		artifactService.voteArtifact(Long.valueOf(userId), Long.valueOf(artifactId), VoteResultType.NEGATIVE);

		Artifact artifact = artifactService.findArtifactWithVotes(Long.valueOf(artifactId));

		logger.info("after voteNeg: " + artifact);
		return artifact;
	}

}
