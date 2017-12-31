package com.facecourt.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.facecourt.webapp.model.CaseForm;

@Controller
public class HomeController {

	@RequestMapping(value = "/newCase", method = RequestMethod.GET)
	public String newCase(Model model) {
		model.addAttribute("caseForm", new CaseForm());
		return "/newCase";
	}
	
	@RequestMapping(value = "/myCase", method = RequestMethod.GET)
	public String myCase(Model model) {
		return "/myCase";
	}
	
	@RequestMapping(value = "/newCaseSubmit", method = RequestMethod.POST)
	public String newCaseSubmit(@ModelAttribute CaseForm caseForm) {
		String caseText = caseForm.getCaseText();
		System.out.println(caseText);
		return "/index";
	}
	
}
