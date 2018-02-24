package com.facecourt.webapp.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import com.facecourt.webapp.controller.HomeController;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.model.UserProviderType;
import com.facecourt.webapp.persist.UserDao;

/**
 * Initial the facebook connection
 * 
 * @author suns
 *
 */
@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private static final String CHAR_DOT = ".";

	@Autowired
	private UserDao userRepository;

	/**
	 * sign-up user
	 * 
	 * Facebook does not provide userId.
	 * 
	 */
	@Override
	public String execute(Connection<?> connection) {
		logger.info("signup === ");
		UserProfile userProfile = connection.fetchUserProfile();
		String email = userProfile.getEmail();
		String firstName = userProfile.getFirstName();
		String lastName = userProfile.getLastName();
		// String displayName = connection.getDisplayName();

		String username = email;
		// TODO: what if FB user does not have email? username has to be unique,
		// how to handle it???
		if (StringUtils.isBlank(email)) {
			username = StringUtils.deleteWhitespace(firstName + CHAR_DOT + lastName);
		}

		// find if the user already exists with same email address
		logger.info("Check if user already exists by email =: " + username);
		User user = userRepository.findByUsername(username);
		logger.info("Existing user : " + user);

		if (user == null) {
			user = new User();
			user.setUsername(username);
			// user.setPassword(randomAlphabetic(8));
			user.setPassword("123");
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmailVerified(Boolean.FALSE);
			user.setProviderType(UserProviderType.FACEBOOK);
			userRepository.save(user);

			// TODO: may not be necessary because spring security already cached
			// principle in session
			// RequestAttributes request =
			// RequestContextHolder.currentRequestAttributes();
			// Object obj = request.getAttribute("user",
			// RequestAttributes.SCOPE_SESSION);
			// if (obj == null) {
			// logger.info("Add user to seesion." + user);
			// request.setAttribute("user", user,
			// RequestAttributes.SCOPE_SESSION);
			// }
		} else {
			// TODO: merge FB user to existing local user ?. Currently use local
			// user account if same email address is found although user login
			// with FB
		}

		logger.info("signup completed." + user);
		return user.getUsername();
	}

}
