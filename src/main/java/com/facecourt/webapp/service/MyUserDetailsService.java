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
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.facecourt.webapp.model.User;
import com.facecourt.webapp.persist.UserDao;

@Service
public class MyUserDetailsService implements UserDetailsService {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

	@Autowired
	private UserDao userRepository;

	public MyUserDetailsService() {
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
		
		// set user to session
//        RequestAttributes request = RequestContextHolder.currentRequestAttributes();
//        Object obj = request.getAttribute("user", RequestAttributes.SCOPE_SESSION);
//        if (obj == null) {
//            logger.info("Add user to seesion." + user);
//            request.setAttribute("user", user, RequestAttributes.SCOPE_SESSION);
//        }
        
		return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true,
				true, Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
