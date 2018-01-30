package com.facecourt.webapp.security;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.facecourt.webapp.controller.HomeController;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.model.UserProviderType;
import com.facecourt.webapp.persist.UserRepository;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public String execute(Connection<?> connection) {
		logger.info("signup === ");
		UserProfile userProfile = connection.fetchUserProfile();
		String providerId = userProfile.getId();
		String email = userProfile.getEmail();
		String firstName = userProfile.getFirstName();
		String lastName = userProfile.getLastName();
		// String displayName = connection.getDisplayName();
		String username = email;
		if (StringUtils.isBlank(email)) {
			username = StringUtils.deleteWhitespace(firstName + "." + lastName);
		}

		// find if the user already exists
		logger.info("Check if user already exists by email =: " + email);
		User user = userRepository.findByEmail(email);
		// User user = userRepository.findByProviderId(providerId,
		// UserProviderType.FACEBOOK);
		logger.info("Existing user : " + user);

		if (user == null) {
			user = new User();
			user.setUsername(username);
			// user.setPassword(randomAlphabetic(8));
			user.setPassword("123");
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setProviderId(providerId);
			user.setProviderType(UserProviderType.FACEBOOK);
			userRepository.save(user);

			RequestAttributes request = RequestContextHolder.currentRequestAttributes();
			Object obj = request.getAttribute("user", RequestAttributes.SCOPE_SESSION);
			if (obj == null) {
				logger.info("Add user to seesion." + user);
				request.setAttribute("user", user, RequestAttributes.SCOPE_SESSION);
			}
		} else {
			// merge local to FB user.
			if (StringUtils.isBlank(firstName))
				user.setFirstName(firstName);

			if (StringUtils.isBlank(lastName))
				user.setLastName(lastName);

			if (StringUtils.isBlank(email))
				user.setEmail(email);

			// TODO: manage multiple social login sources.
			user.setProviderId(userProfile.getId());
			user.setProviderType(UserProviderType.FACEBOOK);
		}

		logger.info("signup completed." + user);
		return user.getUsername();
	}

}
