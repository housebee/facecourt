package com.facecourt.webapp.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.facecourt.webapp.model.User;
import com.facecourt.webapp.model.UserProviderType;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(final String username);

	User findByEmail(final String email);

	@Query("SELECT u FROM User u WHERE u.providerId = :providerId and u.providerType = :providerType")
	User findByProviderId(@Param("providerId") String providerId, @Param("providerType") UserProviderType providerType);

}
