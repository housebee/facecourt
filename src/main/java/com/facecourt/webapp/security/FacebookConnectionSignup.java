package com.facecourt.webapp.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
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
		logger.info("=== facebook signup === ");

		Facebook facebook = (Facebook) connection.getApi();

		String[] fields = { "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices",
				"education", "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown",
				"inspirational_people", "installed", "install_type", "is_verified", "languages", "last_name", "link",
				"locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes",
				"payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other",
				"sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "video_upload_limits",
				"viewer_can_send_gift", "website", "work" };
		org.springframework.social.facebook.api.User userProfile = facebook.fetchObject("me",
				org.springframework.social.facebook.api.User.class, fields);

		String email = userProfile.getEmail();
		String firstName = userProfile.getFirstName();
		String lastName = userProfile.getLastName();
		Boolean isVerified = userProfile.isVerified();

		String username = email;
		if (StringUtils.isBlank(username)) {
			// facebook email is required because it is used as username for login.
			throw new UsernameNotFoundException("cannot create account because facebook email address is empty.");
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
			user.setEmailVerified(isVerified);
			user.setProviderType(UserProviderType.FACEBOOK);
			userRepository.save(user);
		} else {
			logger.info("facebook user come back. " + username);
			// TODO: merge FB user to existing local user ?. Currently use local
			// user account if same email address is found although user login
			// with FB
		}

		logger.info("signup completed." + user);
		return username;
	}

}
