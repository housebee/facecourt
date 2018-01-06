package com.facecourt.webapp.security;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import com.facecourt.webapp.controller.HomeController;
import com.facecourt.webapp.model.User;
import com.facecourt.webapp.persist.UserRepository;

@Service
public class FacebookConnectionSignup implements ConnectionSignUp {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {
        //System.out.println("signup === ");
    	logger.info("signup === ");
        final User user = new User();
        user.setUsername(connection.getDisplayName());
        user.setPassword(randomAlphabetic(8));
        userRepository.save(user);
        return user.getUsername();
    }

}
