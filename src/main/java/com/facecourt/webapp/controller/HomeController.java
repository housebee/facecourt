package com.facecourt.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.facecourt.webapp.model.Artifact;
import com.facecourt.webapp.service.ArtifactService;
import com.facecourt.webapp.ui.CaseForm;

/**
 * Web Controller
 * 
 * @author suns
 *
 */
@Controller
public class HomeController {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ArtifactService artifactService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		logger.info("home.");

		String principalName = getUser();
		logger.info("FB authenticated user = " + principalName);

		List<Artifact> artifacts = artifactService.getAllArtifactsAndVotes();

		model.addAttribute("artifacts", artifacts);
		return "index";
	}

	@RequestMapping(value = "/newCase", method = RequestMethod.GET)
	public String newCase(HttpServletRequest request, Model model) {
		logger.info("create new case.");
		model.addAttribute("caseForm", new CaseForm());
		return "newCase";
	}

	/**
	 * display all my artifacts
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/myCase", method = RequestMethod.GET)
	public String myCase(Model model) {
		logger.info("my case.");

		String principalName = getUser();

		List<Artifact> artifacts = artifactService.getArtifactsByOwner(principalName);

		List<Artifact> result = new ArrayList<Artifact>();

		for (Artifact artifact : artifacts) {
			Long artifactId = artifact.getId();
			Artifact artifactWithCount = artifactService.findArtifactWithVotes(artifactId);
			result.add(artifactWithCount);
		}

		model.addAttribute("artifacts", result);

		logger.info("get my artifacts.");
		return "myCase";
	}

	/**
	 * create a new artifact
	 * 
	 * @param caseForm
	 * @return
	 */
	@RequestMapping(value = "/newCaseSubmit", method = RequestMethod.POST)
	public String newCaseSubmit(@ModelAttribute CaseForm caseForm) {
		logger.info("submit new case.");
		String caseTitle = caseForm.getCaseTitle();
		String caseText = caseForm.getCaseText();

		String userName = getUser();

		Artifact artifact = new Artifact();
		artifact.setDesc(caseText);
		artifact.setTitle(caseTitle);

		artifact = artifactService.createArtifact(artifact, userName);

		logger.info("submit new case end. artifact = " + artifact);
		return "redirect:/myCase";
	}

	@RequestMapping(value = "edit/{artifactId}", method = RequestMethod.GET)
	public String editCase(Model model, @PathVariable("artifactId") String artifactId) {
		logger.info("edit my case. artifactId = " + artifactId);

		Long artifactIdLong = Long.valueOf(artifactId);

		Artifact artifact = artifactService.getArtifactById(artifactIdLong);

		model.addAttribute("artifact", artifact);

		logger.info("edit my artifact.");
		return "editCase";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String updateCase(Model model, @Valid Artifact artifact) {
		logger.info("update my case. artifact = " + artifact);

		Artifact existingArtifact = artifactService.getArtifactById(artifact.getId());

		existingArtifact.setTitle(artifact.getTitle());
		existingArtifact.setDesc(artifact.getDesc());

		Artifact savedArtifact = artifactService.updateArtifact(existingArtifact);

		logger.info("updated my artifact. " + savedArtifact);
		return "redirect:/myCase";
	}

	@RequestMapping(value = "delete/{artifactId}", method = RequestMethod.GET)
	public String deleteCase(Model model, @PathVariable("artifactId") String artifactId) {
		logger.info("delete my case. artifactId = " + artifactId);

		Long artifactIdLong = Long.valueOf(artifactId);

		artifactService.deleteArtifactById(artifactIdLong);

		logger.info("delete my artifact.");
		return "redirect:/myCase";
	}

	/**
	 * Principle in the session
	 * 
	 * @return authenticated user name
	 */
	private String getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		logger.debug("current user : " + currentPrincipalName);
		return currentPrincipalName;
	}

}
