package com.facecourt.webapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.mem.InMemoryUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInController;

import com.facecourt.webapp.security.FacebookConnectionSignup;
import com.facecourt.webapp.security.FacebookSignInAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = { "com.facecourt.webapp.security" })
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// logger
	private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;

	@Autowired
	private UsersConnectionRepository usersConnectionRepository;

	@Autowired
	private FacebookConnectionSignup facebookConnectionSignup;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(final HttpSecurity httpSecurity) throws Exception {
		logger.info("configure.");
		// @formatter:off
		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/login*", "/signin/**", "/signup/**", "/api/**").permitAll()
				.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll().and().logout()
				// TODO: for H2 console. Only authorized user can access
				// http://localhost:8080/h2-console. Remove for different database.
				 .and().authorizeRequests().antMatchers("/h2-console/**").permitAll();
				//.and().authorizeRequests().antMatchers("/admin", "/h2_console/**").hasRole("ADMIN").anyRequest();
		httpSecurity.headers().frameOptions().disable();
		logger.info("configure done.");
	} // @formatter:on

	@Bean
	// @Primary
	public ProviderSignInController providerSignInController() {
		logger.info("providerSignInController.");
		((InMemoryUsersConnectionRepository) usersConnectionRepository).setConnectionSignUp(facebookConnectionSignup);
		return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository,
				new FacebookSignInAdapter());
	}

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder authMgrBuilder) throws Exception {
//		authMgrBuilder.inMemoryAuthentication().withUser("user").password("user").roles("USER").and().withUser("admin")
//				.password("admin").roles("ADMIN");
//	}
}