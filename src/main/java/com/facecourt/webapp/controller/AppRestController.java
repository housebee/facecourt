package com.facecourt.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.facecourt.webapp.model.Artifact;

@RestController
public class AppRestController {
	// logger
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/votePos/{artifactId}", method = RequestMethod.GET)
	public Artifact votePos(@PathVariable(value = "artifactId") String artifactId) {
		logger.info("votePos, artifactId = " + artifactId);

		Artifact artifact = new Artifact();
		artifact.setId(Long.valueOf(artifactId));
		artifact.setTotalPos(Long.valueOf(12345));
		artifact.setTotalNeg(Long.valueOf(54321));
		
		logger.info(artifact.toString());
		return artifact;
	}

	@RequestMapping(value = "/voteNeg/{artifactId}", method = RequestMethod.GET)
	public Artifact voteNeg(@PathVariable(value = "artifactId") String artifactId) {
		logger.info("voteNeg, artifactId = " + artifactId);

		Artifact artifact = new Artifact();
		artifact.setId(Long.valueOf(artifactId));
		artifact.setTotalPos(Long.valueOf(54321));
		artifact.setTotalNeg(Long.valueOf(12345));
		
		logger.info(artifact.toString());
		return artifact;
	}

}
