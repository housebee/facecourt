package com.facecourt.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

		List<Artifact> artifacts = artifactService.getAllArtifacts();

		model.addAttribute("artifacts", artifacts);
		return "/index";
	}

	@RequestMapping(value = "/newCase", method = RequestMethod.GET)
	public String newCase(HttpServletRequest request, Model model) {
		logger.info("create new case.");
		model.addAttribute("caseForm", new CaseForm());
		return "/newCase";
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

		model.addAttribute("artifacts", artifacts);

		logger.info("get my artifacts.");
		return "/myCase";
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
		return "/myCase";
	}

	/**
	 * Principle in the session
	 * 
	 * @return authenticated user name
	 */
	private String getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		return currentPrincipalName;
	}

}
