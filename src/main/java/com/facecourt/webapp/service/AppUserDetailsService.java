package com.facecourt.webapp.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.facecourt.webapp.model.User;
import com.facecourt.webapp.persist.UserDao;

@Service
public class AppUserDetailsService implements UserDetailsService {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);

	@Autowired
	private UserDao userRepository;

	public AppUserDetailsService() {
		super();
	}

	// API
	@Override
	public UserDetails loadUserByUsername(final String username) {
		logger.info("loadUserByUsername, username = " + username);
		final User user = userRepository.findByUsername(username);
		if (user == null) {
			logger.error("cannot find user, username = " + username);
			throw new UsernameNotFoundException(username);
		}
		logger.info("find user " + user);

		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true,
				true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
