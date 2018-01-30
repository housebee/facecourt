package com.facecourt.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.facecourt.webapp.model.CaseForm;

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

	@RequestMapping(value = "/newCase", method = RequestMethod.GET)
	public String newCase(HttpServletRequest request, Model model) {
		logger.info("create new case.");
		
		String principalName = getUser();
		logger.info("FBauthenticated user = " + principalName);

		model.addAttribute("caseForm", new CaseForm());
		return "/newCase";
	}

	@RequestMapping(value = "/myCase", method = RequestMethod.GET)
	public String myCase(Model model) {
		logger.info("my case.");
		return "/myCase";
	}

	@RequestMapping(value = "/newCaseSubmit", method = RequestMethod.POST)
	public String newCaseSubmit(@ModelAttribute CaseForm caseForm) {
		logger.info("submit new case.");
		String caseText = caseForm.getCaseText();
		System.out.println(caseText);
		return "/index";
	}
	
	private String getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		return currentPrincipalName;
	}

}
