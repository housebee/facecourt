package com.facecourt.webapp.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.model.VoteResultType;
import com.facecourt.webapp.service.ArtifactService;
import com.facecourt.webapp.util.AppUtil;

@RestController
public class AppRestController {
	// logger
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ArtifactService artifactService;

	@RequestMapping(value = "/votePos/{artifactId}", method = RequestMethod.GET)
	public Artifact votePos(@PathVariable(value = "artifactId") String artifactId) {
		logger.info("votePos, artifactId = " + artifactId);

		String currentUserName = AppUtil.INSTANCE.getUserName();

		artifactService.voteArtifact(currentUserName, Long.valueOf(artifactId), VoteResultType.POSITIVE);

		Artifact artifact = artifactService.getArtifactById(Long.valueOf(artifactId));

		Map<Long, Long> voteResultMap = artifactService.getArtifactVoteNum(artifact);

		Long posVote = voteResultMap.get(Long.valueOf(VoteResultType.POSITIVE.getCode()));
		Long negVote = voteResultMap.get(Long.valueOf(VoteResultType.NEGATIVE.getCode()));
		artifact.setTotalPos(posVote == null ? Long.valueOf(0) : posVote);
		artifact.setTotalNeg(negVote == null ? Long.valueOf(0) : negVote);

		logger.info("after votePos: " + artifact.toString());
		return artifact;
	}

	@RequestMapping(value = "/voteNeg/{artifactId}", method = RequestMethod.GET)
	public Artifact voteNeg(@PathVariable(value = "artifactId") String artifactId) {
		logger.info("voteNeg, artifactId = " + artifactId);

		String currentUserName = AppUtil.INSTANCE.getUserName();

		artifactService.voteArtifact(currentUserName, Long.valueOf(artifactId), VoteResultType.NEGATIVE);

		Artifact artifact = artifactService.getArtifactById(Long.valueOf(artifactId));

		Map<Long, Long> voteResultMap = artifactService.getArtifactVoteNum(artifact);

		Long posVote = voteResultMap.get(Long.valueOf(VoteResultType.POSITIVE.getCode()));
		Long negVote = voteResultMap.get(Long.valueOf(VoteResultType.NEGATIVE.getCode()));
		artifact.setTotalPos(posVote == null ? Long.valueOf(0) : posVote);
		artifact.setTotalNeg(negVote == null ? Long.valueOf(0) : negVote);

		logger.info("after voteNeg: " + artifact.toString());
		return artifact;
	}

}
