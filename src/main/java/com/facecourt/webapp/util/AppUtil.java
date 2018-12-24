package com.facecourt.webapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.facecourt.webapp.controller.HomeController;

public class AppUtil {
	// logger
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	public static AppUtil INSTANCE = new AppUtil();

	/**
	 * Principle in the session
	 * 
	 * @return authenticated user name
	 */
	public String getUserName() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		logger.debug("current user : " + currentPrincipalName);
		return currentPrincipalName;
	}
}
