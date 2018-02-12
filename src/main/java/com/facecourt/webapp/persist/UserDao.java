package com.facecourt.webapp.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facecourt.webapp.model.User;

public interface UserDao extends JpaRepository<User, Long> {

	User findByUsername(final String username);

	// @Query("SELECT u FROM User u WHERE u.providerId = :providerId and
	// u.providerType = :providerType")
	// User findByProviderId(@Param("providerId") String providerId,
	// @Param("providerType") UserProviderType providerType);

}
