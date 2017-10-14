package com.facecourt.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String displayHome(Model model) {
		model.addAttribute("facebook", "facebook profile");
		model.addAttribute("message", "Sun");
		return "/test";
	}
}
